// sqlMap.js  sql语句
const sqlMap = {
    Acc: {
        add: 'insert into account(addressx,accountName,passwordx,publicKeyx,privateKeyx,balance,myRecordCount) values (?,?,?,?,?,?,?)',
        show: 'select * from account',
        del: 'delete from account where addressx = ?',
        // eslint-disable-next-line max-len
        update: 'update account set accountName = ?,passwordx = ?,publicKeyx = ?,privateKeyx = ?,balance = ?,myRecordCount =? where addressx = ?',
    },
}

module.exports = sqlMap
