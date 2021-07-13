package org.fisco.bcos.scutcloud.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;
import java.util.List;

@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Data {
    private BigInteger ownerId;

    private BigInteger id;

    private BigInteger credit;

    //private List<BigInteger> not = null;

    private String dataName;

    private String dataDescription;

    private String address;

    private String type;

//    public Data(BigInteger ownerId, BigInteger id, BigInteger credit,
//                List<BigInteger> not, String dataName, String dataDescription,
//                String address, String type) {
//        this.ownerId = ownerId;
//        this.id = id;
//        this.credit = credit;
//        this.not = not;
//        this.dataName = dataName;
//        this.dataDescription = dataDescription;
//        this.address = address;
//        this.type = type;
//    }
    public Data(BigInteger ownerId, BigInteger id, BigInteger credit,
                 String dataName, String dataDescription,
                String address, String type) {
        this.ownerId = ownerId;
        this.id = id;
        this.credit = credit;
        //this.not = null;
        this.dataName = dataName;
        this.dataDescription = dataDescription;
        this.address = address;
        this.type = type;
    }
    public BigInteger getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(BigInteger ownerId) {
        this.ownerId = ownerId;
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

//    public List<BigInteger> getNot() {
//        return not;
//    }
//
//    public void setNot(List<BigInteger> not) {
//        this.not = not;
//    }

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

}
