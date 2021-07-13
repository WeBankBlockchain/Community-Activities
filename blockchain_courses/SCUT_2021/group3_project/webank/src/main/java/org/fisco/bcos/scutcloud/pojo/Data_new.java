package org.fisco.bcos.scutcloud.pojo;

import java.math.BigInteger;
import java.util.List;

public class Data_new {
    private String userName;

    private BigInteger id;

    private BigInteger credit;

    private BigInteger downloadTimes;

    private String dataName;

    private String dataDescription;

    private String address;

    private String type;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getCredit() {
        return credit;
    }

    public void setCredit(BigInteger credit) {
        this.credit = credit;
    }



    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataDescription() {
        return dataDescription;
    }

    public void setDataDescription(String dataDescription) {
        this.dataDescription = dataDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigInteger getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(BigInteger downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public Data_new(String userName, BigInteger id, BigInteger credit, BigInteger downloadTimes, String dataName, String dataDescription, String address, String type) {
        this.userName = userName;
        this.id = id;
        this.credit = credit;
        this.downloadTimes = downloadTimes;
        this.dataName = dataName;
        this.dataDescription = dataDescription;
        this.address = address;
        this.type = type;
    }

//    public Data_new(String userName, BigInteger ownerId, BigInteger id, BigInteger credit,
//                    List<BigInteger> not, String dataName, String dataDescription,
//                    String address, String type) {
//        this.userName = userName;
//        this.ownerId = ownerId;
//        this.id = id;
//        this.credit = credit;
//        this.not = null;
//        this.dataName = dataName;
//        this.dataDescription = dataDescription;
//        this.address = address;
//        this.type = type;
//    }
}
