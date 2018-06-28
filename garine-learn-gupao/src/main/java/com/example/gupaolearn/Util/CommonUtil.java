package com.example.gupaolearn.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtil {
    public static void println(String str){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println(sf.format(cal.getTime()) +"[" + Thread.currentThread().getName() + "] " + str);
    }
}
