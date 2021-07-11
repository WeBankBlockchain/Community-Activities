pragma solidity ^0.4.25;

import "./ParallelContract.sol";
//import "./Transaction.sol";
//不写注释的人都得被凌迟，nndx
//contract Transaction is ParallelContract{
  //  function makePurchase(address _buyer, address _seller, uint32 _price, uint _petId, string _time) external;
//}

//-----------------------------------------------------------------------------------


contract Transaction is ParallelContract{
    function makePurchase(address _buyer, address _seller, uint32 _price, uint _ntId, string _time) external;
}

contract NTFMarket is ParallelContract{
    
    struct NTF{
        bool selling;//是否上架
        uint16 kinds;//NTF种类
        uint32 price;//价格
        string name;//NTF名
      //  string bday;
      //  string disc;
        string picUrl;//图片
        string time;//时间戳
       // string remark;
    }
    
    NTF[] ntfs;
    
    mapping(uint => address) ntfsToOwner;
    
  //  address admin;
    constructor(){
  //      admin = msg.sender;
    }
    //设置只有自己能查看自己的ntf
    function ntfOwner(address _sender, uint _ntfsId) public view returns (address){
       require(_sender == msg.sender, "require admin permission!");
        return ntfsToOwner[_ntfsId];
    }
//设置只有自己能查看自己的ntf数目
	function ntfCount() public view returns(uint) {
		require(msg.sender == msg.sender, "require admin permission");
		return ntfs.length;
	}

	
    
    function newNtf(string _name, uint16 _species, 
                    string _disc, string _picUrl) public{
        uint _id = ntfs.push(NTF(false, _species, 0, _name, _picUrl, "")) - 1;
        
        ntfsToOwner[_id] = msg.sender;
        
    }
    //上架商品
    function sellNtf(uint _ntfsId, uint32 _price, string _time, string _remark) public  {
        require(ntfsToOwner[_ntfsId] == msg.sender, "require ntf owner permission!");
        require(!ntfs[_ntfsId].selling, "ntf is for sale! please put off sale first.");
        ntfs[_ntfsId].price = _price;
        ntfs[_ntfsId].selling = true;
        ntfs[_ntfsId].time = _time;
        //pets[_petId].remark = _remark;
    }
    //下架商品
    function offSale(uint _ntfsId) public {
        require(ntfsToOwner[_ntfsId] == msg.sender , "require ntf owner permission!");
        require(ntfs[_ntfsId].selling, "ntf not for sale now!");
       ntfs[_ntfsId].selling = false;
       // pets[_petId].remark = "";
        //salingPetCount--;
    }
    //当前账户拥有的ntf数目
    function ownerNtfCount(address _owner) public view returns (uint) {
        uint count = 0;
        for(uint i = 0; i < ntfs.length; i++){
            if(ntfsToOwner[i] == _owner){
                count++;
            }
        }
        return count;
    }
    //返回当前账户的ntf
    function getMyNtfs() public view returns (uint[]) {
        uint[] memory myNtfs = new uint[](ownerNtfCount(msg.sender));
        uint count = 0;
        for(uint i = 0; i < ntfs.length; i++){
            if(ntfsToOwner[i] == msg.sender){
                myNtfs[count] = i;
                count++;
            }
        }
        return myNtfs;
    }
    //在售的ntf数目
    function sellingNtfCount() public view returns (uint) {
        uint count = 0;
        for(uint i = 0; i < ntfs.length; i++){
            if(ntfs[i].selling){
                count++;
            }
        }
        return count;
    }
    //返回在售的ntf
    function getNtfsForSale() public view returns (uint[]){
        uint[] memory ntfsForSale = new uint[](sellingNtfCount());
        uint count = 0;
        for(uint i = 0; i < ntfs.length; i++){
            if(ntfs[i].selling){
                ntfsForSale[count] = i;
                count++;
            }
        }
        return ntfsForSale;
    }
    //返回当前调用该合约的账户地址
    function getAddress() public view returns (address) {
        return msg.sender;
    }

    Transaction transactionContract;
    //
    function setTranContAddr(address _transactionContract) public {
        //require(msg.sender == admin, "require admin permission!");
        transactionContract = Transaction(_transactionContract);
    }
    
    function buyNtf(uint _ntfsId, string _time) public payable{
        address _buyer = msg.sender;
        address _seller = ntfsToOwner[_ntfsId];
        
        //write a transaction record
        transactionContract.makePurchase( _buyer, _seller, ntfs[_ntfsId].price, _ntfsId, _time);
        
        ntfsToOwner[_ntfsId] = _buyer;
        ntfs[_ntfsId].selling = false;
    }
    
    function changeOwnership(uint _ntfsId, address _newOwner) public {
        //other contract call, not msg.sender
        //msg.sender is not transitive
       // require(_sender == admin, "require admin permission!");
        ntfsToOwner[_ntfsId] = _newOwner;
    }

    function getNtfById1(uint _ntfsId) external view returns (
                bool selling,//status
                uint16 species,
                uint price,
                string name
               // string bday
                ){
    //    require(ntfs[_ntfsId].selling || msg.sender == ntfsToOwner[_ntfsId] || msg.sender == admin, "no owner or admin permission!");
        return (ntfs[_ntfsId].selling,
               ntfs[_ntfsId].kinds,
                ntfs[_ntfsId].price,
                ntfs[_ntfsId].name);
               // pets[_ntfsId].bday);
    }
    
    function getNtfById2(uint _ntfsId) external view returns (
             //  string disc,
                string picUrl,
                string time
              //  string remark
                ){
    //   require(ntfs[_ntfsId].selling || msg.sender == ntfsToOwner[_ntfsId] || msg.sender == admin, "no owner or admin permission!");
        return (//pets[_petId].disc,
                ntfs[_ntfsId].picUrl,
               ntfs[_ntfsId].time);
                //pets[_petId].remark);
    }
   
    function enableParallel() public
	{
		//Enable some Function to be parallel
		registerParallelFunction("sellPet(uint256,uint256,string,string)",1);
		registerParallelFunction("offSale(uint256)",1);
		registerParallelFunction("changeOwnership(uint256,address,address)",2);
	}

	function disableParallel() public
	{
		//Disable above mentioned functions not to be parallel
		unregisterParallelFunction("sellPet(uint256,uint256,string,string)");
		unregisterParallelFunction("offSale(uint256)");
		unregisterParallelFunction("changeOwnership(uint256,address,address)");		
	}
    
}

