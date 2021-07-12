package org.fisco.bcos.scutcloud.pojo;

import java.math.BigInteger;

public class Request {
    private BigInteger userId;

    private BigInteger dataId;

    private String message;

    public Request(BigInteger userId, BigInteger dataId, String message) {
        this.userId = userId;
        this.dataId = dataId;
        this.message = message;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getDataId() {
        return dataId;
    }

    public void setDataId(BigInteger dataId) {
        this.dataId = dataId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
