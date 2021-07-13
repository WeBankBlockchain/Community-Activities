package cn.edu.scut.medicalresourceflow.exception;

import cn.edu.scut.medicalresourceflow.util.ErrorCode;
import cn.edu.scut.medicalresourceflow.util.Result;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * @description: 处理逻辑等异常
     * @param ex
     * @param response
     * @return: com.sgmd.backend.util.Result
     * @author 知日
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result errorHandle(BusinessException ex, HttpServletResponse response){
        return ex.getErrorResult();
    }

    /**
     * @description: 处理参数校验异常
     * @param:  * @param null
     * @return:
     * @author 知日
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result errorHandle(ConstraintViolationException ex, HttpServletResponse response){
        ex.printStackTrace();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)){
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation:constraintViolations){
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errMsg = msgBuilder.toString();
            if (errMsg.length() > 1){
                errMsg = errMsg.substring(0,errMsg.length()-1);
            }
            return Result.NULL()
                    .errorCode(ErrorCode.CUSTOMIZE_PARAM_VALIDATION_ERROR)
                    .data(errMsg)
                    .build();
        }
        return Result.NULL()
                .errorCode(ErrorCode.CUSTOMIZE_PARAM_VALIDATION_ERROR)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result errorHandle(MethodArgumentNotValidException ex, HttpServletResponse response){
        ex.printStackTrace();
        return Result.NULL()
                .errorCode(ErrorCode.CUSTOMIZE_PARAM_VALIDATION_ERROR)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(JWTDecodeException.class)
    @ResponseBody
    public Result errorHandle(MethodArgumentNotValidException ex){
        ex.printStackTrace();
        return Result.NULL()
                .errorCode(ErrorCode.CUSTOMIZE_TOKEN_ERROR)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result errorHandle(Exception ex){
        ex.printStackTrace();
        return Result.BAD()
                .data(ex.getMessage())
                .build();
    }

}
