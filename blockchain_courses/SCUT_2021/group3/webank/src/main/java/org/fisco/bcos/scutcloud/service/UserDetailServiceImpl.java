//package org.fisco.bcos.scutcloud.service;
//
//import org.fisco.bcos.scutcloud.contract.AccessControl;
//import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.math.BigInteger;
//
//
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    @Autowired
//    DeployService deployService;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//            UserService userService = new UserService();
//            userService.deployService =  deployService;
//            Tuple6<BigInteger, BigInteger, String, String, String, String> user = userService.getUser(username);
//            return new User(username,user.getValue2().toString(), AuthorityUtils.createAuthorityList("admin"));
//
//    }
//}
