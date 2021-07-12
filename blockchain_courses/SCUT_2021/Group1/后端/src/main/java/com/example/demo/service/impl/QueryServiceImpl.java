package com.example.demo.service.impl;

import com.example.demo.ApplicationContextUtil;
import com.example.demo.client.ContractClient;
import com.example.demo.contract.TestContract;
import com.example.demo.pojo.*;
import com.example.demo.service.QueryService;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("QueryService")
public class QueryServiceImpl implements QueryService {
    @Autowired
    ContractClient contractClient = ApplicationContextUtil.getBean(ContractClient.class);
    @Override
    public Result showPath(String id) {
        try{
            String contractAddress = contractClient.loadContractAddr();
            TestContract testContract = TestContract.load(contractAddress,contractClient.client,contractClient.cryptoKeyPair);

            String res = testContract.showPath(id);
            res = res.substring(1);
            System.out.println(res);
            String[] data = res.split("\\s+");
//            for(int i=0;i< data.length;i++){
//                System.out.println(data[i]);
//            }
            String time = "";
            String location = "";
            if(data.length>=1)
                time = data[0];
            if(data.length>=2)
                location = data[1];
            ArrayList<Node> nodes = new ArrayList<Node>();
            int i = 2;
            while(i<data.length){
                Node node = new Node();
                node.in_time = data[i];
                i++;
                if(i>=data.length)break;
                node.out_time = data[i];
                i++;
                if(i>=data.length)break;
                node.port_name = data[i];
                i++;
                nodes.add(node);
            }
//            int l=1,r=1;
//            while(l<res.length()){
//                r = l;
//                while(res.charAt(r)!=' '){
//                    r++;
//                    if(r>=res.length())
//                        break;
//                }
//                if(time.isEmpty()){
//                    time = res.substring(l,r);
//                }else if(location.isEmpty()){
//                    location = res.substring(l,r);
//                }else{
//                    break;
//                }
//                l = r+1;
//            }
//            while(l<res.length()){
//                if(r>=res.length())
//                    break;
//                Node node = new Node();
//                while(res.charAt(r)!=' '){
//                    r++;
//                    if(r>=res.length())
//                        break;
//                }
//                String in = res.substring(l,r);
//                l = r+1;
//                r = l;
//                if(r>=res.length())
//                    break;
//                while(res.charAt(r)!=' '){
//                    r++;
//                    if(r>=res.length())
//                        break;
//                }
//                String out = res.substring(l,r);
//                l = r+1;
//                r = l;
//                if(r>=res.length())
//                    break;
//                while(res.charAt(r)!=' '){
//                    r++;
//                    if(r>=res.length())
//                        break;
//                }
//                String port = res.substring(l,r);
//                node.in_time = in;
//                node.out_time = out;
//                node.port_name = port;
//                nodes.add(node);
//            }
            //System.out.println(time);
            //System.out.println(location);

            QueryRes queryRes = new QueryRes(time,location,nodes);

            return Result.OK().data(queryRes).build();

        } catch (Exception e){
            System.out.printf("showPath meet exception: %s", e.getMessage());
            return Result.BAD().data("exception happened").build();
        }
    }

    @Override
    public Result showPortList() {
        try{
            String contractAddress = contractClient.loadContractAddr();
            TestContract testContract = TestContract.load(contractAddress,contractClient.client,contractClient.cryptoKeyPair);

            String res = testContract.showPortList();
            ArrayList<PortUnit> arrayList = new ArrayList<PortUnit>();
            System.out.println(res);
            int l=1,r=1;
            while(l<res.length()){
                while(res.charAt(r)!=' '){
                    r++;
                    if(r>=res.length())
                        break;
                }
                String user = res.substring(l,r);
                l = r+1;
                r = l;
                if(r>=res.length())
                    break;
                while(res.charAt(r)!=' '){
                    r++;
                    if(r>=res.length())
                        break;
                }
                String port = res.substring(l,r);
                l = r+1;
                r = l;
                System.out.println(user);
                System.out.println(port);
                PortUnit portUnit = new PortUnit(user,port);
                arrayList.add(portUnit);
            }
            PortList portList = new PortList(arrayList);
            return Result.OK().data(portList).build();

        } catch (Exception e){
            System.out.printf("showPath meet exception: %s", e.getMessage());
            return Result.BAD().data("exception happened").build();
        }
    }
}
