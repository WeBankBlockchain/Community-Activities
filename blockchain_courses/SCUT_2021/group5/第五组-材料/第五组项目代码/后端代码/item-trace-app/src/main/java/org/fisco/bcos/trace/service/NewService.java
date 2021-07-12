package org.fisco.bcos.trace.service;

import org.fisco.bcos.trace.VO.Result;
import org.fisco.bcos.trace.pojo.ProduceData;
import org.fisco.bcos.trace.pojo.TransferRequest;
import org.fisco.bcos.trace.pojo.User;

public interface NewService {

    Result get_user_level(String mp_openid) throws Exception;
    Result get_company_name(String mp_openid) throws Exception;
    Result grant_level(String mp_openid,String account,String power) throws Exception;
    Result send_transfer_request(String mp_openid, TransferRequest transferrequest) throws Exception;
    Result accept_transfer_request(String mp_openid,String sender_id,String isAccept) throws Exception;
    Result insert_User(User user) throws Exception;
    Result get_item_process(String item_id) throws Exception;
    Result get_receiver_request(String mp_openid) throws Exception;
    Result post_item_process(String mp_openid, ProduceData producedata) throws Exception;
    Result get_company_employee(String mp_openid) throws Exception;
    Result get_user_send(String mp_openid) throws Exception;
    Result get_company_produce(String mp_openid) throws Exception;




    /*
    Result getNumLeft(String seat_id, String area_name, String is_window, String is_plug, int time_slot);

    Result getOccupyTSlot(String seat_id);

    Result confmSeat(String unionid, String seat_id,int time_slot);

    Result dealRequest(String unionid, String seat_id, String area_name, String is_window, String is_plug, int time_slot);
     */
}