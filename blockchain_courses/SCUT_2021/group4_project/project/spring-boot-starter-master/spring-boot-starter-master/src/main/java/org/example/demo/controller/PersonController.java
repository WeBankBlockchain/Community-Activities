package org.example.demo.controller;
import com.alibaba.fastjson.JSON;
import org.example.demo.contracts.NTFMarket;
import  org.example.demo.contracts.Transaction;
import  org.example.demo.contracts.Account;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.service.ContractService;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple7;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.demo.response.Result;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/contract/person")
public class PersonController {

    @Autowired
    private ContractService service;
    @Autowired
    private Account person;

    @Autowired
    private banquanMarket market;

    @Autowired
    private Transaction transaction;

    /**
     *
     * @param
     * @return
     * @throws
     */
    @CrossOrigin
    @RequestMapping(value = "/setsender", method = RequestMethod.GET)
    public Result<?> setsenderAccount() throws Exception {
        if (service != null) {
            log.info("account contract address is: {}", service.getAccountContract().getContractAddress());
            TransactionReceipt tuple1 = service.getAccountContract().setsender();
            TransactionReceipt tuple2 =service.getNtfMarketContract().setsender();
            TransactionReceipt tuple3=service.getTransactionContract().setsender();
            return Result.data(tuple1);
        }
        return Result.fail("执行account合约失败");
    }
    //see how much money you have
    @CrossOrigin
    @RequestMapping(value = "/myBalance", method = RequestMethod.GET)
    public Result<?> balanceOf() throws Exception {
        if (service!= null) {
            log.info("account contract address is: {}", service.getAccountContract().getContractAddress());
            BigInteger tuple = service.getAccountContract().myBalance();
            return Result.data(tuple);
        }
        return Result.fail("执行account合约失败");
    }
    //get account address
    @CrossOrigin
    @RequestMapping(value = "/myAddress", method = RequestMethod.GET)
    public Result<?> myAddress() throws Exception {
        if (service != null) {
            log.info("account contract address is: {}", service.getAccountContract().getContractAddress());
            String tuple = service.getAccountContract().myAddress();
            return Result.data(tuple);
        }
        return Result.fail("执行account合约失败");
    }
    //transfer money to others
    @CrossOrigin
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public Result<?> transfer(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("account contract address is: {}", service.getAccountContract().getContractAddress());
            String address = (String)param.get("address");

            BigInteger money = BigInteger.valueOf((Integer) param.get("money"));

            TransactionReceipt receipt = service.getAccountContract().transfer(address,money);
            log.info("account contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行account合约失败");
    }

    //new account
    @CrossOrigin
    @RequestMapping(value = "/newAccount", method = RequestMethod.POST)
    public Result<?> newAccount(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("account contract address is: {}", service.getAccountContract().getContractAddress());
            String address = (String)param.get("address");

            BigInteger money = BigInteger.valueOf((Integer) param.get("money"));

            TransactionReceipt receipt = service.getAccountContract().newAccount(address,money);
            log.info("account contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行account合约失败");
    }
    @CrossOrigin
    @RequestMapping(value = "/newbanquan", method = RequestMethod.POST)
    public Result<?> newbanquan(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}", service.getbanquanMarketContract().getContractAddress());
            String disc = (String)param.get("disc");
            String name=(String)param.get("name");
            String picURl=(String)param.get("picURL");
            BigInteger kind = BigInteger.valueOf((Integer) param.get("kind"));

