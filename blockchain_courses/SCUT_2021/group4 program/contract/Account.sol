pragma solidity ^0.4.25;

import "./ParallelContract.sol";

contract Account is ParallelContract{
    
    //address admin;
    
    address[] account;
    address sender;
    
    constructor (){
     /nder= msg.sender;//把管理员设部署时的地址，意为只有自己能修改自己
    }
 /*   
    modifier onlyAdmin(address _sender){
        require(_sender == admin, "require admin permission!");
        _;
    }
*/
    mapping(address => uint) balance;
    
    function setsender()public
    {
        sender=msg.sender;
    }

    function transfer(address to, uint money) public {
        require(to != 0x0, "please provide reciever address!");
        _transfer( msg.sender, to, money);
    }
    
    function _transfer(address _from, address _to, uint _money) public payable{
        require(balance[_from] >= _money, "not enough money!");
        balance[_from] = balance[_from] - _money;
        balance[_to] = balance[_to] + _money;
    }
    
    function newAccount(address _address, uint256 _money) public {
        balance[_address] = _money;
    }

    function balanceOf(address _address) public view returns (uint) {
        return balance[_address];
    }

    function bonus(address _address, uint _money) public {
        balance[_address] = balance[_address] + _money;
    }
    
  //  function isAdmin() public returns (bool) {
    //    return (admin == msg.sender);
  //  }
    
    function myBalance() public view returns(uint) {
        return balance[sender];
    }
    
    function myAddress() public view returns(address) {
        return sender;
    }
    
    function enableParallel() public
	{
		//Enable functions
		registerParallelFunction("_transfer(address,address,address,uint256)", 3);
		registerParallelFunction("bonus(address,uint256)",1);
		registerParallelFunction("newAccount(address, uint256)",1);
		
	}

	function disableParallel() public
	{
		//Disable Functions
		unregisterParallelFunction("_transfer(address,address,address,uint256)");
        unregisterParallelFunction("bonus(address,uint256)");
        unregisterParallelFunction("newAccount(address, uint256)");
        
	}
}