/*
pragma solidity ^0.4.24;
import "./Table.sol";

contract util {
    constructor() public {
        
    }
    
    function query(string table_name, string primary_key) returns(int, Entry){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable(table_name);
        Entries entries = table.select(primary_key, table.newCondition());
        int result = 0;
        if (0 == uint256(entries.size())) {
            return (-1, table.newEntry());
        } else {
            return (0, entries.get(0));
        }
    }
}
*/
pragma solidity ^0.4.24;
import "./Table.sol";

contract util {
    constructor() public {

    }
    /*
        描述 : 查询表信息
        参数 ：
                table_name : 表名
                primary_key ：主键名
        返回值：
                参数一： 存在返回0, 不存在返回-1
                参数二： json格式：如id：23131， name：tony,


        */
    function query(string table_name, string primary_key) returns(int, string){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable(table_name);
        Entries entries = table.select(primary_key, table.newCondition());
        if (0 == uint256(entries.size())) {
            string memory nullString = "";
            return (-1, nullString);
        } else {
            string memory result;
            Entry entry = entries.get(0);
            if(compare(table_name, "t_hospital")){
                // var idString = stringToJson("id", entry.getString("id"));
                // var nameString = stringToJson("name", entry.getString("name"));
                // var resultString = stringToJson("result", intToString(entry.getInt("result")));
                // var dateString = stringToJson("date", intToString(entry.getInt("date")));

                // result = concat(idString, nameString);
                // result = concat(result, resultString);
                // result = concat(result, dateString);
                result = toHospital(entry.getString("id"), entry.getString("name"), entry.getInt("result"), entry.getInt("date"));
            }
            if(compare(table_name, "t_government")){
                // var regionNameString = stringToJson("regionName", entry.getString("regionName"));
                // var isDangerousString = stringToJson("isDangerous", intToString(entry.getInt("isDangerous")));
                // var datesString = stringToJson("date", intToString(entry.getInt("date")));

                // result = concat(regionNameString, isDangerousString);
                // result = concat(result, datesString);
                result = togovernment(entry.getString("regionName"), entry.getInt("isDangerous"), entry.getInt("date"));
            }
            if(compare(table_name, "t_health")){
                // var userHealthHashString = stringToJson("userHealthHash", entry.getString("userHealthHash"));
                // var isHealthString = stringToJson("isHealth", intToString(entry.getInt("isHealth")));
                // var validPeriodString = stringToJson("validPeriod", intToString(entry.getInt("validPeriod")));

                // result = concat(userHealthHashString, isHealthString);
                // result = concat(result, validPeriodString);
                result = tohealth(entry.getString("userHealthHash"), entry.getInt("isHealth"), (entry.getInt("validPeriod")));

            }
            if(compare(table_name, "t_user")){
                // var id2String = stringToJson("id", entry.getString("id"));
                // var name2String = stringToJson("name", entry.getString("name"));
                // var residenceString = stringToJson("residence", entry.getString("residence"));

                // result = concat(id2String, name2String);
                // result = concat(result, residenceString);
               result = touser(entry.getString("id"), entry.getString("name"), entry.getString("residence"));
            }
            return(0, result);
        }
    }
    function toHospital(string id, string name, int result, int date) returns(string){
        var idString = stringToJson("id", id);
        var nameString = stringToJson("name",name);
        var resultString = stringToJson("result", intToString(result));
        var dateString = stringToJson("date", intToString(date));

        var result1 = concat(idString, nameString);
        result1 = concat(result1, resultString);
        result1 = concat(result1, dateString);
        return result1;
    }

    function togovernment(string id, int isDangerous, int date) returns(string){
        var idString = stringToJson("id", id);
        var isDangerousString = stringToJson("isDangerous",intToString(isDangerous));
        var dateString = stringToJson("date", intToString(date));

        var result1 = concat(idString, isDangerousString);
        result1 = concat(result1, dateString);
        return result1;
    }

     function tohealth(string userHealthHash, int isHealth, int validPeriod) returns(string){
        var userHealthHashString = stringToJson("userHealthHash", userHealthHash);
        var isHealthString = stringToJson("isHealth",intToString(isHealth));
        var validPeriodString = stringToJson("validPeriod", intToString(validPeriod));

        var result1 = concat(userHealthHashString, isHealthString);
        result1 = concat(result1, validPeriodString);
        return result1;
    }

     function touser(string id, string name, string residence) returns(string){
        var idString = stringToJson("id", id);
        var nameString = stringToJson("name", (name));
        var residenceString = stringToJson("residence", (residence));

        var result1 = concat(idString, nameString);
        result1 = concat(result1, residenceString);
        return result1;
    }

    function intToString(int v) constant returns (string str) {
        uint maxlength = 100;
        bytes memory reversed = new bytes(maxlength);
        uint i = 0;
        while (v != 0) {
            int remainder = v % 10;
            v = v / 10;
            reversed[i++] = byte(48 + remainder);
        }
        bytes memory s = new bytes(i);
        for (uint j = 0; j < i; j++) {
            s[j] = reversed[i - 1 - j];
        }
        str = string(s);
    }

    function stringToJson(string firstString, string secondString) returns(string){
        string memory result = concat(firstString, ": ");
        result = concat(result, secondString);
        result = concat(result, ", ");
        return result;
    }

    function compare(string firstString, string secondString) returns(bool){
        bytes memory firstBytes = bytes(firstString);
        bytes memory secondBytes = bytes(secondString);
        if(firstBytes.length == secondBytes.length){
            for(uint i=0; i<firstBytes.length; i++){
                if(firstBytes[i] != secondBytes[i])
                    return false;
            }
            return true;
        }else
            return false;
    }

    function concat(string firstString, string secondString) returns (string){
        bytes memory firstBytes = bytes(firstString);
        bytes memory secondBytes = bytes(secondString);

        string memory temp = new string(firstBytes.length + secondBytes.length);
        bytes memory result = bytes(temp);

        uint indexForResult = 0;
        for(uint i=0; i<firstBytes.length; i++){
            result[indexForResult] = firstBytes[i];
            indexForResult++;
        }
        for(uint j=0;j<secondBytes.length; j++){
            result[indexForResult] = secondBytes[j];
            indexForResult++;
        }

        return string(result);

    }

}
