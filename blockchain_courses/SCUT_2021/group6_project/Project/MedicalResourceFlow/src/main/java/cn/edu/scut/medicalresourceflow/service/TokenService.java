package cn.edu.scut.medicalresourceflow.service;


import cn.edu.scut.medicalresourceflow.entity.User;

public interface TokenService {
    String createToken(User user, Integer version);

    Integer getUserId(String token);

    Integer getUserTokenVerByPrimaryKey(Integer userId);

    int updateVerByPrimaryKey(Integer userId);
}
