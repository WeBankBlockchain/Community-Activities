package org.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.dao.UserDAO;
import org.example.demo.pojo.User;
import org.example.demo.utils.KeyCreater;
import org.example.demo.utils.Sha256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public User getByName(String username) {
        return userDAO.findByUsername(username);
    }

    public boolean isExists(String username) {
        User user = getByName(username);
        if(user==null) {
            return false;
        }
        return true;
    }

    public User get(String username, String password) throws NoSuchAlgorithmException {
        String encodepassword =  Sha256.getSha256String(password);
        return userDAO.getByUsernameAndPassword(username,encodepassword);
    }

    public void add(String username, String password) throws NoSuchAlgorithmException {
        String encodePassword =  Sha256.getSha256String(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodePassword);
        KeyCreater keyCreater = new KeyCreater();
        user.setPrivateKey(keyCreater.getPrivateKey());
        user.setPublicKey(keyCreater.getPublicKey());
        user.setAddress(keyCreater.getAddress());
        log.info("new privatekey:{}, publickey:{}, address:{}",
                keyCreater.getPrivateKey(),keyCreater.getPublicKey(),keyCreater.getAddress());
        userDAO.save(user);
        log.info("save success");
    }
}
