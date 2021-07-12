package org.fisco.bcos.scutcloud.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;

@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class User {
    private BigInteger _id;

    private int credit;

    private String userName;

    private String pwd;

    private String description;

    private String address;


    public User(BigInteger id, int credit, String username, String pd, String des, String addr){
        this._id = id;
        this.credit = credit;
        this.userName = username;
        this.pwd = pd;
        this.description = des;
        this.address = addr;
    }

    public BigInteger get_id() {
        return _id;
    }

    public void set_id(BigInteger id) {
        this._id = id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
