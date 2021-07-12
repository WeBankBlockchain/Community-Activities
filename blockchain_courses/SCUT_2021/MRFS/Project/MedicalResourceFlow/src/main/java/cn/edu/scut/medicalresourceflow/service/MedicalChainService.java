package cn.edu.scut.medicalresourceflow.service;


import cn.edu.scut.medicalresourceflow.entity.Account;
import cn.edu.scut.medicalresourceflow.entity.Transaction;
import cn.edu.scut.medicalresourceflow.entity.User;
import cn.edu.scut.medicalresourceflow.entity.bo.ResourceBO;
import cn.edu.scut.medicalresourceflow.entity.dto.ResourceDTO;
import cn.edu.scut.medicalresourceflow.entity.dto.TransactionDTO;
import cn.edu.scut.medicalresourceflow.util.RoleUtil;

import javax.management.relation.Role;
import java.math.BigInteger;
import java.util.List;

public interface MedicalChainService {

    public void registerUser(Account account, User user);

    public void changeRole(Account account, RoleUtil role);

    public void registerResource(Account account,ResourceBO resource);

    public void transferResource(Account from, Account to, BigInteger resourceId, String info);

    public void deleteResource(Account account,BigInteger resourceId,String info);

    public List<TransactionDTO> getTransactionListByResourceId(BigInteger resourceId);

    public List<ResourceBO> getMyResources(Account account);

    public List<ResourceDTO> getAllResourceInfoByCategory(String category);

    public List<ResourceDTO> getAllResourceInfoByProvince(String province);

    public List<ResourceDTO> getAllResourceInfo();

    public BigInteger getResourceCount();

    public BigInteger getNotUsedResourceCount();

    public BigInteger getAllResourceCntByProvince(String province);

    public BigInteger getNotUsedResourceCntByProvince(String province);
}
