package org.fisco.bcos.trace.service.impl;

import org.fisco.bcos.trace.VO.Result;
import org.fisco.bcos.trace.service.NewService;
import org.fisco.bcos.trace.server.TraceServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.fisco.bcos.trace.*;
import org.fisco.bcos.trace.pojo.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.sql.Array;
import java.sql.ResultSet;
import  java.util.*;

@Service("NewService")
public class NewServiceImpl implements NewService {

    @Override
    public Result get_user_level(String mp_openid) throws Exception {
        TraceServer.initialize();
        Integer tmp = TraceServer.getUserLevel(mp_openid).intValue();
        String res = tmp.toString();
        UserLevel ul = new UserLevel(res);
        return Result.OK().data(ul).build();
    }

    @Override
    public Result get_company_name(String mp_openid) throws Exception {
        TraceServer.initialize();
        String result=TraceServer.getUserCompany(mp_openid);
        return Result.OK().data(result).build();
    }

    @Override
    public Result grant_level(String mp_openid, String account, String power) throws Exception {
        TraceServer.initialize();
        BigInteger pow= new BigInteger(power);
        BigInteger result=TraceServer.grantLevel(mp_openid,account,pow);
        int res=result.intValue();
        return Result.OK().data(res).build();
    }

    @Override
    public Result send_transfer_request(String mp_openid,TransferRequest transferrequest) throws Exception {
        String receiver_id=transferrequest.receiver_id;
        String send_from=transferrequest.send_from;
        String send_to=transferrequest.send_to;
        List<String> itemlist=transferrequest.item_list;
        int len = itemlist.size();
        String item_list="";
        for(int i=0;i<len;i++) {
            item_list += itemlist.get(i);
            if(i!=len-1)
                item_list += " ";
        }
        TraceServer.initialize();
        BigInteger result=TraceServer.sendTransferRequest(mp_openid,item_list,receiver_id,send_from,send_to);
        int res=result.intValue();
        return Result.OK().data(res).build();
    }

    @Override
    public Result accept_transfer_request(String mp_openid, String sender_id, String isAccept) throws Exception {
        String time=Time.getFormatDate();
        TraceServer.initialize();
        BigInteger result=TraceServer.acceptTransferRequest(sender_id,mp_openid,time,isAccept);
        int res=result.intValue();
        return Result.OK().data(res).build();
    }

    @Override
    public Result insert_User(User user) throws Exception {
        String mp_openid=user.mp_openid;
        String nickname=user.nick_name;
        String image_url=user.image_url;
        TraceServer.initialize();
        BigInteger result=TraceServer.insertUser(mp_openid,nickname,image_url);
        int res=result.intValue();
        return Result.OK().data(res).build();
    }

    @Override
    public Result get_item_process(String item_id) throws Exception {
        TraceServer.initialize();
        String result=TraceServer.getItemProcess(item_id);
        String[] ans = result.split(" ");
        int len = ans.length;
        if (!result.equals("")) {
            List<String> transfer_time = new ArrayList<>();
            List<String> sender_id = new ArrayList<>();
            List<String> receiver_id = new ArrayList<>();
            List<String> send_from = new ArrayList<>();
            List<String> send_to = new ArrayList<>();
            for (int i = 0; i < len; i += 5) {
                transfer_time.add(ans[i]);
                sender_id.add(ans[i + 1]);
                receiver_id.add(ans[i + 2]);
                send_from.add(ans[i + 3]);
                send_to.add(ans[i + 4]);
            }
            ItemProcess res=new ItemProcess(transfer_time,sender_id,receiver_id,send_from,send_to);
            return Result.OK().data(res).build();
        }else
            return Result.OK().data(0).build();
    }

