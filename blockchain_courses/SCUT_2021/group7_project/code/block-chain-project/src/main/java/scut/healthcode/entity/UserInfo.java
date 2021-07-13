package scut.healthcode.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserInfo {

    private String id;
    private String name;
    private String residence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", residence='" + residence + '\'' +
                '}';
    }
}
