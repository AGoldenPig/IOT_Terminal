package com.hit_src.iot_terminal;

import androidx.databinding.ObservableArrayList;

import com.hit_src.iot_terminal.object.Sensor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GlobalVar {
    public volatile static ObservableArrayList<Sensor> sensorList = new ObservableArrayList<>();
    public volatile static ObservableArrayList<String> logList=new ObservableArrayList<>();

    public static int getSensorConnectedAmount() {
        int res=0;
        for(Sensor i:sensorList){
            if(i.isConnected()){
                res+=1;
            }
        }
        return res;
    }

    public static String getLog() {
        StringBuilder sb=new StringBuilder();
        for(String i:logList){
            sb.append(i+"\n");
        }
        return sb.toString();
    }

    public static void log(String s) {
        if(logList.size()>100){
            logList.remove(0);
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss->", Locale.CHINA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        logList.add(simpleDateFormat.format(new Date())+s);
    }
}
