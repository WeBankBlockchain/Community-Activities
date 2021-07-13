package org.example.demo;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest

public class test1 {
    @Test
    public void testClient() throws Exception {
        test a = new test();
     Client client= a.generateAccount();
     a.myBalance(client);

    }
}
