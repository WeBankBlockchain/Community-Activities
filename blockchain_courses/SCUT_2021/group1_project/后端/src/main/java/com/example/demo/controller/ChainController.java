package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/chain")
@CrossOrigin
public class ChainController {
    @Autowired
    private ChainService chainService;
    @PostMapping("/genId")
    public Result genId(@RequestParam("user_id")String user_id,
                        @RequestParam("token")String token){
        return chainService.genId(user_id,token);
    }
    @PostMapping("/initHead")
    public Result initHead(@RequestParam("user_id")String user_id,
                           @RequestParam("id")String id,
                           @RequestParam("time")String time,
                           @RequestParam("location")String location,
                           @RequestParam("token")String token)
    {
        return chainService.initHead(user_id,id,time,location,token);
    }
    @PostMapping("/giveRight")
    public Result giveRight(@RequestParam("from")String from,
                            @RequestParam("to")String to,
                            @RequestParam("id")String id,
                            @RequestParam("token")String token)
    {
        return chainService.giveRight(from,to,id,token);
    }
    @PostMapping("/addNode")
    public Result addNode(@RequestParam("user_id")String user_id,
                          @RequestParam("id")String id,
                          @RequestParam("in_time")String in_time,
                          @RequestParam("out_time")String out_time,
                            @RequestParam("token")String token)
    {
        return chainService.addNode(user_id,id,in_time,out_time,token);
    }
}
