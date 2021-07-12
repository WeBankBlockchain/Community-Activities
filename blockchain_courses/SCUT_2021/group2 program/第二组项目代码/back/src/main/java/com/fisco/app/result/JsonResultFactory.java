package com.fisco.app.result;



/**
 * 生产 json 结果的工厂（单例模式）
 * @author amer
 */
public class JsonResultFactory {
    private static JsonResultFactory instance;

    private JsonResultFactory() {
    }

    /**
     * 获得工厂实例
     */
    synchronized public static JsonResultFactory getInstance() {
        if(instance == null) {
            instance = new JsonResultFactory();
        }
        return instance;
    }

    /**
     * 构建不带数据的成功结果
     */
    public JsonResult buildSuccessResult() {
        return new JsonResult(true);
    }

    /**
     * 构建带数据的成功结果
     * @param data 要携带的数据
     */
    public JsonResult buildSuccessResult(Object data) {
        return new JsonResult(true, data);
    }

    /**
     * 构建不带数据的失败结果
     * @param resultCode 状态码以及响应消息
     */
    public JsonResult buildFailResult(ResultCode resultCode) {
        return new JsonResult(false, resultCode);
    }

    /**
     * 构建带数据的失败结果
     * @param resultCode 状态码以及响应消息
     * @param data 要携带的数据
     */
    public JsonResult buildFailResult(ResultCode resultCode, Object data) {
        return new JsonResult(false, resultCode, data);
    }

}

