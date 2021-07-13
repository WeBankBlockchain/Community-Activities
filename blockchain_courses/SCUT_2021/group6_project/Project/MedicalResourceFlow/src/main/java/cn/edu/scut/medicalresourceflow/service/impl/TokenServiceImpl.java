package cn.edu.scut.medicalresourceflow.service.impl;

import cn.edu.scut.medicalresourceflow.dao.UserDao;
import cn.edu.scut.medicalresourceflow.entity.User;
import cn.edu.scut.medicalresourceflow.service.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.token-secret}")
    String tokenSecret;

    @Resource
    UserDao userDao;

    @Override
    public String createToken(User user, Integer version){
        Calendar currentTime = Calendar.getInstance();
        Date issuedTime = currentTime.getTime();
        currentTime.add(Calendar.HOUR,4);
        Date expiresTIme = currentTime.getTime();

        String token = JWT.create()
                .withAudience(user.getUserId().toString())
                .withClaim("role",user.getRole())
                .withClaim("ver",version)
                .withIssuedAt(issuedTime)
                .withExpiresAt(expiresTIme)
                .sign(Algorithm.HMAC256(tokenSecret));
        return token;
    }

    @Override
    public Integer getUserId(String token) {
        return Integer.parseInt(JWT.decode(token).getAudience().get(0));
    }

    @Override
    public Integer getUserTokenVerByPrimaryKey(Integer userId) {
        return userDao.getUserTokenVerByPrimaryKey(userId);
    }

    @Override
    public int updateVerByPrimaryKey(Integer userId) {
        return userDao.updateTokenVerByPrimaryKey(userId);
    }
}
