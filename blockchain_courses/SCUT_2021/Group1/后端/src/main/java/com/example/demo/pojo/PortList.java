package com.example.demo.pojo;

import java.util.ArrayList;

public class PortList {
    private ArrayList<PortUnit> arrayList;
    public PortList(ArrayList<PortUnit> arrayList){
        this.arrayList = arrayList;
    }

    public ArrayList<PortUnit> getArrayList() {
        return arrayList;
    }
}
