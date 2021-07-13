package com.fisco.app.client;

import com.fisco.app.common.CommonClient;
import com.fisco.app.contract.OwnContract;
import com.fisco.app.contract.TestCRUD;
import com.fisco.app.contract.UserManager;
import com.fisco.app.pojo.Own;
import com.fisco.app.utils.SpringUtils;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @Classname OwnClient
 * @Description sdk客户端，可以不使用ApplicationRunner，这里是让spring容器启动部署合约
 * @Date 2021/3/25 21:45
 * @Created by zyt
 */
@Service
public class OwnClient extends CommonClient implements ApplicationRunner {

    public static final Logger logger = LoggerFactory.getLogger(OwnClient.class.getName());

    public void addOwn(String userId, String certId, String content) {
        OwnContract ownContract = (OwnContract) getContractMap().get("OwnContract");
        Date now = new Date();
        BigInteger timestamp = BigInteger.valueOf(now.getTime());
        TransactionReceipt receipt = ownContract.newOwn(userId, certId, content, timestamp);
        logger.info("调用OwnClient的insert方法");
        logger.info("结果：{}", receipt);
    }




    public Boolean IsExist(String userId, String certId) {
        //TransactionReceipt getOwnByUserIdAndCertId(String userid, String certid)
        OwnContract ownContract = (OwnContract) getContractMap().get("OwnContract");
        TransactionReceipt result = ownContract.getOwnByUserIdAndCertId(userId, certId);
        logger.info("调用OwnContract的query方法");
        Tuple1<Boolean> flag = ownContract.getGetOwnByUserIdAndCertIdOutput(result);
        logger.info("结果：{}", flag.getValue1());
        return flag.getValue1();
    }
    public Tuple2 getUserInfoByUserName(String userName) throws ContractException {

        UserManager userManager = (UserManager) getContractMap().get("UserManager");
        Tuple2<String, String> getValue = userManager.getUserInfoByUserName(userName);
        logger.info("调用CRUDClient的query方法");
        logger.info("结果：{}", getValue);
        return getValue;

    }

    public void edit(String name, String age, String tel) {

        TestCRUD testCRUD = (TestCRUD) getContractMap().get("TestCRUD");
        TransactionReceipt receipt = testCRUD.update(name, age, tel);
        logger.info("调用CRUDClient的edit方法");
        logger.info("结果：{}", receipt);

    }

    @Override
    public void remove(String name) {

        TestCRUD testCRUD = (TestCRUD) getContractMap().get("TestCRUD");
        TransactionReceipt receipt = testCRUD.remove(name);
        logger.info("调用CRUDClient的remove方法");
        logger.info("结果：{}", receipt);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BcosSDK sdk = SpringUtils.getBean("bcosSDK");
        load("OwnContract", OwnContract.class, sdk, 1);
    }
}
