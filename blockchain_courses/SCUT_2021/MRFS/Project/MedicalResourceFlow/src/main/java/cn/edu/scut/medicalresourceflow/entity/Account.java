package cn.edu.scut.medicalresourceflow.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * account
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    /**
     * account_id
     */
    private Integer accountId;

    /**
     * user_id
     */
    private Integer userId;

    /**
     * 账户address
     */
    private String address;

    /**
     * 账户public_key
     */
    private String publicKey;

    /**
     * 账户private_key
     */
    private String privateKey;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 修改时间
     */
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    public Account(String address,String publicKey,String privateKey){
        this.setAddress(address);
        this.setPublicKey(publicKey);
        this.setPrivateKey(privateKey);
    }
}