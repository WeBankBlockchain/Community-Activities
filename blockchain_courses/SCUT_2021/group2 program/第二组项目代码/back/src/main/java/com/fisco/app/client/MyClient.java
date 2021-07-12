package com.fisco.app.client;

import com.fisco.app.common.CommonClient;
import com.fisco.app.contract.TestCRUD;
import com.fisco.app.contract.UserManager;
import com.fisco.app.utils.SpringUtils;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname CRUDClient
 * @Description sdk客户端，可以不使用ApplicationRunner，这里是让spring容器启动部署合约
 * @Date 2021/3/25 21:45
 * @Created by zyt
 */
@Service
public class MyClient extends CommonClient implements ApplicationRunner {

    public static final Logger logger = LoggerFactory.getLogger(MyClient.class.getName());

    public void insert(String ethAddr, String userName, String password) {
        UserManager userManager = (UserManager) getContractMap().get("UserManager");
        TransactionReceipt receipt = userManager.register(ethAddr,userName,password);
        logger.info("调用MyClient的insert方法");
        logger.info("结果：{}", receipt);

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
        //load("UserManager", UserManager.class, sdk, 1);
    }
}
