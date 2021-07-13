package com.fisco.app.pojo;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApplyAgencyForUser {
    private int id;
    private int applyRole;
    private String USCC;
    private int result;

    public ApplyAgencyForUser(int id, int applyRole, String USCC, int result) {
        this.id = id;
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

    public void setApplyrole(int applyRole) {
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
        return "ApplyAgencyForUser{" +
                "id=" + id +
                ", applyRole=" + applyRole +
                ", USCC='" + USCC + '\'' +
                ", result=" + result +
                '}';
    }
}
