// db.js
// 数据库连接配置
module.exports = {
    mysql: {
        host: 'localhost', // 新建数据库连接时的 主机名或ID地址 内容
        user: 'root',
        password: 'root', // root 密码
        database: 'accountdb', // 数据库名
        port: '3306', //
    }, // 这两个逗号真的要加吗
}
