pragma solidity ^0.4.24;

import "./Table.sol";

contract user{

    TableFactory tf = TableFactory(0x1001);
    //    Table t_user = tf.openTable("t_user");
    //    Table t_hospital = tf.openTable("t_hospital");
    //    Table t_government = tf.openTable("t_government");
    //    Table t_health = tf.openTable("t_health");

    constructor() public {
        //TableFactory tf = TableFactory(0x1001);
        tf.createTable("t_health", "userHealthHash", "isHealth,validPeriod");
        tf.createTable("t_user", "id", "name,residence");
    }

    /*
    描述 : 个人信息
    参数 ：
            id : 身份证号
            name: 姓名
            residence: 居住地

    返回值：
             1 成功，更新一条数据
             0 成功，新插入一条数据
            -1 失败
    */
    function upload(string id, string name, string residence) public returns(int) {
        // 查询被测id是否存在表中
        // TableFactory tf = TableFactory(0x1001);
        Table t_user = tf.openTable("t_user");
        Entries entries = t_user.select(id, t_user.newCondition());

        Entry entry = t_user.newEntry();
        if(0 == uint(entries.size())) {
            // 身份证号不存在表中，新建记录
            entry.set("id", id);
            entry.set("name", name);
            entry.set("residence", residence);

            // 插入
            int count = t_user.insert(id, entry);
            if (count == 1) {
                // 成功
                return 0;
            } else {
                // 失败? 无权限或者其他错误
                return -1;
            }
        } else {
            // 身份证号存在表中，更新记录
            entry.set("residence", residence);
            Condition condition = t_user.newCondition();
            condition.EQ("id", id);
            condition.EQ("name", name);
            count = t_user.update(id, entry, condition);
            if (count == 1) {
                return 1;
            } else {
                return -1;
            }
        }
    }


    /*
    描述 : 查询用户健康信息
    参数 ：
            userHealthHash : 用户健康哈希值
    返回值：
            参数一 ：有效返回0, 健康哈希值不存在返回-1, 健康哈希值已过期返回 -2
            参数二 ：健康为0, 不健康为 1, 不知道为 -1
    */
    function isHealthy(string userHealthHash) public view returns (int256, int256){
        //TableFactory tf = TableFactory(0x1001);
        Table t_health = tf.openTable("t_health");
        Entries entries = t_health.select(userHealthHash, t_health.newCondition());

        int256 is_vaild = -1;
        int256 is_health = -1;
        if(0 != uint256(entries.size())){
            Entry entry = entries.get(0);
            if (uint256(entry.getInt("validPeriod")) < now){
                is_vaild = -2;
            } else {
                is_vaild = 0;
                is_health = entry.getInt("isHealth");
            }
        }
        return (is_vaild, is_health);
    }


    /*
    描述 : generate health code
    参数 ：
            ID : user's ID number
    返回值：
            参数一：成功返回 1，用户不存在返回 2， 用户有问题返回3
            参数二：成功时返回健康哈希值
    */
    function generate(string id) public returns (int256, string){
        // int256 user_is_healthy = 1;

        int ret_code = 0;
        bytes32 health_code = "0x0";
        //TableFactory tf = TableFactory(0x1001);
        // Table t_hospital = tf.openTable("t_hospital");
        // Table t_government = tf.openTable("t_government");
        Table t_user = tf.openTable("t_user");
        Entries user_entries = t_user.select(id, t_user.newCondition());

        if (0 != uint256(user_entries.size())) {
            // 获取用户
            Entry user_entry = user_entries.get(0);

            // 获取该用户核酸检测信息和居住地安全信息
            // Entries hospital_entries = (t_hospital.select(ID, t_hospital.newCondition()));
            // Entries government_entries = (t_government.select(user_entry.getString("residence"), t_government.newCondition()));
            // require(hospital_entries.size() != 0,
            //     "The hospital does not have the user’s nucleic acid test results.");
            // require(government_entries.size() != 0,
            //     "The government does not have safety information about the user’s place of residence.");

            // int pHealth = hospital_entries.get(0).getInt("result");
            // int rHealth = government_entries.get(0).getInt("isDangerous");
            // int rHealth = 1;
            // int isHealth = -1;
            // if (pHealth == 0 && rHealth == 0) {
            //     isHealth = 0;
            // }
            // return (1, "what happen?");
            int isHealth = getHealthInfo(id, user_entry.getString("residence"));
            if (isHealth < -1) {
                string memory error_info;
                if (isHealth == -3) {
                    error_info = "The hospital does not have the user’s nucleic acid test results.";
                } else if (isHealth == -2) {
                    error_info = "The government does not have safety information about the user’s place of residence.";
                } else {
                    error_info = "wtf??";
                }
                return (isHealth, error_info);
            }
            // 根据选定字段生成健康哈希值
            health_code = sha256(
                abi.encodePacked(
                    user_entry.getInt("id"),
                    user_entry.getString("residence"),
                    user_entry.getString("name"),
                    now,
                    isHealth
                )
            );

            // 插入一条新的健康记录
            Table t_health = tf.openTable("t_health");
            Entry health_entry = t_health.newEntry();
            health_entry.set("userHealthHash", bytes32ToString(health_code));
            health_entry.set("isHealth", isHealth);
            health_entry.set("validPeriod", int(now + 60));

            //int count = t_health.insert(bytes32ToString(health_code), health_entry);
            if (t_health.insert(bytes32ToString(health_code), health_entry) == 1) {
                ret_code = 1;
            } else {
                ret_code = 3;
            }
        } else {
            // 用户不存在
            ret_code = 2;
            return (int256(user_entries.size()), "az");
        }

        return (ret_code, bytes32ToString(health_code));
    }

    //
    function getHealthInfo(string ID, string user_residence) private view returns(int){
        Table t_hospital = tf.openTable("t_hospital");
        Table t_government = tf.openTable("t_government");

        Entries hospital_entries = (t_hospital.select(ID, t_hospital.newCondition()));
        Entries government_entries = (t_government.select(user_residence, t_government.newCondition()));
        // require(hospital_entries.size() != 0,
        //     "The hospital does not have the user’s nucleic acid test results.");
        // require(government_entries.size() != 0,
        //     "The government does not have safety information about the user’s place of residence.");
        if (hospital_entries.size() == 0) {
            return -3;
        }
        if (government_entries.size() == 0) {
            return -2;
        }
        int pHealth = hospital_entries.get(0).getInt("result");
        int rHealth = government_entries.get(0).getInt("isDangerous");
        //int rHealth = 1;
        int isHealth = -1;
        if (pHealth == 0 && rHealth == 0) {
            isHealth = 0;
        } else {
            isHealth = 1;
        }
        return isHealth;
    }

    /// string类型转化为bytes32型转
    // function stringToBytes32(string memory source) constant returns(bytes32 result){
    //     assembly{
    //         result := mload(add(source,32))
    //     }
    // }

    /// bytes32类型转化为string型转
    function bytes32ToString(bytes32 x) private pure returns(string){
        bytes memory bytesString = new bytes(x.length);
        uint charCount = 0 ;
        for(uint j = 0 ; j < x.length; j++){
            // byte char = x[j];
            bytesString[j] = byte(65 + uint256(x[j]) % 58);
            charCount++;
        }
        // bytes memory bytesStringTrimmed = new bytes(charCount);
        // for(j=0;j<charCount;j++){
        //     bytesStringTrimmed[j]=bytesString[j];
        // }
        return string(bytesString);
    }

    // function NewBytes32ToString(bytes32 bname) returns(string){
    //     // 此处要加上memory
    //     // 先将有效字符计算出来
    //     bytes memory bytesChar = new bytes(bname.length);
    //     uint charCount = 0;
    //     for(uint i = 0;i < bname.length; i++){
    //         bytes1 char = bname[i];
    //         if(char != 0){
    //             charCount++;
    //         }
    //     }
    //     // 新建数组，指定长度为有效字节长度
    //     bytes memory bytesName = new bytes(charCount);
    //     for(uint j = 0;j < charCount;j++){
    //         bytesName[j] = bname[j];
    //     }
    //     return string(bytesName);
    // }
}