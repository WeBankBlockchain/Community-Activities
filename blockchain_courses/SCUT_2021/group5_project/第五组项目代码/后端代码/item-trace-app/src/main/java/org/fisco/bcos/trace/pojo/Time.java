package org.fisco.bcos.trace.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public static String getFormatDate(){
        Date date = new Date();
        long times = date.getTime();//时间戳
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

}
