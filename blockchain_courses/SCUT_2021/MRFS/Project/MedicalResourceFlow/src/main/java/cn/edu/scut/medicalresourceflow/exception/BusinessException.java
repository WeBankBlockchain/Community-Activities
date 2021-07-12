package cn.edu.scut.medicalresourceflow.exception;

import cn.edu.scut.medicalresourceflow.util.ErrorCode;
import cn.edu.scut.medicalresourceflow.util.Result;

/**
 * @author 知日
 * @version 1.0
 */
public class BusinessException extends RuntimeException {
    private static long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private String detailMsg;

    public BusinessException(ErrorCode errorCode, String detailMsg) {
        this.errorCode = errorCode;
        this.detailMsg = detailMsg;
    }

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException() {
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public Result getErrorResult(){
        return Result.NULL()
                .status(errorCode.getStatus())
                .msg(errorCode.getMsg())
                .data(detailMsg)
                .build();
    }
}
