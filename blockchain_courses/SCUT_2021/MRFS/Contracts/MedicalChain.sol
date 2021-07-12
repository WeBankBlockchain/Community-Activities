pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

contract MedicalChain{

    struct Resource {
        address owner;      // 所有者地址
        uint256 resource_id;         //资源id
        string location;    // 所在位置
        uint256 resource_num;        // 数量
        string resource_name; //物资名字
        string category;       // 类别 
        string batch_code;  // 开始时间
        bool is_used;    // 到期时间
        string img_url; //图片的url
        
    }

    struct User { // 这里的用户实体可以是任何，包括节点，用户，企业
        string name;    // 用户实体的名称
        address addr;      // 用户地址
        string location; //用户在区块链上地址
        string role; //用户角色,暂时是 user，factory，hospital,god
    }

    // 交易记录
    struct Transaction {
        address from;     // 转让方地址
        address to;       // 接受方地址
        uint256 resource_id; // 资源编号
        string time;      // 交易时间
        string info;      // 交易信息
    }


    mapping(address => User) account;  // 每个地址对应一个用户实体

    address private owner; //owner

    Resource[] resources;     // 记录链上所有的资源
    mapping(uint256 => Transaction[]) transactions; //rid到对应的转账记录
    uint256 resource_id_cnt = 0; //自增id
    
    uint index = 0; //用于减少函数内额外定义的变量
    uint256 userCnt = 0; //用户数量
    uint256 factoryCnt = 0; //生产商数量
    
    // 构造函数
    constructor() public{
        owner = msg.sender;
        account[owner].name = "GOD";
        account[owner].role = "god";
    }

    /*
    描述 : 登记用户
            1 : 登记成功
            -1 : 登记失败，重复登记
    */
    function registerUser(string _name, string _location) public returns (int256){
        if(keccak256(account[msg.sender].name) != keccak256(""))
            return -1;
        account[msg.sender].name = _name;
        account[msg.sender].location = _location;
        account[msg.sender].addr = msg.sender;
        account[msg.sender].role = "user";
        userCnt++;
        return 1;
    }

    /*
    描述 : 更改用户角色
            1 : 登记成功
            -1 : 登记失败，重复登记
    */
    function changeRole(address _addr,string _role) public returns(int256){
        if(msg.sender !=owner || _addr == owner)
            return -1;
        account[_addr].role = _role;
        if(keccak256(_role) == keccak256("factory"))
            factoryCnt++;
        return 1;
    }
    
    /*
    描述 : 登记资源
            1 : 登记成功
            -1 : 登记失败，非工厂不能登记
    */
    function registerResource(uint256 resource_num, string resource_name,  string category, string batch_code,string img_url,string time,string info) public returns (int256){
        if (keccak256(account[msg.sender].role) != keccak256("factory"))
            return -1;
        resources.push(Resource(msg.sender, resource_id_cnt,account[msg.sender].location, resource_num, resource_name, category, batch_code, false,img_url));
        transactions[resource_id_cnt].push(Transaction(msg.sender,msg.sender,resource_id_cnt,time,info));
        resource_id_cnt++;
        return 1;
    }

    /*
    描述 : 资源转让
    参数 ：
            _to : 接受用户地址
            _resource_id ： 资源序号
            _time ： 转让时间
    返回值：
            1  转让成功
            -1 账户不存在
            -2 没这个资源或者资源已经用过了
            -3 转账用户不拥有该资源
    */
    function transferResource(address _to, uint256 _resource_id, string _time, string info) public returns(int256){
        address from = msg.sender;
        if(keccak256(account[_to].name) == keccak256("") )
            return -1;
        if( _resource_id >=resources.length || resources[_resource_id].is_used)
            return -2;
        if(from != resources[_resource_id].owner)
            return -3;
        
        resources[_resource_id].location = account[_to].location;
        resources[_resource_id].owner = _to;
        transactions[_resource_id].push(Transaction(from, _to, _resource_id, _time, info));
        return 1;
        
    }

    
    /*
    描述 : 使用资源
    返回值：
            1  物资使用成功
            -1 账户不存在或者账户不是医院
            -2 没这个资源或者资源已经用过了
            -3 转账用户不拥有该资源
    */
    function deleteResource(uint256 _resource_id,string _time, string info) public returns(int256){
        address from = msg.sender;
        address _to = owner;
        if(keccak256(account[_to].name) == keccak256("") || keccak256(account[from].role) != keccak256("hospital"))
            return -1;
        if( _resource_id >=resources.length || resources[_resource_id].is_used)
            return -2;
        if(from != resources[_resource_id].owner)
            return -3;
            
        resources[_resource_id].owner = _to;
        resources[_resource_id].is_used = true;
        transactions[_resource_id].push(Transaction(from, _to, _resource_id, _time, info));
        return 1;
    }

    // 返回对应rid的交易记录
    function getLogByRid(uint256 _resource_id) public view returns(address[],address[],string[],string[]){
        Transaction[] memory list = transactions[_resource_id];
        address[] memory froms = new address[](list.length);
        address[] memory tos = new address[](list.length);
        string[] memory times = new string[](list.length);
        string[] memory infos = new string[](list.length);
        for(uint i=0;i<list.length;i++){
            froms[i] = list[i].from;
            tos[i]=list[i].to;
            times[i]=list[i].time;
            infos[i]=list[i].info;
        }
        return (froms,tos,times,infos);
    }
    

    // 返回发送请求者的信息
    function getUserInfo() public returns (string,address,string,string) {
        User storage user = account[msg.sender];
        return (user.name, msg.sender, user.location, user.role);
    }

    // 根据地址返回用户信息
    function getUserInfoByAddress(address _addr) public returns  (string,address,string,string) {
        User storage user = account[_addr];
        return (user.name, user.addr, user.location, user.role);
    }
    
    // 返回发送请求的所有资源序号
    function getMyResourcesId() public returns(uint256[]){
        uint256[] storage nums;
        nums.length = 0;
        for(uint i = 0; i < resources.length; i++){
            if (resources[i].owner == msg.sender){
                nums.push(i);
            }
        }
        return nums;
    }
    
    // 返回请求者的物资详细信息
    function getMyResourcesInfo() public returns(uint256[], string[],uint256[],string[],string[],string[],bool[]){
        uint i = 0;
        uint len = 0;
        for(i=0;i<resources.length;i++)
            if(resources[i].owner == msg.sender)
                len++;
        
        uint256[] memory rids = new uint256[](len);
        string[] memory lcas = new string[](len);
        uint256[] memory nums = new uint256[](len);
        string[] memory names = new string[](len);
        string[] memory cas = new string[](len);
        string[] memory bcs = new string[](len);
        bool[] memory useds = new bool[](len);
        
        len = 0;
        for(i = 0; i < resources.length; i++){
            if (resources[i].owner == msg.sender){
                rids[len] = resources[i].resource_id;
                lcas[len] = resources[i].location;
                nums[len] = resources[i].resource_num;
                names[len] = resources[i].resource_name;
                cas[len] = resources[i].category;
                bcs[len] = resources[i].batch_code;
                useds[len] = resources[i].is_used;
                len++;
            }
        }
        
        return (rids,lcas,nums,names,cas,bcs,useds);
    }
    
    // 返回对应类别的所有物资的详细信息
    function getAllResourcesInfoByCategory(string category) public returns(address[],uint256[], string[],uint256[],string[],string[],bool[]){
        uint i = 0;
        index = 0;
        for(i=0;i<resources.length;i++)
            if(keccak256(resources[i].category) == keccak256(category))
                index++;
        
        address[] memory owners = new address[](index);
        uint256[] memory rids = new uint256[](index);
        string[] memory lcas = new string[](index);
        uint256[] memory nums = new uint256[](index);
        string[] memory names = new string[](index);
        string[] memory bcs = new string[](index);
        bool[] memory useds = new bool[](index);
        
        index = 0;
        for(i = 0; i < resources.length; i++){
            if(keccak256(resources[i].category) == keccak256(category)){
                owners[index] = resources[i].owner;
                rids[index] = resources[i].resource_id;
                lcas[index] = resources[i].location;
                nums[index] = resources[i].resource_num;
                names[index] = resources[i].resource_name;
                bcs[index] = resources[i].batch_code;
                useds[index] = resources[i].is_used;
                index++;
            }
        }
        
        return (owners,rids,lcas,nums,names,bcs,useds);
    }
    
   // 返回对应省份所有物资的详细信息
    function getAllResourcesInfoByLocation(string location) public returns(address[],uint256[], uint256[],string[],string[],string[],bool[]){
        uint i = 0;
        index = 0;
        for(i=0;i<resources.length;i++)
            if(keccak256(resources[i].location) == keccak256(location))
                index++;
        
        address[] memory owners = new address[](index);
        uint256[] memory rids = new uint256[](index);
        uint256[] memory nums = new uint256[](index);
        string[] memory names = new string[](index);
        string[] memory cas = new string[](index);
        string[] memory bcs = new string[](index);
        bool[] memory useds = new bool[](index);
        
        index = 0;
        for(i = 0; i < resources.length; i++){
            if(keccak256(resources[i].location) == keccak256(location)){
                owners[index] = resources[i].owner;
                rids[index] = resources[i].resource_id;
                nums[index] = resources[i].resource_num;
                names[index] = resources[i].resource_name;
                cas[index] = resources[i].category;
                bcs[index] = resources[i].batch_code;
                useds[index] = resources[i].is_used;
                index++;
            }
        }
        
        return (owners,rids,nums,names,cas,bcs,useds);
    }
    
    // 返回所有物资的详细信息
    function getAllResourcesInfo() public returns(address[], uint256[], string[],uint256[],string[],string[],string[],bool[]){
        
        address[] memory owners = new address[](resources.length);
        uint256[] memory rids = new uint256[](resources.length);
        string[] memory lcas = new string[](resources.length);
        uint256[] memory nums = new uint256[](resources.length);
        string[] memory names = new string[](resources.length);
        string[] memory cas = new string[](resources.length);
        string[] memory bcs = new string[](resources.length);
        bool[] memory useds = new bool[](resources.length);
        
        index = 0;
        for(index = 0; index < resources.length; index++){
            owners[index] = resources[index].owner;
            rids[index] = resources[index].resource_id;
            lcas[index] = resources[index].location;
            nums[index] = resources[index].resource_num;
            names[index] = resources[index].resource_name;
            cas[index] = resources[index].category;
            bcs[index] = resources[index].batch_code;
            useds[index] = resources[index].is_used;
            
        }
        
        return (owners,rids,lcas,nums,names,cas,bcs,useds);
    }
    
    // 返回所有物资的数量
    function getAllResourcesCnt() public returns(uint256){
        return resources.length;
    }
    
    // 返回未使用的所有物资的数量
    function getNotUsedResourcesCnt() public returns(uint256){
        uint256 cnt = 0;
        for(uint i=0;i<resources.length;i++){
            if(!resources[i].is_used)
                cnt++;
        }
        return cnt;
    }
    
    // 返回对应省份的所有物资的数量
    function getAllResourcesCntByLocation(string location) public returns(uint256){
        uint256 cnt = 0;
        for(uint i=0;i<resources.length;i++){
            if(keccak256(resources[i].location) == keccak256(location))
                cnt++;
        }
        return cnt;
    }
    
    // 返回对应省份的所有未使用物资的数量
    function getNotUsedResourcesCntByLocation(string location) public returns(uint256){
        uint256 cnt = 0;
        for(uint i=0;i<resources.length;i++){
            if(!resources[i].is_used && keccak256(resources[i].location) == keccak256(location))
                cnt++;
        }
        return cnt;
    }
    
    // 返回用户数量
    function getUserCnt() public returns(uint256){
        return userCnt;
    }
    
    // 返回生产商数量
    function getFactoryCnt() public returns(uint256){
        return factoryCnt;
    }
    
}









