package org.fisco.bcos.scutcloud.handler;

import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.scutcloud.util.ResponseWritingUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 * 登录失败处理逻辑，返回前端 json 数据提示 “登录失败”，并且提示失败原因
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Result result = null;
        // 密码错误
        if (exception instanceof BadCredentialsException) {
            result = ResultFactory.buildFailResult("pwd error");
        }
        // 账号不可用
        else if (exception instanceof DisabledException) {
            result = ResultFactory.buildFailResult("account error");
        }
        // 用户不存在
        else if (exception instanceof InternalAuthenticationServiceException) {
            result = ResultFactory.buildFailResult("user doesn't exist");
        }
        // 其他错误
        else {
            result = ResultFactory.buildFailResult("common error");
        }
        ResponseWritingUtil.writeJsonResultToResponse(response, result);
    }
}