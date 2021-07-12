pragma solidity ^0.6.4;

contract AccessControl {
    //用户结构
    struct User {
        uint8 userId; //用户Id
        uint8 credit; //用户持有的共享积分
        string userName; //用户名
        string pwd; //用户密码
        string userDescription; //用户描述
        address userAddress; //用户以太坊地址
        uint8[] accessible; //可访问的共享数据列表
        uint8[] msgList; //用户消息列表
    }

    //共享数据结构
    struct Data {
        uint8 dataId; //共享数据Id
        uint8 dataCredit; //共享数据所需积分
        uint8 downloadTimes; //共享数据被下载次数（即成功访问次数）
        uint8[] notAllowed; //不允许访问本数据的用户列表
        string dataName; //共享数据名
        string dataDescription; //共享数据描述
        string dataAddress; //共享数据地址（存储于数据共享系统存储部分）
        string dataType;//共享数据类型
    }

    //消息结构
    struct Message {
        uint8 reqId; //消息发送方
        uint8 resId; //消息接收方
        uint8 dataId; //涉及的共享数据Id
        uint8 status; //0为免费申请中，1为免费申请批准，2为免费申请拒绝
        string text; //消息内容（申请原因）
    }

    //三个永久储存在区块链上的结构数组
    //只有通过调用智能合约中的相关函数，才可以修改三个列表中的信息，完成访问控制操作
    User[] public users; //用户列表

    Data[] public datas; //共享数据列表

    Message[] public msgs; //消息列表

    /**
    Added By Hxy
    因为后面在根据用户名判断某个账号不存在的时候，是使用合约函数isExitUserName
    并且传入的参数是UserName和id：0
    先在mapping里面索引，如果索引到的id为0，就判断为该用户名不存在
    但是这对于系统第一个注册的用户名是不适用的，所以在合约部署的时候应该先初始化一个用户
    真正的用户注册索引将从1开始。
    解决这个bug，后面在使用的时候，禁止用户用这个用户名注册
     */
    //构造函数在合约部署的时候运行，但是这样部署合约的账号就没有注册这个系统的权力了
    constructor() public {
        uint8 n = 0;
        users.push(
            User(
                n,
                0,
                "###", //用户名
                "###", //用户密码
                " ", //用户描述
                msg.sender, //msg.sender就是当前调用方法时的发起人,的地址
                new uint8[](0),
                new uint8[](0)
            )
        );
        adToid[msg.sender] = n; //msg.sender就是当前调用方法时的发起人,的地址
        nameToid["###"] = n;
        //emit NewUser(n, _userName, _userAddress);
    }

    //web3无法调用结构数组相关属性
    //共享数据列表长度获取接口
    function getDataLength() public view returns (uint8) {
        return uint8(datas.length);
    }

    //用户列表长度获取接口
    function getUserLength() public view returns (uint8) {
        return uint8(users.length);
    }

    //消息列表长度获取接口
    function getMsgLength() public view returns (uint8) {
        return uint8(msgs.length);
    }

    //用户地址与用户Id映射
    mapping(address => uint8) adToid;

    //映射调用接口
    function fadToid(address _ad) public view returns (uint8) {
        return adToid[_ad];
    }

    //用户名与用户Id映射
    mapping(string => uint8) nameToid;

    //映射调用接口
    function fnameToid(string memory _name) public view returns (uint8) {
        return nameToid[_name];
    }

    //共享数据名与共享数据Id映射
    mapping(string => uint8) dnameToid;

    //映射调用接口
    function fdnameToid(string memory _name) public view returns (uint8) {
        return dnameToid[_name];
    }

    //共享数据Id与数据拥有者用户Id映射
    mapping(uint8 => uint8) dataToOwner;

    //映射调用接口
    function fdataToOwner(uint8 _id) public view returns (uint8) {
        return dataToOwner[_id];
    }

    //区块链事件，通过监听事件了解访问控制操作是否成功发布到区块链上
    //新用户注册事件
    event NewUser(uint8 userId, string userName, address userAddress);
    //新数据发布事件
    event NewData(uint8 dataId, string dataName);
    //用户信息修改事件
    event AlterUser(uint8, string, address);
    //共享数据信息修改事件
    event AlterData(uint8, string);
    //常规访问事件
    event RequestApproved(uint8 userId, uint8 dataId);
    //免费访问申请事件
    event FreeRequest(uint8 userId, uint8 dataId, uint256 msgId);
    //免费访问批准事件
    event FreeApproved(uint256 freeId);
    //免费访问拒绝事件
    event FreeDenied(uint256 freeId);

    //Added by hxy
    //积分转移事件
    event TransIntegral(uint8 fromUserId, uint8 integralNum, uint8 toUserId);

    //web3返回结构实例时，无法获得结构实例中的数组
    //用户列表中可访问数据列表获取接口
    function getAccessible(uint8 _id) public view returns (uint8[] memory) {
        return users[_id].accessible;
    }

    //可访问数据列表长度接口
    function getAccessibleLength(uint8 _id) public view returns (uint256) {
        return users[_id].accessible.length;
    }

    //共享数据列表中不允许访问用户列表获取接口
    function getNotAllowed(uint8 _id) public view returns (uint8[] memory) {
        return datas[_id].notAllowed;
    }

    //不允许访问用户列表长度接口
    function getNotAllowedLength(uint8 _id) public view returns (uint256) {
        return datas[_id].notAllowed.length;
    }

    //用户列表中消息列表获取接口
    function getMsgList(uint8 _id) public view returns (uint8[] memory) {
        return users[_id].msgList;
    }

    //消息列表长度获取接口
    function getMsgListLength(uint8 _id) public view returns (uint256) {
        return users[_id].msgList.length;
    }

    //用户管理
    //判断用户地址是否存在
    function isExitUserAddress(address _userAddress, uint8 _id)
        public
        view
        returns (bool)
    {
        if (users.length == 0) return false;
        return (adToid[_userAddress] != _id &&
            adToid[_userAddress] != uint8(0));
    }

    //判断用户名是否存在
    function isExitUserName(string memory _username, uint8 _id)
        public
        view
        returns (bool)
    {
        if (users.length == 0) return false;
        return (nameToid[_username] != uint8(0) && nameToid[_username] != _id);
    }

    //创建新用户
    function createUser(
        string memory _userName,
        string memory _pwd,
        address _userAddress
    ) public {
        uint8 n = uint8(users.length);
        users.push(
            User(
                n,
                0,
                _userName,
                _pwd,
                " ",
                _userAddress,
                new uint8[](0),
                new uint8[](0)
            )
        );
        adToid[_userAddress] = n;
        nameToid[_userName] = n;
        emit NewUser(n, _userName, _userAddress);
    }

    //修改用户信息
    function alterUser(
        uint8 _userId,
        string memory _name,
        string memory _pwd,
        string memory _description,
        address _address
    ) public {
        users[_userId].userName = _name;
        users[_userId].pwd = _pwd;
        users[_userId].userDescription = _description;
        users[_userId].userAddress = _address;
        adToid[_address] = _userId;
        nameToid[_name] = _userId;
        emit AlterUser(_userId, _name, _address);
    }

    //共享数据管理
    //判断共享数据名是否存在
    function isExitDataName(
        string memory _name,
        uint8 _id //可以将函数声明为 view 类型，这种情况下要保证不修改状态。
    ) public view returns (bool) {
        if (datas.length == 0) return false;
        return (dnameToid[_name] != uint8(0) && dnameToid[_name] != _id);
    }

    //创建新数据
    function createData(
        uint8 _owner,
        uint8 _credit,
        uint8[] memory _not,
        string memory _name,
        string memory _description,
        string memory _address,
        string memory _type
    ) public {
        uint8 n = uint8(datas.length);
        datas.push(Data(n, _credit, 0, _not, _name, _description, _address, _type));
        dataToOwner[n] = _owner;
        dnameToid[_name] = n;
        users[_owner].accessible.push(n);
        users[_owner].credit += 5; //新数据奖励
        emit NewData(n, _name);
    }

    //修改共享数据信息
    function alterData(
        uint8 _dataId,
        uint8 _credit,
        string memory _name,
        string memory _description,
        string memory _address,
        string memory _type,
        uint8[] memory _not
    ) public {
        datas[_dataId].dataName = _name;
        datas[_dataId].dataCredit = _credit;
        datas[_dataId].dataDescription = _description;
        datas[_dataId].dataAddress = _address;
        datas[_dataId].dataType = _type;
        datas[_dataId].notAllowed = _not;
        dnameToid[_name] = _dataId;
        emit AlterData(_dataId, _name);
    }

    //共享数据访问控制
    //检查使用者积分
    function checkCredit(uint8 _userId, uint8 _dataId)
        public
        view
        returns (bool)
    {
        return (users[_userId].credit >= datas[_dataId].dataCredit);
    }

    //检查是否在可授予范围内
    function checkList(uint8 _dataId, uint8 _userId)
        public
        view
        returns (bool)
    {
        for (uint8 i = 0; i < datas[_dataId].notAllowed.length; i++) {
            if (datas[_dataId].notAllowed[i] == _userId) {
                return true;
            }
        }
        return false;
    }

    //常规访问
    function shareRequest(uint8 _userId, uint8 _dataId) public {
        //扣减使用者积分
        users[_userId].credit -= datas[_dataId].dataCredit;

        //增加拥有者积分
        //users[dataToOwner[_userId]].credit += datas[_dataId].dataCredit;
        //这里是不是有错？
        //应该是
        users[dataToOwner[_dataId]].credit += datas[_dataId].dataCredit;

        datas[_dataId].downloadTimes++;
        users[_userId].accessible.push(_dataId);
        emit RequestApproved(_userId, _dataId);
    }

    /**
    Added by hxy
    用于在用户免费访问数据，
     */
    function freeRequestWithoutApproved(uint8 _userId, uint8 _dataId) public {
        //访问免费数据，可以加分
        users[_userId].credit += 3;
        //数据的访问次数增加
        datas[_dataId].downloadTimes++;
        //该资料被加入到用户的可访问列表中
        users[_userId].accessible.push(_dataId);
        emit RequestApproved(_userId, _dataId);
    }

    /**
    Added by hxy
    用于用户转移积分，
     */
    function transIntegral(
        uint8 _fromUserId,
        uint8 _integralNum,
        uint8 _toUserId
    ) public {
        users[_fromUserId].credit -= _integralNum;
        users[_toUserId].credit += _integralNum;
        emit TransIntegral(_fromUserId, _integralNum, _toUserId);
    }

    //免费访问申请
    function freeRequest(
        uint8 _userId,
        uint8 _dataId,
        string memory _text
    ) public {
        uint8 owner = dataToOwner[_dataId];
        uint8 n = uint8(msgs.length);
        msgs.push(Message(_userId, owner, _dataId, 0, _text));
        users[owner].msgList.push(n);
        users[_userId].msgList.push(n);
        emit FreeRequest(_userId, _dataId, n);
    }

    //免费访问批准
    function freeApproved(uint256 _freeId) public {
        //根据消息的id取到消息要访问的数据id
        uint8 data = msgs[_freeId].dataId;
        //取到要求访问的用户id
        uint8 userId = msgs[_freeId].reqId;
        //修改数据的下载次数
        datas[data].downloadTimes++;
        //该用户的可访问数据数组中又多了一个数据
        users[userId].accessible.push(data);
        //这个访问请求的状态变成1
        msgs[_freeId].status = 1;
        emit FreeApproved(_freeId);
    }

    //免费访问拒绝
    function freeDenied(uint256 _freeId) public {
        //直接改变状态，状态变成2
        msgs[_freeId].status = 2;
        emit FreeDenied(_freeId);
    }
}
