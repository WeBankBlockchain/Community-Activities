package com.scut.library.Entity;

public class FinedUser extends User {
    private String name;
    private String fine;
    public FinedUser(String user_id,String name,String fine){
        super(user_id);
        this.name=name;
        this.fine=fine;
    }

    public String getFine() {
        return fine;
    }

    public String getName() {
        return name;
    }
}
