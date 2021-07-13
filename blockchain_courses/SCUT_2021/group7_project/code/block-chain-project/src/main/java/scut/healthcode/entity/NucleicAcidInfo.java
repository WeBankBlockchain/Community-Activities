package scut.healthcode.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NucleicAcidInfo {

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getTime() {
        return time;
    }

    public int getResult() {
        return result;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    private String id;
    private String userName;
    private String time;
    private Integer result;

    @Override
    public String toString() {
        return "NucleicAcidInfo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", time='" + time + '\'' +
                ", result=" + result +
                '}';
    }
}