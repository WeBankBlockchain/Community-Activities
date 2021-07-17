
const mysql = require('mysql')
const express = require('express')
const models = require('../db')

const router = express.Router()

const $sql = require('../sqlMap')
// 连接数据库
const conn = mysql.createConnection(models.mysql)
conn.connect()
const jsonWrite = function (res, ret) {
    if (typeof ret === 'undefined') {
        res.json({
            code: '1', msg: '操作失败',
        })
    } else {
        res.json(
            ret
        )
    }
}
// 接口：增加信息
router.post('/addAcc', (req, res) => {
    const sql = $sql.Acc.add
    const params = req.body
    console.log('添加', params)
    // eslint-disable-next-line max-len
    conn.query(sql, [params.addressx, params.accountName, params.passwordx, params.publicKeyx, params.privateKeyx, params.balance, params.myRecordCount], (err, result) => {
        if (err) {
            console.log(err)
        }
        if (result) {
            jsonWrite(res, result)
        }
    })
})

// 接口：查询全部
router.get('/showAcc', (req, res) => {
    const sql = $sql.Acc.show
    const params = req.body
    console.log(params)
    // eslint-disable-next-line max-len
    conn.query(sql, [params.accountName, params.passwordx, params.publicKeyx, params.privateKeyx, params.balance, params.myRecordCount], (err, result) => {
        if (err) {
            console.log(err)
        }
        if (result) {
            jsonWrite(res, result)
        }
    })
})

// 接口：删除信息
router.post('/delAcc', (req, res) => {
    const sql = $sql.Acc.del
    const params = req.body
    console.log('删除', params)
    conn.query(sql, [params.addressx], (err, result) => {
        if (err) {
            console.log(err)
        }
        if (result) {
            jsonWrite(res, result)
        }
    })
})

// 接口：修改信息
router.post('/updateAcc', (req, res) => {
    const sql = $sql.Acc.update
    const params = req.body
    console.log('修改', params)
    // eslint-disable-next-line max-len
    conn.query(sql, [params.accountName, params.passwordx, params.publicKeyx, params.privateKeyx, params.balance, params.myRecordCount, params.addressx], (err, result) => {
        if (err) {
            console.log(err)
        }
        if (result) {
            jsonWrite(res, result)
        }
    })
})

module.exports = router
