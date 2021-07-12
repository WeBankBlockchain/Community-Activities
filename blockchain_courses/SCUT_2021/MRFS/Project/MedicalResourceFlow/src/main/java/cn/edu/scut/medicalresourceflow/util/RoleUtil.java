package cn.edu.scut.medicalresourceflow.util;

public enum RoleUtil {

    GOD("god"),
    HOSPITAL("hospital"),
    FACTORY("factory"),
    USER("user"),
    ;

    private final String role;

    private RoleUtil(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
