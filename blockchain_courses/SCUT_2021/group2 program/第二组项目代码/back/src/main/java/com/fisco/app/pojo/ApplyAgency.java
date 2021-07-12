package com.fisco.app.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对应数据库apply_agency表
 * @author amer
 */

public class ApplyAgency {
    private int id;
    private String applyId;
    private int applyRole;
    private String USCC;
    private int result;

    public ApplyAgency(int id, String applyId, int applyRole, String USCC, int result) {
        this.id = id;
        this.applyId = applyId;
        this.applyRole = applyRole;
        this.USCC = USCC;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplyRole() {
        return applyRole;
    }

    public void setApplyRole(int applyRole) {
        this.applyRole = applyRole;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getUSCC() {
        return USCC;
    }

    public void setUSCC(String USCC) {
        this.USCC = USCC;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ApplyAgency{" +
                "applyId='" + applyId + '\'' +
                ", applyRole=" + applyRole +
                ", USCC='" + USCC + '\'' +
                ", result=" + result +
                '}';
    }
}
