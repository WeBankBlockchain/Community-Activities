package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/query")
@CrossOrigin
public class QueryController {
    @Autowired
    private QueryService queryService;
    @PostMapping("/showPath")
    public Result showPath(@RequestParam("id")String id)
    {
        return queryService.showPath(id);
    }
    @PostMapping("/showPortList")
    public Result showPortList()
    {
        return queryService.showPortList();
    }
}