            TransactionReceipt receipt = service.getbanquanMarketContract().newbanquan(name,kind,disc,picURl);
            log.info("banquanMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行banquanMarket合约失败");
    }
    @CrossOrigin
    @RequestMapping(value = "/getMybanquans", method = RequestMethod.POST)
    public Result<?> getMybanquans() throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}", service.getbanquanMarketContract().getContractAddress());


            List receipt = service.getbanquanMarketContract().getMyNtfs();
            log.info("NbanquanMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行banquanMarket合约失败");
    }
    @CrossOrigin
    @RequestMapping(value = "/banquanOwner", method = RequestMethod.POST)
    public Result<?> banquanOwner(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}",service.getbanquanMarketContract().getContractAddress());
            String address = (String)param.get("address");

            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));

            String receipt = service.getbanquanMarketContract().banquanOwner(address,id);
            log.info("banquanMarket contract receipt = {}", receipt);
            return Result.data(receipt);
        }
        return Result.fail("执行banquanMarket合约失败");
    }
    //set goods for sell
    @CrossOrigin
    @RequestMapping(value = "/sellbanquan", method = RequestMethod.POST)
    public Result<?> sellNtf(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}", service.getbanquanMarketContract().getContractAddress());
            String time = (String)param.get("time");
            String remark = (String)param.get("remark");
            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));
            BigInteger price = BigInteger.valueOf((Integer) param.get("price"));
            TransactionReceipt  receipt = service.getbanquanMarketContract().sellbanquan(id,price,time,remark);
            log.info("banquanMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行banquanMarket合约失败");
    }
    // set good off sell
    @CrossOrigin
    @RequestMapping(value = "/offSale", method = RequestMethod.POST)
    public Result<?> offSale(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}", service.getbanquanMarketContract().getContractAddress());

            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));
            TransactionReceipt  receipt = service.getbanquanMarketContract().offSale(id);
            log.info("banquanMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行banquanMarket合约失败");
    }

    //get Ntfs which is For Sale
    @CrossOrigin
    @RequestMapping(value = "/getbanquansForSale", method = RequestMethod.POST)
    public Result<?> getbanquansForSale() throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}", service.getbanquanMarketContract().getContractAddress());
            List receipt =  service.getbanquanMarketContract().getNtfsForSale();
            log.info("banquanMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行NTFMarket合约失败");
    }
    //purchase
    @CrossOrigin
    @RequestMapping(value = "/buybanquan", method = RequestMethod.POST)
    public Result<?> buybanquan(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}", service.getbanquanMarketContract().getContractAddress());
            String time = (String)param.get("time");
            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));

            TransactionReceipt  receipt = service.getbanquanMarketContract().buybanquan(id,time);
            log.info("banquanMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行banquanMarket合约失败");
    }

    //get NTF's name, price, species,if selling
    @CrossOrigin
    @RequestMapping(value = "/getbanquanById1", method = RequestMethod.POST)
    public Result<?> getbanquanById1(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}", service.getbanquanMarketContract().getContractAddress());
            // String time = (String)param.get("time");
            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));

            Tuple4<Boolean, BigInteger, BigInteger, String> receipt = service.getbanquanMarketContract().getbanquanById1(id);//selling species,price,name
            //log.info("NTFMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行banquanMarket合约失败");
    }
//get NIF's url and created time
    @CrossOrigin
    @RequestMapping(value = "/getbanquanById2", method = RequestMethod.POST)
    public Result<?> getbanquanById2(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("banquanMarket contract address is: {}", service.getbanquanMarketContract().getContractAddress());
            // String time = (String)param.get("time");
            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));

            Tuple2<String, String> receipt = service.getbanquanMarketContract().getbanquanById2(id);//url,time
            //log.info("NTFMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行banquanMarket合约失败");
    }

    //get all the records ID that you make purchase with others
    @CrossOrigin
    @RequestMapping(value = "/ getMyRecords", method = RequestMethod.POST)
    public Result<?>  getMyRecords() throws Exception {
        if (service != null) {
            log.info("transaction contract address is: {}", service.getTransactionContract().getContractAddress());
            // String time = (String)param.get("time");
            // BigInteger id = BigInteger.valueOf((Integer) param.get("id"));

            List receipt = service.getTransactionContract().getMyRecords();
            //log.info("NTFMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行transaction合约失败");
    }
    //return a record of one purchase
    //the sequence of return data is under this line
//address buyer,
    // address seller,
    // uint ntfId,
    //uint price,
    //uint status,
    //string time,
    //string reason
    @CrossOrigin
    @RequestMapping(value = "/  getRecordById", method = RequestMethod.POST)
    public Result<?>  getRecordById(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("transaction contract address is: {}", service.getTransactionContract().getContractAddress());
            // String time = (String)param.get("time");
            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));

            Tuple7<String, String, BigInteger, BigInteger, BigInteger, String, String> receipt = service.getTransactionContract().getRecordById(id);
            //log.info("NTFMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行transaction合约失败");
    }
    //request Arbitration(tui kuan)
    @CrossOrigin
    @RequestMapping(value = "/ requestArbitration", method = RequestMethod.POST)
    public Result<?>  requestArbitration(@RequestBody Map<String,Object> param) throws Exception {
        if (service != null) {
            log.info("transaction contract address is: {}", service.getTransactionContract().getContractAddress());
            String reason = (String)param.get("reason");
            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));

            TransactionReceipt receipt = service.getTransactionContract().requestArbitration(id,reason);
            log.info("NTFMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行transaction合约失败");
    }

    //request Arbitration(tui kuan)
    @CrossOrigin
    @RequestMapping(value = "/ approveArbitration", method = RequestMethod.POST)
    public Result<?>  approveArbitration(@RequestBody Map<String,Object> param) throws Exception {
        if (service!= null) {
            log.info("transaction contract address is: {}", service.getTransactionContract().getContractAddress());
            // String reason = (String)param.get("reason");
            BigInteger id = BigInteger.valueOf((Integer) param.get("id"));

            TransactionReceipt receipt = service.getTransactionContract().approveArbitration(id);
            log.info("NTFMarket contract receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行transaction合约失败");
    }

}
