package com.numny.mstarttask.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class EmailUtil {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
