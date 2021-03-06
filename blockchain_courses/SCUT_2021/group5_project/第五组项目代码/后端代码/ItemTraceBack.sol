pragma solidity ^0.4.24;

contract ItemTraceBack {
    
    //event
    event GrantLevelEvent(int256 ret,string leader_mpopenid,string user_mpopenid,uint256 operation);
    event SendRequestEvent(int256 ret,string senderid,string item_list,string receiverid,string transfer_from,string transfer_to);
    event AcceptRequestEvent(int256 ret,string senderid,string receiverid,string transfer_time,string acc);
    event InsertUserEvent(int256 ret,string mpopenid,string nickname,string avatar);
    event ProduceEvent(int256 ret,string itemid,string itemname,string producerid,string producetime,string produceplace);
    
    struct User{
        string nickname;
        string avatar;
        uint256 user_level;
        string company;
        bool isUsed;
    }
    
    struct Produce{
        string item_name;
        string producer_id;
        string company;
        string produce_time;
        string produce_place;
        bool isUsed;
    }
    
    struct Location{
        string transporter_id;
        string location;
    }
    
    struct Item_Process_Message{
        string sender_id;
        string receiver_id;
        string transfer_from;
        string transfer_to;
    }
    
    struct Request{
        string item_list;
        string transfer_from;
        string transfer_to;
        bool isUsed;
    }
    
    struct itmapping_Process{
        mapping(string=>Item_Process_Message) data;
        string[] keys;
        uint size;
    }
    
    struct Item_List{
        string[] keys;
        uint size;
    }
    
    function push_key(Item_List storage itemlist,string key)private{
        itemlist.keys.push(key);
        itemlist.size++;
    }
    
    struct Employees{
        string[] keys;
        uint size;
    }
    
    function pop_key(Employees storage employee,string key)private returns(uint256){
        for(uint i=0;i<employee.size;i++)
            if(compare_str(employee.keys[i],key)==0)
                {
                    employee.keys[i]=employee.keys[employee.size-1];
                    employee.size--;
                    return 1;
                }
        return 0;
    }
    
    function push_key(Employees storage employee,string key)private{
        employee.keys.push(key);
        employee.keys[employee.size]=employee.keys[employee.keys.length-1];
        employee.size++;
    }
    
    struct Company_Produce{
        string[] keys;
        uint size;
    }
    
    function push_key(Company_Produce storage companyproduce,string key)private{
        companyproduce.keys.push(key);
        companyproduce.size++;
    }
    
    struct itmapping_Request{
        mapping(string=>Request) data;
        string[] keys;
        uint size;
    }
    
    function pop_key(itmapping_Request storage request,string key)private returns(uint256){
        for(uint i=0;i<request.size;i++)
            if(compare_str(request.keys[i],key)==0)
                {
                    request.keys[i]=request.keys[request.size-1];
                    request.size--;
                    return 1;
                }
        return 0;
    }
    
    function push_key(itmapping_Request storage request,string key)private{
        request.keys.push(key);
        request.keys[request.size]=request.keys[request.keys.length-1];
        request.size++;
    }
    
    // ?????????????????????(Item_Process),key : item_id,transfer_time, field : sender_id,receiver_id,transfer_from,transfer_to
    mapping(string=>itmapping_Process) Item_Process;
    
    // ???????????????(Transfer_Request),key : receiver_id,sender_id, field : item_list,transfer_from,transfer_to
    mapping(string=>itmapping_Request) Transfer_Request;
    

    // ???????????????(System_User),key : mp_openid, field : nickname,avatar,user_level,company
    mapping(string=>User) System_User;
    

    // ?????????????????????(Item_Produce),key : item_id, field : item_name,producer_id,company,produce_time,produce_place
    mapping(string=>Produce) Item_Produce;
    

    // ?????????????????????(Item_Location),key : item_id, field : transporter_id,location
    mapping(string=>Location) Item_Location;
    
    //??????????????????(Company_Empoloyee)Company=>Employees
    mapping(string=>Employees)Company_Empoloyee;
    
    //????????????????????????(Success_Transfer)sender_id=>Item_List
    mapping(string=>Item_List)Success_Transfer;

    //???????????????????????????
    mapping(string=>Company_Produce)Company_Item;
    
    constructor()public{
        insert_User("100001","ZhangSan","asdf");
        update_user_level("100001",4,"AUZhiZao");
        push_key(Company_Empoloyee["AUZhiZao"],"100001");
        insert_User("100002","LiSi","sdfg");
        update_user_level("100002",4,"CUZhiZao");
        push_key(Company_Empoloyee["CUZhiZao"],"100002");
        insert_User("100003","WangWu","dfgh");
        update_user_level("100003",4,"AGZhiZao");
        push_key(Company_Empoloyee["AGZhiZao"],"100003");
        insert_User("100004","ZhaoLiu","qwer");
        update_user_level("100004",2,"ZGYouZheng");
        push_key(Company_Empoloyee["ZGYouZheng"],"100004");
        insert_User("100005","WuQi","wert");
        update_user_level("100005",2,"ZTKuaiDi");
        push_key(Company_Empoloyee["ZTKuaiDi"],"100005");
        insert_User("100006","LiuBa","erty");
        update_user_level("100006",2,"YDKuaiDi");
        push_key(Company_Empoloyee["YDKuaiDi"],"100006");
    }
    
    //??????????????????????????????,????????????1????????????????????????????????????????????????,????????????-1????????????????????????????????????????????????
    //??????0???????????????????????????
    function compare_str(string str1,string str2) private view returns(int256){
        bytes memory str1_bytes=bytes(str1);
        bytes memory str2_bytes=bytes(str2);
        uint str1_len=str1_bytes.length;
        uint str2_len=str2_bytes.length;
        int256 ret;
        for(uint i=0;i<str1_len;i++)
        {
            if(i==str2_len)
                return 1;
            if(str1_bytes[i]>str2_bytes[i])
                return 1;
            else if(str1_bytes[i]<str2_bytes[i])
                return -1;
        }
        if(str1_len==str2_len)
            return 0;
        else
            return -1;
    }
    
    //?????????????????????
    function connect_str(string str1,string str2) private view returns (string memory) {
        bytes memory str1_bytes=bytes(str1);
        bytes memory str2_bytes=bytes(str2);
        uint str1_len=str1_bytes.length;
        uint str2_len=str2_bytes.length;
        string memory result=new string(str1_len+str2_len);
        bytes memory result_bytes=bytes(result);
        for(uint i=0;i<str1_len;i++)
            result_bytes[i]=str1_bytes[i];
        for(;i<str1_len+str2_len;i++)
            result_bytes[i]=str2_bytes[i-str1_len];
        return string(result);
    }
    
    //????????????????????????????????????
    function itemID_Exist(string itemid) private returns(uint256){
		if(Item_Produce[itemid].isUsed==true)
		    return 1;
		else
		    return 0;
    }
    
    function get_item_name(string itemid) private returns(string){
        return Item_Produce[itemid].item_name;
    }
    
    function get_last_transporter_id(string itemid) private returns(string){
        return Item_Location[itemid].transporter_id;
    }
    
    function user_Exist(string mpopenid) private returns(uint256){
        if(System_User[mpopenid].isUsed==true)
            return 1;
        else
            return 0;
    }
    
    //???????????????????????????
    function get_user_level(string mpopenid) public constant returns(uint256){
        if(user_Exist(mpopenid)==0)
            return 0;
        else
            return System_User[mpopenid].user_level;
    }
    
    function update_user_level(string mpopenid,uint256 newlevel,string companyname)private {
        System_User[mpopenid].user_level=newlevel;
        System_User[mpopenid].company=companyname;
    }
    
    
    function insert_Produce(string itemid,string itemname,string producerid,string producetime,string produceplace)private{
        string memory companyname=get_user_company(producerid);
        Item_Produce[itemid].item_name=itemname;
        Item_Produce[itemid].producer_id=producerid;
        Item_Produce[itemid].company=companyname;
        Item_Produce[itemid].produce_time=producetime;
        Item_Produce[itemid].produce_place=produceplace;
        Item_Produce[itemid].isUsed=true;
    }
    
    function insert_Location(string itemid,string producerid,string produceplace)private{
        Item_Location[itemid].transporter_id=producerid;
        Item_Location[itemid].location=produceplace;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //???????????????????????????0??????????????????????????????????????????
    //operation???1??????user?????????????????????????????????????????????????????????,???0??????user??????????????????????????????????????????
    //??????-1?????????????????????????????????,-2??????????????????????????????,-3???????????????????????????????????????????????????
    //-4?????????????????????????????????????????????????????????,-5????????????????????????????????????????????????????????????
    function grant_level(string leader_mpopenid,string user_mpopenid,uint256 operation)public returns(int256 ret_code){
        uint256 leader_level=get_user_level(leader_mpopenid);
        uint256 user_level=get_user_level(user_mpopenid);
        string memory leader_company=get_user_company(leader_mpopenid);
        if(user_Exist(user_mpopenid)==0)
            ret_code=-2;
        else if(leader_level==2)
        {
            if(operation==1)
            {
                if(user_level==0)
                {
                    update_user_level(user_mpopenid,1,leader_company);
                    push_key(Company_Empoloyee[leader_company],user_mpopenid);
                    ret_code=1;
                }
                else
                {
                    string memory user_company=get_user_company(user_mpopenid);
                    if(compare_str(leader_company,user_company)==0)
                        ret_code=-3;
                    else
                        ret_code=-4;
                }
            }
            else
            {
                if(user_level==1)
                {
                    string memory user_company2=get_user_company(user_mpopenid);
                    if(compare_str(leader_company,user_company2)==0)
                    {
                        update_user_level(user_mpopenid,0,"");
                        pop_key(Company_Empoloyee[leader_company],user_mpopenid);
                        ret_code=1;
                    }
                    else
                        ret_code=-5;
                }
                else
                    ret_code=-5;
            }
        }
        else if(leader_level==4)
        {
            if(operation==1)
            {
                if(user_level==0)
                {
                    update_user_level(user_mpopenid,3,leader_company);
                    push_key(Company_Empoloyee[leader_company],user_mpopenid);
                    ret_code=1;
                }
                else
                {
                    string memory user_company3=get_user_company(user_mpopenid);
                    if(compare_str(leader_company,user_company3)==0)
                        ret_code=-3;
                    else
                        ret_code=-4;
                }
            }
            else
            {
                if(user_level==3)
                {
                    string memory user_company4=get_user_company(user_mpopenid);
                    if(compare_str(leader_company,user_company4)==0)
                    {
                        update_user_level(user_mpopenid,0,"");
                        pop_key(Company_Empoloyee[leader_company],user_mpopenid);
                        ret_code=1;
                    }
                    else
                        ret_code=-5;
                }
                else
                    ret_code=-5;
            }
        }
        else
            ret_code=-1;
            
        emit GrantLevelEvent(ret_code,leader_mpopenid,user_mpopenid,operation);
    }
    
    //??????????????????????????????????????????????????????????????????????????????????????????
    //??????-1????????????????????????????????????-2???????????????????????????
    //-3??????????????????????????????????????????id????????????-4??????????????????????????????????????????id????????????????????????

    function send_transfer_request(string senderid,string item_list,string receiverid,string transfer_from,string transfer_to)public returns(int256 ret_code){
        ret_code=0;
        if(Transfer_Request[receiverid].data[senderid].isUsed==true)
            ret_code = -5;
        else if(get_user_level(senderid)==0)
            ret_code = -1;
        else if(get_user_level(receiverid)==0)
            ret_code = -2;
        else
        {
            bytes memory item_list_bytes=bytes(item_list);
            uint item_list_length=item_list_bytes.length;
            uint len=0;
            for(uint i=0;i<item_list_length;i++)
            {
                if(item_list_bytes[i]==' ')
                {
                    string memory item=new string(len);
                    bytes memory item_bytes=bytes(item);
                    for(uint j=0;j<len;j++)
                        item_bytes[j]=item_list_bytes[i-len+j];
                    if(itemID_Exist(string(item))==0)
                    {
                        ret_code = -3;
                        break;
                    }
                    else if(compare_str(get_last_transporter_id(string(item)),senderid)!=0)
                    {
                        ret_code = -4;
                        break;
                    }
                    len=0;
                }
                else
                    len++;
                if(i==item_list_length-1)
                {
                    i++;
                    item=new string(len);
                    item_bytes=bytes(item);
                    for(j=0;j<len;j++)
                        item_bytes[j]=item_list_bytes[i-len+j];
                    if(itemID_Exist(string(item))==0)
                    {
                        ret_code = -3;
                        break;
                    }
                    else if(compare_str(get_last_transporter_id(string(item)),senderid)!=0)
                    {
                        ret_code = -4;
                        break;
                    }
                }
            }
            //??????????????????
            if(ret_code==0)
            {
                Transfer_Request[receiverid].data[senderid]=Request(item_list,transfer_from,transfer_to,true);
                push_key(Transfer_Request[receiverid],senderid);
                ret_code=1;
            }
        }
        emit SendRequestEvent(ret_code,senderid,item_list,receiverid,transfer_from,transfer_to);
    }
    
    //?????????????????????????????????
    //acc??????1"?????????????????????acc???"0"??????????????????
    //??????-1????????????sender???receiver?????????????????????
    function accept_transfer_request(string senderid,string receiverid,string transfer_time,string acc)public returns(int256 ret_code){
        if(Transfer_Request[receiverid].data[senderid].isUsed==false)
            ret_code=-1;
        else if(compare_str(acc,"0")==0)
        {
            Transfer_Request[receiverid].data[senderid].isUsed=false;
            ret_code=1;
        }
        else
        {
            string memory item_list=Transfer_Request[receiverid].data[senderid].item_list;
            bytes memory item_list_bytes=bytes(item_list);
            uint item_list_length=item_list_bytes.length;
            uint len=0;
            for(uint i=0;i<item_list_length;i++)
            {
                if(item_list_bytes[i]==' ')
                {
                    string memory item=new string(len);
                    bytes memory item_bytes=bytes(item);
                    for(uint j=0;j<len;j++)
                        item_bytes[j]=item_list_bytes[i-len+j];
                    Item_Process[string(item)].data[transfer_time]=Item_Process_Message(senderid,receiverid,Transfer_Request[receiverid].data[senderid].transfer_from,Transfer_Request[receiverid].data[senderid].transfer_to);
                    Item_Process[string(item)].keys.push(transfer_time);
                    Item_Process[string(item)].size++;
                    Item_Location[string(item)].transporter_id=receiverid;
                    Item_Location[string(item)].location=connect_str(connect_str(Transfer_Request[receiverid].data[senderid].transfer_from,"->"),Transfer_Request[receiverid].data[senderid].transfer_to);
                    pop_key(Transfer_Request[receiverid],senderid);
                    len=0;
                }
                else
                    len++;
                if(i==item_list_length-1)
                {
                    i++;
                    item=new string(len);
                    item_bytes=bytes(item);
                    for(j=0;j<len;j++)
                        item_bytes[j]=item_list_bytes[i-len+j];
                    Item_Process[string(item)].data[transfer_time]=Item_Process_Message(senderid,receiverid,Transfer_Request[receiverid].data[senderid].transfer_from,Transfer_Request[receiverid].data[senderid].transfer_to);
                    Item_Process[string(item)].keys.push(transfer_time);
                    Item_Process[string(item)].size++;
                    Item_Location[string(item)].transporter_id=receiverid;
                    Item_Location[string(item)].location=connect_str(connect_str(Transfer_Request[receiverid].data[senderid].transfer_from,"->"),Transfer_Request[receiverid].data[senderid].transfer_to);
                    pop_key(Transfer_Request[receiverid],senderid);
                }
            }
            push_key(Success_Transfer[senderid],item_list);
            Transfer_Request[receiverid].data[senderid].isUsed=false;
            ret_code=1;
        }
        emit AcceptRequestEvent(ret_code,senderid,receiverid,transfer_time,acc);
    }
    
    //???????????????????????????
    function get_user_company(string mpopenid) public constant returns(string){
        return System_User[mpopenid].company;
    }
    
    function get_user_message(string mpopenid) public constant returns(string nickname,string avatar){
        return (System_User[mpopenid].nickname,System_User[mpopenid].avatar);
    }
    
    function insert_User(string mpopenid,string nickname,string avatar)public returns(int256 ret_code){
        System_User[mpopenid].nickname=nickname;
        System_User[mpopenid].avatar=avatar;
        if(System_User[mpopenid].isUsed==false)
            System_User[mpopenid].user_level=0;
        System_User[mpopenid].isUsed=true;
        ret_code = 1;
        emit InsertUserEvent(ret_code,mpopenid,nickname,avatar);
    }
    
    //???????????????????????????????????????5???string???????????????string?????????????????????????????????????????????????????????????????????id????????????id???????????????????????????
    function get_item_process(string itemid)public constant returns(string message){
        uint s=Item_Process[itemid].size;
        string memory ans = "";
        for(uint i=0;i<s;i++)
        {
            string memory transfer_time=Item_Process[itemid].keys[i];
            ans=connect_str(ans,transfer_time);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,Item_Process[itemid].data[transfer_time].sender_id);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,Item_Process[itemid].data[transfer_time].receiver_id);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,Item_Process[itemid].data[transfer_time].transfer_from);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,Item_Process[itemid].data[transfer_time].transfer_to);
            ans=connect_str(ans,' ');
        }
        return ans;
    }
    
    //?????????????????????????????????????????????????????????????????????string???????????????string????????????????????????????????????????????????!?????????????????????????????????????????????id?????????????????????????????????????????????????????????????????????????????????
    function get_receiver_request(string receiverid)public constant returns(string message){
        uint s=Transfer_Request[receiverid].size;
        string memory ans = "";
        for(uint i=0;i<s;i++)
        {
            string memory sender_id=Transfer_Request[receiverid].keys[i];
            ans=connect_str(ans,sender_id);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,Transfer_Request[receiverid].data[sender_id].transfer_from);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,Transfer_Request[receiverid].data[sender_id].transfer_to);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,Transfer_Request[receiverid].data[sender_id].item_list);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,'!');
            ans=connect_str(ans,' ');
        }
        return ans;
    }
    
    
    //????????????????????????????????????????????????????????????????????????openid???????????????????????????????????????????????????
    //??????1??????????????????,??????-1??????????????????????????????,??????-2???????????????????????????
    function produce(string itemid,string itemname,string producerid,string producetime,string produceplace) public 
        returns(int256 ret_code){
        uint256 user_level=get_user_level(producerid);
        if(user_level!=3&&user_level!=4)
            ret_code=-1;
        else if(itemID_Exist(itemid)==1)
            ret_code=-2;
        else{
            insert_Produce(itemid,itemname,producerid,producetime,produceplace);
            insert_Location(itemid,producerid,produceplace);
            string memory company=get_user_company(producerid);
            push_key(Company_Item[company],itemid);
            ret_code=1;
        }
        emit ProduceEvent(ret_code,itemid,itemname,producerid,producetime,produceplace);
    }
    
    function get_company_employee(string companyname)public constant returns(string message){
        uint s=Company_Empoloyee[companyname].size;
        string memory ans = "";
        for(uint i=0;i<s;i++)
        {
            string memory employee_id=Company_Empoloyee[companyname].keys[i];
            string memory nickname;
            string memory avatar;
            (nickname,avatar)=get_user_message(employee_id);
            ans=connect_str(ans,employee_id);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,nickname);
            ans=connect_str(ans,' ');
            
        }
        return ans;
    }
    
    function get_user_send(string mpopenid)public constant returns(string message){
        uint s=Success_Transfer[mpopenid].size;
        string memory ans = "";
        for(uint i=s;i>0&&i+4>=s;i--)
        {
            ans=connect_str(ans,Success_Transfer[mpopenid].keys[i-1]);
            ans=connect_str(ans,' ');
            ans=connect_str(ans,'!');
            ans=connect_str(ans,' ');
        }
        return ans;
    }
    
    function get_company_produce(string companyname)public constant returns(string message){
        uint s=Company_Item[companyname].size;
        string memory ans = "";
        for(uint i=s;i>0;i--)
        {
            string memory itemid=Company_Item[companyname].keys[i-1];
            ans=connect_str(ans,itemid);
            ans=connect_str(ans,' ');
            string memory itemname=get_item_name(itemid);
            ans=connect_str(ans,itemname);
            ans=connect_str(ans,' ');
        }
        return ans;
    }
}