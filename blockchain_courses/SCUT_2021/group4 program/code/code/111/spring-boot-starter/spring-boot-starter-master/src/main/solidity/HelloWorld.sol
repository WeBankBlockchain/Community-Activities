pragma solidity ^0.4.2;

contract HelloWorld{
    string name;
	
    
    function set(string n){
		emit org.example.demo.Demos.test(n);
    	name = n;
    }
    
	event org.example.demo.Demos.test(string a);

    function HelloWorld(){
       name = "Hello, World!";
    }

    function get()constant returns(string){
        return name;
    }

}