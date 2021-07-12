package scut.healthcode.service;

import org.springframework.stereotype.Service;
import scut.healthcode.entity.UserInfo;

import java.util.HashMap;

@Service
public interface UserService {
    public HashMap<String, Object> upload(UserInfo userInfo);
    public HashMap<String, Object> isHealth(String hashCode);
    public HashMap<String, Object> generateHealthcode(String ID);
}
