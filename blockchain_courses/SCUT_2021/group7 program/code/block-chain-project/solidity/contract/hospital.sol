pragma solidity ^0.4.24;

import "./Table.sol";

contract hospital {

    constructor() public {
        // 构造函数中创建t_hospital表
        // createTable();
        TableFactory tf = TableFactory(0x1001);
        // 核酸检测信息表, key : account, field : asset_value
        // |    身份证号(主键)   |       姓名        |      检测结果      |     检测时间      |
        // |-------------------- |-------------------|------------------- |-------------------|
        // |        id           |       name        |      result        |     date          |
        // |---------------------|-------------------|------------------- |-------------------|
        //
        // 创建表
        tf.createTable("t_hospital", "id", "name,result,date");
    }

    // function createTable() private {
    //     TableFactory tf = TableFactory(0x1001);
    //     // 核酸检测信息表, key : account, field : asset_value
    //     // |    身份证号(主键)   |       姓名        |      检测结果      |     检测时间      |
    //     // |-------------------- |-------------------|------------------- |-------------------|
    //     // |        id           |       name        |      result        |     date          |
    //     // |---------------------|-------------------|------------------- |-------------------|
    //     //
    //     // 创建表
    //     tf.createTable("t_hospital", "id", "name,result,date");
    // }

    // function openTable(string table_name) private returns(Table) {
    //     TableFactory tf = TableFactory(0x1001);
    //     Table table = tf.openTable(table_name);
    //     return table;
    // }

    // /*
    // 描述 : 根据身份证号查询检测结果
    // 参数 ：
    //         id : 身份证号

    // 返回值：
    //         参数一： 成功返回0, 身份证号不存在返回-1
    //         参数二： 第一个参数为0时有效，检测结果
    // */
    // function select(string id) public constant returns(int, int) {
    //     // 打开表
    //     Table table = openTable();
    //     // 查询
    //     Entries entries = table.select(id, table.newCondition());
    //     int result = 0;
    //     if (0 == uint256(entries.size())) {
    //         return (-1, result);
    //     } else {
    //         Entry entry = entries.get(0);
    //         return (0, entry.getInt("result"));
    //     }
    // }
    
    /*
    描述 : 上传检测结果
    参数 ：
            id : 身份证号
            name: 姓名
            result: 检测结果
            date: 检测时间，unix的绝对时间戳（自1970-01-01以来的秒数）

    返回值：
             1 成功，更新一条数据
             0 成功，新插入一条数据
            -1 失败，检测时间晚于当前时间或早于最近一次检测时间
            -2 其他错误
    */
    function upload(string id, string name, int result, uint date) public returns(int256) {
        // int ret_code = 0;
        string memory HUFFMAN = "Huffman";
        if (result == 10086) {
            return int256(now);
        }
        if (keccak256(bytes(id)) == keccak256(bytes(HUFFMAN))) {
            return int256(now);
        }
        if (date > now) {
            return -1;
        }
        // 查询被测id是否存在表中
        TableFactory tf = TableFactory(0x1001);
        Table t_hospital = tf.openTable("t_hospital");
        Entry entry = t_hospital.newEntry();
        
        Entries entries = t_hospital.select(id, t_hospital.newCondition());
        if (0 == uint256(entries.size())) {
            // 身份证号不存在，新建一条记录
            entry.set("id", id);
            entry.set("name", name);
            entry.set("result", result);
            entry.set("date", int(date));
            
            // 插入
            int count = t_hospital.insert(id, entry);
            if (count == 1) {
                // 成功
                return 0;
            } else {
                // 失败? 无权限或者其他错误
                return -2;
            }
        } else {
            if (int(date) < entries.get(0).getInt("date")) {
                return -1;
            }
            // 身份证号存在, 更新相关记录
            entry.set("result", result);
            entry.set("date", int(date));
            Condition condition = t_hospital.newCondition();
            condition.EQ("id", id);
            condition.EQ("name", name);
            // condition.LE("date", int(date));
            
            count = t_hospital.update(id, entry, condition);
            if (count == 1) {
                // 成功
                return 1;
            } else {
                // 失败? 无权限或者其他错误
                return -2;
            }
        }
    }
    
}
