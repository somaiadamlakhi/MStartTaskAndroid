package com.numny.mstarttask.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static String getCurrentDate(){

        return  new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

    }

    public static void getFormatedDate(Date date){


        android.text.format.DateFormat.format("yyyy-MM-dd", date);
    }
}
