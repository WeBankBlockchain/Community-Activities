pragma solidity ^0.4.24;

contract TestContract {

    event RegisterEvent(bool ret, string res);
    event RegisterPortEvent(bool ret, string res);
    event LoginEvent(bool ret, string res, string token, int user_type);

    event genIdEvent(bool ret, string res, string id);
    event bind2Event(bool ret, string res);
    event initHeadEvent(bool ret, string res);
    event giveRightEvent(bool ret, string res);
    event addNodeEvent(bool ret, string res);

    struct User {
        string passwd;
        string port_name; // 0 卖家 1 港口 2 买家
        int user_type;
        bool is_register;
    }
    struct Node {
        string port;
        string in_time;
        string out_time;
    }
    struct Product {
        string user;
        string[] ports;
        string time;
        string location;
        bool is_init;
        bool is_bind;
        Node[] nodes;
    }
    mapping (string => User) private user_mp;
    mapping (string => string) private token_mp;
    mapping (string => Product) private product_mp;
    bytes port_list;
    uint64 random = 998244353;
    uint32 _random = 114514144;
    uint64 _id = 0;
    
    function uint2str(uint256 v) private returns (string str) {
        bytes memory reversed = new bytes(100);
        uint i = 0;
        while (v != 0) {
            uint remainder = v % 10;
            v = v / 10;
            reversed[i++] = byte(48 + remainder);
        }
        bytes memory s = new bytes(i);
        for (uint j = 0; j < i; j++) {
            s[j] = reversed[i -1 - j];
        }
        str = string(s);
    }
    
    function randint() private returns(uint32) {
        _random = _random * 73518;
        return _random;
    }
    
    function rand() private returns(string) {
        random = random * (10**9 + 7) + 10 ** 9 + 9;
        return uint2str(random);
    }
    
    function compare(string s, string t) private returns(bool) {
        bytes memory _s = bytes(s);
        bytes memory _t = bytes(t);
        if (_s.length != _t.length) {
            return false;
        }
        for (uint i = 0; i < bytes(s).length; i ++) {
            if(_s[i] != _t[i]) {
                return false;
            }
        }
        return true;
    }
    
    function register(string user, string passwd, int user_type) public returns(bool, string) {
        if (user_mp[user].is_register) {
            emit RegisterEvent(false, "register failed, duplicate user name");
            return (false, "register failed, duplicate user name");
        }
        user_mp[user] = User(passwd, "", user_type, true);
        emit RegisterEvent(true, "register ok");
        return (true, "register ok");
    }
    
    function registerPort(string user, string passwd, string port_name) public returns(bool, string){
        if (user_mp[user].is_register) {
            emit RegisterPortEvent(false, "register failed, duplicate user name");
            return (false, "register failed, duplicate user name");
        }
        join(port_list, user);
        join(port_list, port_name);
        user_mp[user] = User(passwd, port_name, 1, true);
        emit RegisterPortEvent(true, "registerPort ok");
        return (true, "registerPort ok");
    }
    
    function login(string user, string passwd) public returns(bool, string, string, int) {
        if (!user_mp[user].is_register || !compare(user_mp[user].passwd, passwd)) {
            emit LoginEvent(false, "login failed, not registered or wrong passwd", "", -1);
            return (false, "login ok, not registered or wrong passwd", "", -1);
        }
        string memory _token = rand();
        token_mp[user] = _token; 
        emit LoginEvent(true, "login ok", _token, user_mp[user].user_type);
        return (true, "login ok", _token, user_mp[user].user_type);
    }
    
    function getPortName(string user) public constant returns (bool, string, string){
        if (!user_mp[user].is_register) {
             return(false, "getPortName failed, not registered", "");
        }
        return(true, "getPortName ok", user_mp[user].port_name);
    }

    function genId() public returns(bool, string, string){
        _id = randint();
        emit genIdEvent(true, "genIdok", uint2str(_id));
        return (true, "genIdok", uint2str(_id));
    }
    
    function bind2(string id, string user, string token) public returns (bool, string){
        if (!compare(token_mp[user], token)) {
            emit bind2Event(false, "bind2 failed, no permission");
            return (false, "bind2 failed, no permission");
        }
        product_mp[id].user = user;
        product_mp[id].is_bind = true;
        emit bind2Event(true, "bind2 ok");
        return (true, "bind2 ok");
    }
    
    function isBind(string id, string user) public constant returns (bool, string) {
        if (product_mp[id].is_bind && compare(product_mp[id].user, user)) {
            return (true, "binded");
        }
        return (false, "not binded");
    }
    
    function isInit(string id) public constant returns (bool, string){
        if (product_mp[id].is_init) {
            return (true, "inited");
        }
        return (false,"not inited");
    }
    
    function initHead(string id, string time, string location, string user, string token) public returns (bool, string) {
        if (!compare(token_mp[user], token)) {
            emit initHeadEvent(false, "initHead failed, no permission");
            return (false, "initHead failed, no permission");
        }
        product_mp[id].time = time;
        product_mp[id].location = location;
        product_mp[id].is_init = true;
        emit initHeadEvent(true, "initHead ok");
        return (true, "initHead ok");
    }
    
    function giveRight(string id, string user, string token) public returns (bool, string) {
        if (!product_mp[id].is_bind || !compare(token_mp[product_mp[id].user], token)) {
            emit giveRightEvent(false, "giveRight failed, not binded or no permission");
            return (false, "giveRight failed, not binded or no permission");
        }
        product_mp[id].ports.push(user);
        emit giveRightEvent(true, "giveRight ok");
        return (true, "giveRight ok");
    }
    
    function hasRight(string id, string user) public constant returns(bool, string) {
        if (!product_mp[id].is_bind) {
            return(true, "not has right, not binded");
        }
        for (uint i = 0; i < product_mp[id].ports.length; i++) {
            if (compare(product_mp[id].ports[i], user)) {
                return(true, "has right");
            }
        }
        return (false, "not has right");
    }
    function addNode(string id, string user, string in_time, string out_time, string token) public returns (bool,string){
        if (!compare(token_mp[user], token)) {
            emit addNodeEvent(false, "addNode failed, no permission");
            return (true, "addNode failed, no permission");
        }
        product_mp[id].nodes.push(Node(user_mp[user].port_name, in_time, out_time));
        emit addNodeEvent(true, "addNode ok");
        return (true, "addNode ok");
    }
    
    function join(bytes storage s, string t) private {
        bytes memory _t = bytes(t);
        s.push(" ");
        for (uint i = 0; i < _t.length; i++) s.push(_t[i]);
    }
    
    function showPath(string id) public constant returns (string) {
        bytes storage res;
        join(res, product_mp[id].time);
        join(res, product_mp[id].location);
        for (uint i = 0; i < product_mp[id].nodes.length; i++) {
            join(res, product_mp[id].nodes[i].in_time);
            join(res, product_mp[id].nodes[i].out_time);
            join(res, product_mp[id].nodes[i].port);
        }
        return string(res);
    }
    
    function showPortList() public constant returns (string){
        return string(port_list);
    }
    
}