pragma solidity ^0.4.25;
//import "./NTFMarket.sol";
import "./ParallelContract.sol";
//import "./Account.sol";

contract Account is ParallelContract{
    function _transfer( address _from, address _to, uint _money) external;
}

contract banquanMarket is ParallelContract{
    function banquanOwner(address sender,uint _banquanId) external view returns (address);
    function changeOwnership( uint _banquanId,address _sender) external;
}

contract Transaction is ParallelContract{
    banquanMarket banquanContract;
    Account accountContract;
    struct Record{
        address buyer;//购买者
        address seller;//售出者
        uint banquanId;
        uint price;
        uint status;//0 for normal, 1 for arbitrating, 20 for arbitrated fail, 21 for arbitrated success
        string time;//时间戳
        string reason;//购买原因
    }
    //存放记录
    Record[] records;
    //交易次数(有意义吗？)
    mapping(address => uint) myRecordCount;
    
   // address admin;
    uint arbitCount;
    
    constructor (){
       // admin = msg.sender;
    }
    //约束，当输入地址为admin时才能执行有该约束的函数
    /*modifier onlyAdmin(address _sender){
        require(_sender == admin,"require admin permission!");
        _;
    }
    
  */
    
    function setbanquanAndAccntContAddr(address _nftContract, address _accountContract) public  {
       banquanContract = banquanMarket(_banquanContract);
        accountContract = Account(_accountContract);
    }
    
    function getRecordCount() public view returns(uint){
        return records.length;
    } 
    //交易
    function makePurchase( address _buyer, address _seller,
                            uint32 _price, uint _banquanId, string _time) external  payable{
                                
        accountContract._transfer( _buyer, _seller, _price);
        //记录
        records.push(Record(_buyer, _seller, _banquanId, _price, 0, _time, ""));
        myRecordCount[_buyer]++;
        myRecordCount[_seller]++;
    }
    
    function getMyRecords() public view returns (uint[]){
        uint[] memory myRecord = new uint[](myRecordCount[msg.sender]);
        uint count = 0;
        for(uint i = 0; i < records.length; i++){
            if(records[i].buyer == msg.sender||records[i].seller == msg.sender){
                myRecord[count] = i;
                count++;
            }
        }
        return myRecord;
    }
//查看该商品的某次交易
    function getRecordById(uint _recordId) public view returns (
                address buyer, 
                address seller,
                uint banquanId,
                uint price,
                uint status,
                string time,
                string reason){
        return (records[_recordId].buyer,
		        records[_recordId].seller,
		        records[_recordId].banquanId,
		        records[_recordId].price,
		        records[_recordId].status,
		        records[_recordId].time,
		        records[_recordId].reason);
    }
    
    function requestArbitration(uint _recordId, string _reason) public {
        require(msg.sender == records[_recordId].buyer, "only buyer can request Arbitration!");
        require(banquanContract.ntfOwner(msg.sender,records[_recordId].banquanId) == records[_recordId].buyer, "banquan does not belong to you!");
        require(records[_recordId].status == 0, "being arbitrated or arbitration have been processed");
        records[_recordId].reason = _reason;
        records[_recordId].status = 1;
        arbitCount++;
    }
    
    function getAllArbitrations() public view  returns (uint[]) {
        uint[] memory allArbit = new uint[](arbitCount);
        uint count = 0;
        for(uint i = 0; i < records.length; i++){
            if(records[i].status > 0){
                allArbit[count] = i;
                count++;
            }
        }
        return allArbit;
    }
    
    function approveArbitration(uint _recordId) public  {
        address _buyer = records[_recordId].buyer;
        address _seller = records[_recordId].seller;
        uint _price = records[_recordId].price;
        uint _banquanId = records[_recordId].banquanId;
        accountContract._transfer( _seller, _buyer, _price);
        banquanContract.changeOwnership( _banquanId, _seller);
        records[_recordId].status = 21;
        arbitCount--;
    }
    
    function denyArbitration(uint _recordId, string _reason) public  {
        records[_recordId].reason = _reason;
        records[_recordId].status = 20;
        arbitCount--;
    }
    
    
  //-------------------------------------------------------------------------------------------------------------------------------------------  
    function enableParallel() public
	{
		//Enable funcstions 
		registerParallelFunction("requestArbitration(uint256,sting)",1);
		registerParallelFunction("denyArbitrarion(uint256,string)",1);
	}

	function disableParallel() public
	{
		//Disable functions
		unregisterParallelFunction("requestArbitration(uint256,string)");
		unregisterParallelFunction("denyArbitrarion(uint256,string)");
	}
}

