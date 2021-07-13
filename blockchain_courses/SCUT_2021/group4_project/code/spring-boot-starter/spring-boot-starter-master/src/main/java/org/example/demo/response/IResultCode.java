package org.example.demo.response;

import java.io.Serializable;


public interface IResultCode extends Serializable {

    /**
     * 获取状态码
     *
     * @return code
     */
    int getCode();

    /**
     * 获取状态信息
     *
     * @return message
     */
    String getMessage();
}
