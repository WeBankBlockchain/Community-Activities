package org.example.demo.utils;

import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;

public class KeyCreater {
    private String privateKey;
    private String publicKey;
    private String address;

    public KeyCreater() {
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair();
        this.privateKey = cryptoKeyPair.getHexPrivateKey();
        this.publicKey = cryptoKeyPair.getHexPublicKey();
        this.address = cryptoKeyPair.getAddress();
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getAddress() {
        return address;
    }
}
