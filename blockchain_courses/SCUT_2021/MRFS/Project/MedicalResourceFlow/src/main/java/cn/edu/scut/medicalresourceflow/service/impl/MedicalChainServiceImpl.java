package cn.edu.scut.medicalresourceflow.service.impl;

import cn.edu.scut.medicalresourceflow.contract.MedicalChain;
import cn.edu.scut.medicalresourceflow.entity.Account;
import cn.edu.scut.medicalresourceflow.entity.Transaction;
import cn.edu.scut.medicalresourceflow.entity.User;
import cn.edu.scut.medicalresourceflow.entity.bo.ResourceBO;
import cn.edu.scut.medicalresourceflow.entity.dto.ResourceDTO;
import cn.edu.scut.medicalresourceflow.entity.dto.TransactionDTO;
import cn.edu.scut.medicalresourceflow.exception.BusinessException;
import cn.edu.scut.medicalresourceflow.service.AccountService;
import cn.edu.scut.medicalresourceflow.service.MedicalChainService;
import cn.edu.scut.medicalresourceflow.util.ErrorCode;
import cn.edu.scut.medicalresourceflow.util.RoleUtil;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.*;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MedicalChainServiceImpl implements MedicalChainService {

    @Resource
    AccountService accountService;

    @Value("${static.contract-address}")
    private String contractAddress ;

    @Resource
    Client client;

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    MedicalChain getMedicalChain(Account account){
        CryptoKeyPair keyPair= accountService.getKeyPairFromAccount(account);
        MedicalChain medicalChain = MedicalChain.load(
                contractAddress,
                client,
                keyPair
        );
        return medicalChain;
    }

    MedicalChain getMedicalChain(){
        //System.out.println(contractAddress);
        CryptoKeyPair keyPair= accountService.getOwnerKeyPair();
        MedicalChain medicalChain = MedicalChain.load(
                contractAddress,
                client,
                keyPair
        );
        return medicalChain;
    }


    @Override
    public void registerUser(Account account, User user) {
        MedicalChain medicalChain = getMedicalChain(account);
        TransactionReceipt receipt = medicalChain.registerUser(
                user.getName(),
                user.getProvince()
        );
        BigInteger rnt = medicalChain.getRegisterUserOutput(receipt).getValue1();
        if (!rnt.equals(BigInteger.ONE))
            throw new BusinessException(ErrorCode.CUSTOMIZE_USER_EXISTED);
    }

    @Override
    public void changeRole(Account account, RoleUtil role) {
        MedicalChain medicalChain = getMedicalChain();
        TransactionReceipt receipt = medicalChain.changeRole(account.getAddress(), role.getRole());
        BigInteger rnt = medicalChain.getChangeRoleOutput(receipt).getValue1();
        if (!rnt.equals(BigInteger.ONE))
            throw new BusinessException(ErrorCode.CUSTOMIZE_UNAUTHORIZED, "Is is not the god.");
    }

    @Override
    public void registerResource(Account account,ResourceBO resource) {
        MedicalChain medicalChain = getMedicalChain(account);
        TransactionReceipt receipt = medicalChain.registerResource(
                resource.getResourceNum(),
                resource.getResourceName(),
                resource.getCategory(),
                resource.getBatchCode(),
                resource.getImgUrl(),
                df.format(new Date()),
                resource.getInfo()
        );
        BigInteger rnt = medicalChain.getRegisterResourceOutput(receipt).getValue1();
        if (!rnt.equals(BigInteger.ONE))
            throw new BusinessException(ErrorCode.CUSTOMIZE_UNAUTHORIZED,"It is not the factory.");
    }

    @Override
    public void transferResource(Account from, Account to, BigInteger resourceId, String info) {
        MedicalChain medicalChain = getMedicalChain(from);
        TransactionReceipt receipt = medicalChain.transferResource(
                to.getAddress(),
                resourceId,
                df.format(new Date()),
                info);
        BigInteger rnt = medicalChain.getTransferResourceOutput(receipt).getValue1();
        if (rnt.equals(BigInteger.valueOf(-1))){
            throw new BusinessException(ErrorCode.CUSTOMIZE_USER_NOT_EXISTED,"The account transfer to is not existed.");
        }else if (rnt.equals(BigInteger.valueOf(-2))){
            throw new BusinessException(ErrorCode.CUSTOMIZE_RESOURCE_UNABLE);
        }else if (rnt.equals(BigInteger.valueOf(-3))){
            throw new BusinessException(ErrorCode.CUSTOMIZE_RESOURCE_NOT_AVAILABLE);
        }
    }

    @Override
    public void deleteResource(Account account, BigInteger resourceId, String info) {
        MedicalChain medicalChain = getMedicalChain(account);
        TransactionReceipt receipt = medicalChain.deleteResource(
                resourceId,
                df.format(new Date()),
                info
        );
        BigInteger rnt = medicalChain.getTransferResourceOutput(receipt).getValue1();
        if (rnt.equals(BigInteger.valueOf(-1))){
            throw new BusinessException(ErrorCode.CUSTOMIZE_USER_NOT_EXISTED,"The account transfer to is not existed or it is not the hospital");
        }else if (rnt.equals(BigInteger.valueOf(-2))){
            throw new BusinessException(ErrorCode.CUSTOMIZE_RESOURCE_UNABLE);
        }else if (rnt.equals(BigInteger.valueOf(-3))){
            throw new BusinessException(ErrorCode.CUSTOMIZE_RESOURCE_NOT_AVAILABLE);
        }
    }

    @Override
    public List<TransactionDTO> getTransactionListByResourceId(BigInteger resourceId) {
        MedicalChain medicalChain = getMedicalChain();
        List<TransactionDTO> result = new ArrayList<>();
        try{
            Tuple4<List<String>, List<String>, List<String>, List<String>> rnt = medicalChain.getLogByRid(resourceId);
            List<String> froms = rnt.getValue1();
            List<String> tos = rnt.getValue2();
            List<String> times = rnt.getValue3();
            List<String> infos = rnt.getValue4();
            for (int i=0;i<froms.size();i++){
                result.add(
                        new TransactionDTO(
                                accountService.getAccountDTOByAddress(froms.get(i)),
                                accountService.getAccountDTOByAddress(tos.get(i)),
                                null,
                                times.get(i),
                                infos.get(i)
                        )
                );
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public List<ResourceBO> getMyResources(Account account) {
        CryptoKeyPair keyPair = accountService.getKeyPairFromAccount(account);
        MedicalChain medicalChain = getMedicalChain(account);
        List<ResourceBO> resources = new ArrayList<>();
        TransactionReceipt receipt = medicalChain.getMyResourcesInfo();

        Tuple7<List<BigInteger>, List<String>, List<BigInteger>, List<String>, List<String>, List<String>, List<Boolean>>
            rnt = medicalChain.getGetMyResourcesInfoOutput(receipt);
        List<BigInteger> resourceIds = rnt.getValue1();
        List<String> provinces = rnt.getValue2();
        List<BigInteger> resourceNums = rnt.getValue3();
        List<String> resourceName = rnt.getValue4();
        List<String> resourceCategory = rnt.getValue5();
        List<String> resourceBatchCode = rnt.getValue6();
        List<Boolean> isUsed = rnt.getValue7();
        for (int i=0;i<resourceIds.size();i++){
            resources.add(
                    new ResourceBO(
                            keyPair.getAddress(),
                            resourceIds.get(i),
                            resourceNums.get(i),
                            resourceName.get(i),
                            resourceCategory.get(i),
                            resourceBatchCode.get(i),
                            isUsed.get(i),
                            provinces.get(i),
                            null,
                            null
                    )
            );

        }
        return resources;
    }

    @Override
    public List<ResourceDTO> getAllResourceInfoByCategory(String category) {
        MedicalChain medicalChain = getMedicalChain();
        List<ResourceDTO> resources = new ArrayList<>();
        TransactionReceipt receipt = medicalChain.getAllResourcesInfoByCategory(category);
        Tuple7<List<String>,List<BigInteger>, List<String>, List<BigInteger>, List<String>, List<String>, List<Boolean>> rnt
                = medicalChain.getGetAllResourcesInfoByCategoryOutput(receipt);
        List<String> ownerAddress = rnt.getValue1();
        List<BigInteger> resourceId = rnt.getValue2();
        List<String> resourceProvince = rnt.getValue3();
        List<BigInteger> resourceNum = rnt.getValue4();
        List<String> resourceName = rnt.getValue5();
        List<String> resourceBatchCode = rnt.getValue6();
        List<Boolean> isUsed = rnt.getValue7();
        for (int i=0;i<ownerAddress.size();i++){
            resources.add(
                    new ResourceDTO(
                            accountService.getAccountDTOByAddress(ownerAddress.get(i)),
                            resourceId.get(i),
                            resourceNum.get(i),
                            resourceName.get(i),
                            category,
                            resourceBatchCode.get(i),
                            isUsed.get(i),
                            resourceProvince.get(i),
                            null
                    )
            );
        }
        return resources;

    }

    @Override
    public List<ResourceDTO> getAllResourceInfoByProvince(String province) {
        MedicalChain medicalChain = getMedicalChain();
        List<ResourceDTO> resources = new ArrayList<>();
        TransactionReceipt receipt = medicalChain.getAllResourcesInfoByLocation(province);
        Tuple7<List<String>, List<BigInteger>, List<BigInteger>, List<String>, List<String>, List<String>, List<Boolean>> rnt =
                medicalChain.getGetAllResourcesInfoByLocationOutput(receipt);
        List<String> ownerAddress = rnt.getValue1();
        List<BigInteger> resourceId = rnt.getValue2();
        List<BigInteger> resourceNum = rnt.getValue3();
        List<String> resourceName = rnt.getValue4();
        List<String> category = rnt.getValue5();
        List<String> resourceBatchCode = rnt.getValue6();
        List<Boolean> isUsed = rnt.getValue7();
        for (int i=0;i<ownerAddress.size();i++){
            resources.add(
                    new ResourceDTO(
                            accountService.getAccountDTOByAddress(ownerAddress.get(i)),
                            resourceId.get(i),
                            resourceNum.get(i),
                            resourceName.get(i),
                            category.get(i),
                            resourceBatchCode.get(i),
                            isUsed.get(i),
                            province,
                            null
                    )
            );
        }
        return resources;
    }

    @Override
    public List<ResourceDTO> getAllResourceInfo() {
        MedicalChain medicalChain = getMedicalChain();
        List<ResourceDTO> resources = new ArrayList<>();
        TransactionReceipt receipt = medicalChain.getAllResourcesInfo();
        Tuple8<List<String>, List<BigInteger>, List<String>, List<BigInteger>, List<String>, List<String>, List<String>, List<Boolean>> rnt =
                medicalChain.getGetAllResourcesInfoOutput(receipt);
        List<String> ownerAddress = rnt.getValue1();
        List<BigInteger> resourceId = rnt.getValue2();
        List<String> resourceProvince = rnt.getValue3();
        List<BigInteger> resourceNum = rnt.getValue4();
        List<String> resourceName = rnt.getValue5();
        List<String> category = rnt.getValue6();
        List<String> resourceBatchCode = rnt.getValue7();
        List<Boolean> isUsed = rnt.getValue8();
        for (int i=0;i<ownerAddress.size();i++){
            resources.add(
                    new ResourceDTO(
                            accountService.getAccountDTOByAddress(ownerAddress.get(i)),
                            resourceId.get(i),
                            resourceNum.get(i),
                            resourceName.get(i),
                            category.get(i),
                            resourceBatchCode.get(i),
                            isUsed.get(i),
                            resourceProvince.get(i),
                            null
                    )
            );
        }
        return resources;

    }

    @Override
    public BigInteger getResourceCount() {
        MedicalChain medicalChain = getMedicalChain();
        TransactionReceipt receipt = medicalChain.getAllResourcesCnt();
        BigInteger cnt = medicalChain.getGetAllResourcesCntOutput(receipt).getValue1();
        return cnt;
    }

    @Override
    public BigInteger getNotUsedResourceCount() {
        MedicalChain medicalChain = getMedicalChain();
        TransactionReceipt receipt = medicalChain.getNotUsedResourcesCnt();
        BigInteger cnt = medicalChain.getGetNotUsedResourcesCntOutput(receipt).getValue1();
        return cnt;
    }

    @Override
    public BigInteger getAllResourceCntByProvince(String province) {
        MedicalChain medicalChain = getMedicalChain();
        TransactionReceipt receipt = medicalChain.getAllResourcesCntByLocation(province);
        BigInteger cnt = medicalChain.getGetAllResourcesCntByLocationOutput(receipt).getValue1();
        return cnt;
    }

    @Override
    public BigInteger getNotUsedResourceCntByProvince(String province) {
        MedicalChain medicalChain = getMedicalChain();
        TransactionReceipt receipt = medicalChain.getNotUsedResourcesCntByLocation(province);
        BigInteger cnt = medicalChain.getGetNotUsedResourcesCntByLocationOutput(receipt).getValue1();
        return cnt;
    }

    //    @Override
//    public Transaction getTransaction(Account account, BigInteger transactionId) {
//        Transaction transaction;
//        try {
//            MedicalChain medicalChain = getMedicalChain(account);
//            Tuple5<String, String, BigInteger, String, String> rnt = medicalChain.getTransaction(transactionId);
//            transaction = new Transaction(
//                    rnt.getValue1(),
//                    rnt.getValue2(),
//                    rnt.getValue3(),
//                    rnt.getValue4(),
//                    rnt.getValue5()
//            );
//        }catch (ContractException ex){
//            ex.printStackTrace();
//            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON,"Unkown Error.");
//        }
//        return transaction;
//    }
}
