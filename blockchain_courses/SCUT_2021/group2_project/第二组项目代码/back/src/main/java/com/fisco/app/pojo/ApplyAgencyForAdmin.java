package com.fisco.app.pojo;

import lombok.NoArgsConstructor;


public class ApplyAgencyForAdmin {
    private int id;
    private String applyId;
    private String name;
    private int applyRole;
    private String USCC;
    private int result;

    public ApplyAgencyForAdmin() {
    }

    public ApplyAgencyForAdmin(int id, String applyId, String name, int applyRole, String USCC, int result) {
        this.id = id;
        this.applyId = applyId;
        this.name = name;
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

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApplyRole() {
        return applyRole;
    }

    public void setApplyRole(int applyRole) {
        this.applyRole = applyRole;
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
        return "ApplyAgencyForAdmin{" +
                "id=" + id +
                ", applyId='" + applyId + '\'' +
                ", name='" + name + '\'' +
                ", applyRole=" + applyRole +
                ", USCC='" + USCC + '\'' +
                ", result=" + result +
                '}';
    }
}
