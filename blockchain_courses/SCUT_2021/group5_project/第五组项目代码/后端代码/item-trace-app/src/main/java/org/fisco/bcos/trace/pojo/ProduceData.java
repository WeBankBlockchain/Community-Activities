package org.fisco.bcos.trace.pojo;

public class ProduceData {
    public String name;
    public String time;
    public String place;

    ProduceData(){
    }
    ProduceData(String name_,String time_,String place_)
    {
        name=name_;
        time=time_;
        place=place_;
    }

}
