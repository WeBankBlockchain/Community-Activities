package org.fisco.bcos.scutcloud.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fisco.bcos.scutcloud.result.Result;

import javax.servlet.http.HttpServletResponse;

public class ResponseWritingUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void writeJsonResultToResponse(HttpServletResponse response, Result result) {
        response.setContentType("text/json;charset=utf-8");//geshi
        try {
            System.out.println(mapper.writeValueAsBytes(result));
            response.getWriter().write(mapper.writeValueAsString(result));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
