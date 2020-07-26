package com.numny.mstarttask.utils;

public class PhoneNumberUtil {
    public static boolean validCellPhone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

}
