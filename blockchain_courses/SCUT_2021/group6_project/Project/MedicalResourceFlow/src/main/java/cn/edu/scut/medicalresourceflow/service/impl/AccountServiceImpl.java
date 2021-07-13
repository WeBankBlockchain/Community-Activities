package cn.edu.scut.medicalresourceflow.service.impl;

import cn.edu.scut.medicalresourceflow.dao.AccountDao;
import cn.edu.scut.medicalresourceflow.entity.Account;
import cn.edu.scut.medicalresourceflow.entity.dto.AccountDTO;
import cn.edu.scut.medicalresourceflow.service.AccountService;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${static.contract-owner-private-key}")
    private String ownerPrivateKey;

    @Resource
    AccountDao accountDao;

    private static final CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);

    private CryptoKeyPair ownerKeyPair;

    @Override
    public Account createAccount() {
        // 创建非国密类型的CryptoSuite
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        // 随机生成非国密公私钥对
        CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair();

        Account account = new Account(
                cryptoKeyPair.getAddress(),
                cryptoKeyPair.getHexPublicKey(),
                cryptoKeyPair.getHexPrivateKey());
        return account;
    }

    @Override
    public Account getAccountFromPrivateKey(String privateKey) {
        ECDSAKeyPair ecdsaKeyPair= new ECDSAKeyPair();
        ecdsaKeyPair.createKeyPair(privateKey);

        Account account = new Account(
                ecdsaKeyPair.getAddress(),
                ecdsaKeyPair.getHexPublicKey(),
                ecdsaKeyPair.getHexPrivateKey()
        );
        return account;
    }

    @Override
    public int deleteByPrimaryKey(Integer accountId) {
        return accountDao.deleteByPrimaryKey(accountId);
    }

    @Override
    public int insertSelective(Account record) {
        return accountDao.insertSelective(record);
    }

    @Override
    public Account selectByPrimaryKey(Integer accountId) {
        return accountDao.selectByPrimaryKey(accountId);
    }

    @Override
    public Account selectByUserId(Integer userId) {
        return accountDao.selectByUserId(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(Account record) {
        return accountDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public CryptoKeyPair getKeyPairFromAccount(Account account) {
        if (account == null || account.getPrivateKey().isEmpty()) return null;
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair(account.getPrivateKey());
        return keyPair;
    }

    @Override
    public CryptoKeyPair getKeyPairFromPrivateKey(String privateKey) {
        if (privateKey == null || privateKey.isEmpty()) return null;
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair(privateKey);
        return keyPair;
    }

    @Override
    public CryptoKeyPair getOwnerKeyPair() {
        if (ownerKeyPair == null){
            synchronized (this){
                ownerKeyPair = cryptoSuite.createKeyPair(ownerPrivateKey);
            }
        }
        return ownerKeyPair;
    }

    @Override
    public AccountDTO getAccountDTOByAddress(String address) {
        return accountDao.getAccountDTOByAddress(address);
    }
}
