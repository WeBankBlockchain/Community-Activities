package org.fisco.bcos.trace.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.fisco.bcos.trace.VO.Result;
import org.fisco.bcos.trace.pojo.ProduceData;
import org.fisco.bcos.trace.pojo.TransferRequest;
import org.fisco.bcos.trace.pojo.User;
import org.fisco.bcos.trace.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/v1/usr")
public class NewController{
    @Autowired
    private NewService newService;//Service层

    @GetMapping(value="/user/get_user_level")//获取用户的权限
    @ResponseBody
    public Result get_user_level(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid) throws Exception {
        return newService.get_user_level(mp_openid);
    }

    @GetMapping("/user/get_company_name")//获取用户的公司名称(未完成,没给路径)
    public Result get_company_name(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid)throws Exception
    {
        return newService.get_company_name(mp_openid);
    }

    @PostMapping("/company/grant_level")//授予权限
    @ResponseBody
    public Result grant_level(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid,
                              @RequestParam(value = "account", defaultValue = "%")String account,
                              @RequestParam(value = "power", defaultValue = "%")String power) throws Exception {
        return newService.grant_level(mp_openid,account,power);
    }

    @PostMapping("/transfer/send_transfer_request")//发送运输请求
    @ResponseBody
    public Result send_transfer_request(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid,
                                        @RequestBody TransferRequest transferrequest) throws Exception {
        return newService.send_transfer_request(mp_openid,transferrequest);
    }

    @PostMapping("/transfer/accept_transfer_request")//确认交易请求
    @ResponseBody
    public Result accept_transfer_request(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid,
                                          @RequestParam(value = "sender_id", defaultValue = "%")String sender_id,
                                          @RequestParam(value = "isAccept", defaultValue = "%")String isAccept) throws Exception {
        return newService.accept_transfer_request(mp_openid,sender_id,isAccept);
    }

    @PostMapping("/user/insert_User")//插入用户信息
    public Result insert_User(@RequestBody User user) throws Exception {
        return newService.insert_User(user);
    }

    @GetMapping("/item/get_item_process")//获取物品流程
    @ResponseBody
    public Result get_item_process(@RequestParam(value="item_id",defaultValue="%")String item_id) throws Exception {
        return newService.get_item_process(item_id);
    }

    @GetMapping("/transfer/get_receiver_request")//获取接受者待处理请求
    @ResponseBody
    public Result get_receiver_request(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid) throws Exception {
        return newService.get_receiver_request(mp_openid);
    }

    @PostMapping("/items/post_item_process")//生产商家用户获取商品的序列号（制造商品）
    @ResponseBody
    public Result post_item_process(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid,
                                    @RequestBody ProduceData producedata) throws Exception {
        return newService.post_item_process(mp_openid,producedata);
    }

    @GetMapping("/company/get_company_employee")//获取该公司的所有员工
    @ResponseBody
    public Result get_company_employee(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid) throws Exception {
        return newService.get_company_employee(mp_openid);
    }

    @GetMapping("/transfer/get_user_send")//获取该用户最近成功发送出的五次的交易物品列表
    @ResponseBody
    public Result get_user_send(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid) throws Exception {
        return newService.get_user_send(mp_openid);
    }

    @GetMapping("/company/get_company_produce")//生产方获取公司制造的所有物品列表
    @ResponseBody
    public Result get_company_produce(@RequestParam(value="mp_openid",defaultValue="%")String mp_openid) throws Exception {
        return newService.get_company_produce(mp_openid);
    }


}