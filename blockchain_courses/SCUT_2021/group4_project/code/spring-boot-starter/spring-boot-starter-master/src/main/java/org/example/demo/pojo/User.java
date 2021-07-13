package org.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

//    @Column(name="username")
    String username;
//    @Column(name="password")
    String password;
//    @Column(name="address")
    String address;
//    @Column(name="privatekey")
    String privatekey;
//    @Column(name="publickey")
    String publickey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPrivateKey(String privateKey) {
        this.privatekey = privateKey;
    }

    public String getPrivateKey() {
        return privatekey;
    }

    public void setPublicKey(String publicKey) {
        this.publickey = publicKey;
    }

    public String getPublicKey() {
        return publickey;
    }
}