package cn.edu.scut.medicalresourceflow.interceptor;

import cn.edu.scut.medicalresourceflow.annoation.GodToken;
import cn.edu.scut.medicalresourceflow.annoation.PassToken;
import cn.edu.scut.medicalresourceflow.annoation.UserLoginToken;
import cn.edu.scut.medicalresourceflow.exception.BusinessException;
import cn.edu.scut.medicalresourceflow.service.TokenService;
import cn.edu.scut.medicalresourceflow.service.UserService;
import cn.edu.scut.medicalresourceflow.util.ErrorCode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthInterceptor implements HandlerInterceptor {
    @Value("${jwt.token-secret}")
    private String token_secret;

    @Resource
    TokenService tokenService;

    @Resource
    UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(token_secret)).build();
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()){
                return true;
            }
        }


        if (method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()){
                loginChecker(token,jwtVerifier);
                return true;
            }
        }

        if (method.isAnnotationPresent(GodToken.class)){
            GodToken godToken = method.getAnnotation(GodToken.class);
            if (godToken.required()){
                loginChecker(token,jwtVerifier);
                DecodedJWT verify = jwtVerifier.verify(token);
                if (verify.getClaim("role").asString().equals("god")){
                    return true;
                }else {
                    throw new BusinessException(ErrorCode.BAD_REQUEST_FORBIDDEN,"Authorization is denied");
                }
            }
        }


        return true;
//        throw new BusinessException(ErrorCode.BAD_REQUEST_FORBIDDEN,"Authorization is denied");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void loginChecker(String token, JWTVerifier jwtVerifier){
        baseTokenChecker(token, jwtVerifier);
        DecodedJWT verify = jwtVerifier.verify(token);
        if (verify.getClaim("ver").asInt() < tokenService.getUserTokenVerByPrimaryKey(tokenService.getUserId(token))) {
            throw new BusinessException(ErrorCode.CUSTOMIZE_TOKEN_ERROR, "Token is expired");
        }
    }

    //TODO:补充内容
    public void checkAvailable(String token, JWTVerifier jwtVerifier){
        baseTokenChecker(token, jwtVerifier);
//        if (!userService.isMuteByPrimaryKey(tokenService.getUserId(token))) {
//            throw new BusinessException(ErrorCode.CUSTOMIZE_USER_MUTE);
//        }
    }

    public void baseTokenChecker(String token, JWTVerifier jwtVerifier){
        if (token == null){
            throw new BusinessException(ErrorCode.CUSTOMIZE_TOKEN_ERROR,"该请求未携带token");
        }
        try{
            jwtVerifier.verify(token);
        }catch (JWTDecodeException ex){
            ex.printStackTrace();
            throw new BusinessException(ErrorCode.CUSTOMIZE_TOKEN_ERROR,ex.getMessage());
        }

    }

}
