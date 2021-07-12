package com.example.demo.pojo;

import java.util.ArrayList;

public class QueryRes {

    private String time;
    private String location;
    private ArrayList<Node> nodes;

    public QueryRes(String time, String location, ArrayList<Node> nodes) {
        this.time = time;
        this.location = location;
        this.nodes = nodes;
    }
    public String getTime() {
        return time;
    }
    public String getLocation() {
        return location;
    }
    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
