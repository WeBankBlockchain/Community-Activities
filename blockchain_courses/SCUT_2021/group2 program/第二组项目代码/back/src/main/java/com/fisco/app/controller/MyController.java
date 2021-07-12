package com.fisco.app.controller;

import com.fisco.app.client.CRUDClient;
import com.fisco.app.client.MyClient;
import com.fisco.app.entity.MyPerson;
import com.fisco.app.entity.Person;
import com.fisco.app.entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CrudController
 * @Description 通过接口调用sdk
 * @Date 2021/3/25 22:25
 * @Created by zyt
 */
@RestController
public class MyController {

    @Autowired
    private MyClient myClient;


    @GetMapping("/myinterface/query/{userName}")
    public ResponseData query(@PathVariable("userName") String name) throws Exception {

        return ResponseData.success(myClient.getUserInfoByUserName(name));
    }


    @PostMapping("/myinterface/insert")
    public ResponseData insert(@RequestBody MyPerson person)  {
        myClient.insert(person.getEthAddr(),person.getUserName(), person.getPassword());
        return ResponseData.success("新增成功");
    }

}
