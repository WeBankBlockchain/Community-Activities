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

contract banquanMarket is ParallelContract{
    
    struct banquan{
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
    
    banquan[] banquans;
    
    mapping(uint => address) banquansToOwner;
    
  //  address admin;
    constructor(){
  //      admin = msg.sender;
    }
    //设置只有自己能查看自己的ntf
    function banquanOwner(address _sender, uint _banquansId) public view returns (address){
       require(_sender == msg.sender, "require admin permission!");
        return banquansToOwner[_ntfsId];
    }
//设置只有自己能查看自己的版权数目
	function banquanCount() public view returns(uint) {
		require(msg.sender == msg.sender, "require admin permission");
		return ntfs.length;
	}

	
    
    function newbanquan(string _name, uint16 _species,
                    string _disc, string _picUrl) public{
        uint _id = banquans.push(banquan(false, _species, 0, _name, _picUrl, "")) - 1;
        
        banquansToOwner[_id] = msg.sender;
        
    }
    //上架商品
    function sellbanquan(uint _banquansId, uint32 _price, string _time, string _remark) public  {
        require(nbanquansToOwner[_banquansId] == msg.sender, "require banquan owner permission!");
        require(!banquans[_banquansId].selling, "banquan is for sale! please put off sale first.");
        ntfs[_banquansId].price = _price;
        ntfs[_banquansId].selling = true;
        ntfs[_banquansId].time = _time;
        //pets[_petId].remark = _remark;
    }
    //下架商品
    function offSale(uint _banquansId) public {
        require(banquansToOwner[_banquansId] == msg.sender , "require banquan owner permission!");
        require(nbanquans[_banquansId].selling, "banquan not for sale now!");
       banquans[_banquansId].selling = false;
       // pets[_petId].remark = "";
        //salingPetCount--;
    }
    //当前账户拥有的版权数目
    function ownerbanquanCount(address _owner) public view returns (uint) {
        uint count = 0;
        for(uint i = 0; i < banquans.length; i++){
            if(banquansToOwner[i] == _owner){
                count++;
            }
        }
        return count;
    }
    //返回当前账户的版权
    function getMybanquans() public view returns (uint[]) {
        uint[] memory mybanquans = new uint[](ownerbanquanCount(msg.sender));
        uint count = 0;
        for(uint i = 0; i < banquans.length; i++){
            if(banquansToOwner[i] == msg.sender){
                mybanquans[count] = i;
                count++;
            }
        }
        return mybanquans;
    }
    //在售的版权数目
    function sellingbanquanCount() public view returns (uint) {
        uint count = 0;
        for(uint i = 0; i < banquans.length; i++){
            if(banquans[i].selling){
                count++;
            }
        }
        return count;
    }
    //返回在售的版权
    function getbanquansForSale() public view returns (uint[]){
        uint[] memory banquansForSale = new uint[](sellingbanquanCount());
        uint count = 0;
        for(uint i = 0; i < banquans.length; i++){
            if(banquans[i].selling){
                banquansForSale[count] = i;
                count++;
            }
        }
        return banquansForSale;
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
    
    function buybanquan(uint _banquansId, string _time) public payable{
        address _buyer = msg.sender;
        address _seller = banquansToOwner[_banquansId];
        
        //write a transaction record
        transactionContract.makePurchase( _buyer, _seller, banquans[_banquansId].price, _banquansId, _time);
        
        banquansToOwner[_banquansId] = _buyer;
        banquans[_banquansId].selling = false;
    }
    
    function changeOwnership(uint _banquansId, address _newOwner) public {
        //other contract call, not msg.sender
        //msg.sender is not transitive
       // require(_sender == admin, "require admin permission!");
        ntfsToOwner[_banquansId] = _newOwner;
    }

    function getbanquanById1(uint _banquansId) external view returns (
                bool selling,//status
                uint16 species,
                uint price,
                string name
               // string bday
                ){
    //    require(ntfs[_banquansId].selling || msg.sender == banquansToOwner[_banquansId] || msg.sender == admin, "no owner or admin permission!");
        return (banquans[_banquansId].selling,
               banquans[_banquansId].kinds,
                banquans[_banquansId].price,
               banquans[_banquansId].name);
               // pets[_ntfsId].bday);
    }
    
    function getbanquanById2(uint _banquansId) external view returns (
             //  string disc,
                string picUrl,
                string time
              //  string remark
                ){
    //   require(banquans[_banquansId].selling || msg.sender == banquansToOwner[_banquansId] || msg.sender == admin, "no owner or admin permission!");
        return (//banquans[_banquanId].disc,
                banquans[_banquansId].picUrl,
              banquans[_banquansId].time);
                //banquans[_banquanId].remark);
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

