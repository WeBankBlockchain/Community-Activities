package scut.healthcode.util;

import java.util.HashMap;

public class RetMessageFactory {

    /**
     * 生成待返回的消息，ret_code为必须的字段，其余字段和键值自行加入hashmap
     *
     * @param ret_code State code that return front end
     * @author Dejian
     * @return 包含ret_code 字段的hashMap，作为消息返回的统一格式
     * ret_code :   1  成功
     *              2  未知的错误
     */
    public static HashMap<String, Object> newReturnMessage(int ret_code){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ret_code", ret_code);
        return hashMap;
    }
}
