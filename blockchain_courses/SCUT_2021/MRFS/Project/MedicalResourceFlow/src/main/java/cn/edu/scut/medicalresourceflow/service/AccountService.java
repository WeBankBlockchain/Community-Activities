package cn.edu.scut.medicalresourceflow.service;

import cn.edu.scut.medicalresourceflow.entity.Account;
import cn.edu.scut.medicalresourceflow.entity.dto.AccountDTO;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;

public interface AccountService {

    Account createAccount();

    Account getAccountFromPrivateKey(String privateKey);

    int deleteByPrimaryKey(Integer accountId);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer accountId);

    Account selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(Account record);

    CryptoKeyPair getKeyPairFromAccount(Account account);

    CryptoKeyPair getKeyPairFromPrivateKey(String privateKey);

    CryptoKeyPair getOwnerKeyPair();

    AccountDTO getAccountDTOByAddress(String address);
}
