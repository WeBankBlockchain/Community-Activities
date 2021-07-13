pragma solidity ^0.4.24;

import "./Table.sol";

contract government{


    constructor() public{
        createTable();
    }

    function createTable() private{
        TableFactory tf = TableFactory(0x1001);
        tf.createTable("t_government", "regionName", "isDangerous,date");
    }

    function openTable() private returns(Table){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_government");
        return table;
    }

    /*
    描述 : 查询地区信息
    参数 ：
            regionName : 地区
    返回值：
            参数一： 成功返回0, 地区不存在返回-1
            参数二： 第一个参数为0时有效，
                    地区安全信息 :
                    0: 安全
                    1: 风险


    */

    function select(string regionName) public constant returns (int256, uint256, uint256){
        Table table = openTable();
        Entries entries = table.select(regionName, table.newCondition());
        uint256 is_dangerous = 0;
        uint256 date = 0;
        if(0 == uint256(entries.size())){
            return (-1, is_dangerous, date);
        }else{
            Entry entry = entries.get(0);
            is_dangerous = uint256(entry.getInt("isDangerous"));
            date = uint256(entry.getInt("date"));
            return(0, is_dangerous, date);
        }
    }




    /*
    描述 : The government upload region info
    参数 ：
            regionName : region
            isDangerous : 地区安全信息 :
                    0: 安全
                    1: 中风险
            date: 时间

    返回值：
            Success return 0, failed return -1
    */

    function upload_region_info (string regionName, uint256 isDangerous, uint256 date) public returns(int256){
        int256 ret_code = 0;
        int256 ret = 0;
        uint256 isDangerous_temp = 0;
        uint256 date_temp = 0;
        (ret, isDangerous_temp, date_temp) = select(regionName);
        Table table = openTable();
        Entry entry = table.newEntry();
        entry.set("regionName", regionName);
        entry.set("isDangerous", isDangerous);
        entry.set("date", date);
        int256 count = -1;
        if(ret == 0){
            //Region exists
            count = table.update(regionName, entry, table.newCondition());
            if(count ==1){
            	ret_code = 1;
            }else{
            	ret_code = -2;
            }
        }else{
            //Region doesn't exist
            count = table.insert(regionName, entry);
            if(count == 1){
            	ret_code = 0;
            }else{
            	ret_code = -2;
            }
        }
        return ret_code;
    }
}