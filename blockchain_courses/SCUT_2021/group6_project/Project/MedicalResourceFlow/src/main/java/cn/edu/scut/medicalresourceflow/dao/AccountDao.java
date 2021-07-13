package cn.edu.scut.medicalresourceflow.dao;

import cn.edu.scut.medicalresourceflow.entity.Account;
import cn.edu.scut.medicalresourceflow.entity.dto.AccountDTO;

public interface AccountDao {
    int deleteByPrimaryKey(Integer accountId);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer accountId);

    Account selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    AccountDTO getAccountDTOByAddress(String address);
}