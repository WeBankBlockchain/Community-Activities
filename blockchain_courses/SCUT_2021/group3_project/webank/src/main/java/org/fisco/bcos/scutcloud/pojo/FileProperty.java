package org.fisco.bcos.scutcloud.pojo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

// import org.springframework.boot.context.properties.ConfigurationProperties;

// @ConfigurationProperties(prefix = "file")
public class FileProperty {
    private BigInteger ownerId;

    private BigInteger credit;

    private List<BigInteger> not;
    
    private String dataName;

    private String dataDescription;

    private String address;

    private String type;
    
    private MultipartFile file;

    public BigInteger getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(BigInteger ownerId) {
        this.ownerId = ownerId;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public FileProperty(BigInteger ownerId, BigInteger credit, String dataName,
            String dataDescription, String address, String type, MultipartFile file) {
        this.ownerId = ownerId;
        this.credit = credit;
        this.not = null;
        this.dataName = dataName;
        this.dataDescription = dataDescription;
        this.address = address;
        this.type = type;
        this.file = file;
    }

}
