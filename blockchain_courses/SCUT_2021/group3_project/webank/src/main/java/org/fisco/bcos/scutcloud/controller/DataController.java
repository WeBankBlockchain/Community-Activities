package org.fisco.bcos.scutcloud.controller;

import com.google.gson.JsonObject;
import org.fisco.bcos.scutcloud.contract.AccessControl;
import org.fisco.bcos.scutcloud.pojo.Data;
import org.fisco.bcos.scutcloud.pojo.Data_new;
import org.fisco.bcos.scutcloud.pojo.User;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.scutcloud.service.DataService;
import org.fisco.bcos.scutcloud.service.DeployService;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple7;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple8;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ResponseBody
@Controller
@CrossOrigin
@RequestMapping("/api/Data")
public class DataController {

    @Autowired
    DeployService deployService;

   @PostMapping("/create")
    public Result createData(@RequestBody Data data){
       DataService dataService = new DataService();
       dataService.deployService = deployService;

//       return dataService.createScutCloudData(data.getOwnerId(), data.getCredit(), null,
//                        data.getDataName(),data.getDataDescription(),data.getAddress(),data.getType());
              return dataService.createScutCloudData(data.getOwnerId(), data.getCredit(), null,
                        data.getDataName(),data.getDataDescription(),data.getAddress(),data.getType());
   }

   @PostMapping("/alter")
   public Result alterData(@RequestBody Data_new data){
       DataService dataService = new DataService();
       dataService.deployService = deployService;

       return dataService.alterScutCloudData(data.getUserName(),data.getId(),data.getCredit(),data.getDownloadTimes(),data.getDataName(),data.getDataDescription(),
               data.getAddress(),data.getType());
   }
   @GetMapping("/getLength")
    public Result getDataLength(){
       DataService dataService = new DataService();
       dataService.deployService = deployService;

       return dataService.getScutCloudDataLength();

   }
   @GetMapping("/getAllData")
    public Result getData() {
       DataService dataService = new DataService();
       dataService.deployService = deployService;

       List<Data_new> temp =dataService.getdatas();
       return ResultFactory.buildSuccessResult(temp);
   }
   @PostMapping("/getData")
    public Result getData_new(@RequestBody Data_new data) throws Exception {
       DataService dataService = new DataService();
       dataService.deployService = deployService;
       List<Data_new> temp =dataService.getdata(data.getDataName(),data.getType());

       return ResultFactory.buildSuccessResult(temp);
   }
   @PostMapping("/getSelfData")
    public Result getSelfData(@RequestBody User user) throws  Exception{
       DataService dataService = new DataService();
       dataService.deployService = deployService;
       List<Data_new> res = dataService.getSelfData(user.get_id());
       return ResultFactory.buildSuccessResult(res);
   }
}