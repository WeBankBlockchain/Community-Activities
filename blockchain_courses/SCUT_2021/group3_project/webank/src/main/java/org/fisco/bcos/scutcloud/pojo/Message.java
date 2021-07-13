package org.fisco.bcos.scutcloud.pojo;

import java.math.BigInteger;

public class Message {
    private BigInteger reqId;

    private BigInteger resId;

    private BigInteger freeId;

    private BigInteger dataId;

    private BigInteger status;

    private String text;

    public Message(BigInteger reqId, BigInteger resId, BigInteger freeId, BigInteger dataId, BigInteger status, String text) {
        this.reqId = reqId;
        this.resId = resId;
        this.freeId = freeId;
        this.dataId = dataId;
        this.status = status;
        this.text = text;
    }

    public BigInteger getDataId() {
        return dataId;
    }

    public void setDataId(BigInteger dataId) {
        this.dataId = dataId;
    }

    public BigInteger getReqId() {
        return reqId;
    }

    public void setReqId(BigInteger reqId) {
        this.reqId = reqId;
    }

    public BigInteger getResId() {
        return resId;
    }

    public void setResId(BigInteger resId) {
        this.resId = resId;
    }

    public BigInteger getFreeId() {
        return freeId;
    }

    public void setFreeId(BigInteger freeId) {
        this.freeId = freeId;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
