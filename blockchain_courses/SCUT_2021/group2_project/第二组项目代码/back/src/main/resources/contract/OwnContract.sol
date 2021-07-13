pragma solidity >= 0.4.25;
pragma experimental ABIEncoderV2;
import "./Table.sol";

contract OwnContract {
    
    struct Own{
        string userid;
        string certid;
        string content;
        int256 timestamp;
    }

    event NewOwn(string userid, string certid, string content, int256 timestamp);

    constructor() public{
        createTable();
    }

    function createTable() private {
        TableFactory tf = TableFactory(0x1001);
        // 创建表
        tf.createTable("t_own", "identity", "userid,certid,content,timestamp");
    }

    function openTable() private returns(Table) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_own");
        return table;
    }

    /*
    描述 : 创建交易
    参数 :
        sellusername : 卖方用户名
        name : 商品名字
        description : 商品描述
        price : 价格
        figpath : 图片路径
    状态 : 
        0 : 等待卖出
        1 : 已卖出
        2 : 等待仲裁
        3 : 已下架
    返回值：
        0  订单创建成功
        -1 订单创建失败
    */
    
    function newOwn(string memory userid, string memory certid, string memory content, int256 timestamp) public returns(int256){
        int256 ret_code = 0;
        Table table = openTable();

        Entry entry = table.newEntry();

        entry.set("identity", "own");
        entry.set("userid", userid);
        entry.set("certid", certid);
        entry.set("content", content);
        entry.set("timestamp", timestamp);
        
        
        // 插入
        int count = table.insert("own", entry);
        if (count == 1) {
            // 成功
            ret_code = 0;
        } else {
            // 失败? 无权限或者其他错误
            ret_code = -1;
        }
        emit NewOwn(userid, certid, content, timestamp);
        return ret_code;
    }

    function getOwn(string userid) public view returns (string[], string[], string[], int[]) {
        Table table = openTable();

        Condition condition = table.newCondition();
        condition.EQ("userid", userid);

        Entries entries = table.select("own", condition);
        string[] memory userids = new string[](uint(entries.size()));
        string[] memory certids = new string[](uint(entries.size()));
        string[] memory contents = new string[](uint(entries.size()));
        int[] memory timestamps = new int[](uint(entries.size()));
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            userids[uint(i)] = entry.getString("userid");
            certids[uint(i)] =  entry.getString("certid");
            contents[uint(i)] = entry.getString("content");
            timestamps[uint(i)] = entry.getInt("timestamp");                             
           
       
        }
        return (userids, certids, contents, timestamps);
    }
    

    function getOwnByUserIdAndCertId(string memory userid, string memory certid) public returns(bool) {
        Table table = openTable();

        Condition condition = table.newCondition();
        condition.EQ("userid", userid);
        condition.EQ("certid", certid);
        Entries entries = table.select("own", condition);
        if (entries.size() > 0) return true;
        else return false;
    }

    function deleteAll() public {
        Table table = openTable();

        Condition condition = table.newCondition();
        //condition.EQ("userid", userid);

        table.remove("own", condition);
    }
    
    

}
