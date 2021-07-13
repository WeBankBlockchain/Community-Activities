pragma solidity >= 0.4.25;
contract UserManager {
    // 用户结构体
    struct User {
        address ethAddr;
        string userName;
        bytes32 password;
    }
    // 合约部署者地址
    address host;
    // 保存所有用户
    User[] userInfos;
    // 用户名到是否注册的映射
    mapping(string => bool) registerPool;
    // 用户名到用户的映射
    mapping(string => User) userPool;
    // 构造函数 设置合约拥有者地址
    constructor() public {
        host = msg.sender;
    }
    // 登录功能
    function doLogin(string memory userName, string memory password)  public view returns (bool) {
        return userPool[userName].password == keccak256(abi.encode(password));
    }
    // 注册检测
    function checkRegister(string memory userName)  public view returns (bool) {
        return registerPool[userName];
    }
	// 用户注册
    function register(address ethAddr, string memory  userName, string memory password) public {
        // 检查用户是否注册 当 require() 中的条件值为假时抛出异常,异常信息为：“用户已经注册”
        require(!checkRegister(userName),"用户已经注册！");
        // 保存注册信息
		userPool[userName] = User(ethAddr, userName, keccak256(abi.encode(password)));
        // 设置用户名为已经注册
		registerPool[userName] = true;
        // 添加到用户数组中
        userInfos.push(userPool[userName]);
    }
    // 更新密码
    function updatePassword(string memory userName, string memory newPwd) public {
        // keccak256加密
		userPool[userName].password = keccak256(abi.encode(newPwd));
    }
    // 获得用户信息
    function getUserInfoByUserName(string memory userName) public view returns (address,string memory){
        require(registerPool[userName],"未查到该用户信息");
        User storage user = userPool[userName];
        return (user.ethAddr,user.userName);
    }
    // 获得所有用户信息
    function getAllUserInfos(uint index)public view returns (address,string memory){
        // 检查下表范围
        require(index < userInfos.length,"下标越界");
        // 检查用户权限，只有合约创建者才能执行该操作
        require(msg.sender == host,"非法访问");
        // 取出用户信息
        User storage user = userInfos[index];
        // 返回数据
        return (user.ethAddr,user.userName);
    }
    // 获得系统用户数
    function getTotalUserNum() public view returns (uint){
        // 检查用户权限，只有合约创建者才能执行该操作
        require(msg.sender == host,"非法访问");
        return userInfos.length;
    }
}
 
