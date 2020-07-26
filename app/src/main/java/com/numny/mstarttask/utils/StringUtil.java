package com.numny.mstarttask.utils;

public class StringUtil {
    public static boolean isStringEmpty(String string){
        if(string == null || string.isEmpty()){
            return true;
        }
        else
            return false;
    }
}