    @Override
    public Result get_receiver_request(String mp_openid) throws Exception {
        TraceServer.initialize();
        String result=TraceServer.getReceiverRequest(mp_openid);
        //String result = "1234 234 2332 23424 234 234 ! 34 234 432 423 4234 23 4 23 4 2 ! 234 234 234 234 34 2 234 2 ! ";
        String[] ans = result.split(" ");
        int len = ans.length;
        if (!result.equals("")) {
            List<String> sender_id = new ArrayList<>();
            List<String> send_from = new ArrayList<>();
            List<String> send_to = new ArrayList<>();
            List<List<String> > item_list = new ArrayList<>();
            int i = 0;
            while (i < len) {
                List<String> temp = new ArrayList<>();
                sender_id.add(ans[i]);
                i++;
                send_from.add(ans[i]);
                i++;
                send_to.add(ans[i]);
                i++;
                while (i < len) {
                    if (ans[i].equals("!")) {
                        i++;
                        break;
                    } else {
                        temp.add(ans[i]);
                        i++;
                    }
                }
                item_list.add(temp);
            }
            ReceiverRequest res = new ReceiverRequest(sender_id,send_from,send_to,item_list);
            return Result.OK().data(res).build();
        } else
            return Result.OK().data(0).build();
    }

    @Override
    public Result post_item_process(String mp_openid,ProduceData producedata) throws Exception {
        String item_name=producedata.name;
        String producetime=producedata.time;
        String produceplace=producedata.place;
        String item_id=GenerateRandomString.getRandomString(15);
        TraceServer.initialize();
        BigInteger result=TraceServer.Produce(item_id,item_name,mp_openid,producetime,produceplace);
        int result_=result.intValue();
        while(result_==-2)
        {
            item_id=GenerateRandomString.getRandomString(15);
            result=TraceServer.Produce(item_id,item_name,mp_openid,producetime,produceplace);
            result_=result.intValue();
        }
        if(result_==-1)
            return Result.OK().data(-1).build();
        if(result_==-100)
            return Result.OK().data(-100).build();

        ItemID res = new ItemID(item_id);
        return Result.OK().data(res).build();
    }

    @Override
    public Result get_company_employee(String mp_openid) throws Exception {
        TraceServer.initialize();
        String companyname = TraceServer.getUserCompany(mp_openid);
        String result = TraceServer.getCompanyEmployee(companyname);
        String[] ans = result.split(" ");
        int len = ans.length;
        List<String> employee_id = new ArrayList<>();
        List<String> nickname = new ArrayList<>();
        for (int i = 0; i < len; i += 2) {
            employee_id.add(ans[i]);
            nickname.add(ans[i + 1]);
        }
        CompanyEmployee res = new CompanyEmployee(employee_id, nickname);
        return Result.OK().data(res).build();
    }

    @Override
    public Result get_user_send(String mp_openid) throws Exception {
        TraceServer.initialize();
        String result = TraceServer.getUserSend(mp_openid);
        //String result ="2134 233 554 ! 234 3425 432 1 ! 243 ! 323 23 ! ";
        String[] ans = result.split(" ");
        int len = ans.length;
        if (!result.equals("")) {
            List<List<String>> item_list = new ArrayList<>();
            int i = 0;
            while (i < len) {
                List<String> temp = new ArrayList<>();
                while (i < len) {
                    if (ans[i].equals("!")) {
                        i++;
                        break;
                    } else {
                        temp.add(ans[i]);
                        i++;
                    }
                }
                item_list.add(temp);
            }
            ItemList res = new ItemList(item_list);
            return Result.OK().data(res).build();
        } else
            return Result.OK().data(0).build();
    }


    @Override
    public Result get_company_produce(String mp_openid) throws Exception {
        TraceServer.initialize();
        String company=TraceServer.getUserCompany(mp_openid);
        String result=TraceServer.getCompanyProduce(company);
        //String result="";
        String[] ans=result.split(" ");
        int len=ans.length;
        if(!result.equals("")) {
            List<String> itemid = new ArrayList<>();
            List<String> itemname = new ArrayList<>();
            for (int i = 0; i < len; i += 2) {
                itemid.add(ans[i]);
                itemname.add(ans[i + 1]);
            }
            CompanyProduct res = new CompanyProduct(itemid, itemname);
            return Result.OK().data(res).build();
        }
        else
            return Result.OK().data(0).build();
    }

}
