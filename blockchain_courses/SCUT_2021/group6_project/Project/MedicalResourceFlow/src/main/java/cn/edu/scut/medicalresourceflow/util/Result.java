package cn.edu.scut.medicalresourceflow.util;
/**
 * @author 知日
 * @version 1.0
 * @date 2021/1/20 22:32
 */
public class Result {
    private int status;
    private String msg;
    private Object data;

    public Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Result(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public static class ResultBuilder{
        private int status;
        private String msg;
        private Object data;

        public ResultBuilder() {
        }

        public ResultBuilder(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public ResultBuilder(ErrorCode errCode){
            this.status=errCode.getStatus();
            this.msg=errCode.getMsg();
        }

        public ResultBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ResultBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResultBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public ResultBuilder errorCode(ErrorCode errorCode){
            this.status=errorCode.getStatus();
            this.msg=errorCode.getMsg();
            return this;
        }

        public Result build(){
            return new Result(status,msg,data);
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public Object getData() {
            return data;
        }
    }
    public static ResultBuilder OK(){
        return new ResultBuilder(ErrorCode.SUCCESS);
    }

    public static ResultBuilder BAD(){
        return new ResultBuilder(ErrorCode.BAD_REQUEST_COMMON);
    }

    public static ResultBuilder NULL(){
        return new ResultBuilder();
    }
}
