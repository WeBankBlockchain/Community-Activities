package org.fisco.bcos.scutcloud.handler;

import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.scutcloud.util.ResponseWritingUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AnonymousAccessHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = ResultFactory.buildFailResult("can not login");
        ResponseWritingUtil.writeJsonResultToResponse(response, result);
    }
}
