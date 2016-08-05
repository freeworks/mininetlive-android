package com.kouchen.mininetlive.utils;

import java.util.regex.Pattern;

/**
 * Created by cainli on 16/7/18.
 */
public class ValidateUtil {

    public static boolean checkPhone(String phone) {
        return Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}")
                .matcher(phone).matches();
    }

    public static boolean checkPassword(String password) {
        return Pattern
                .compile("[A-Za-z0-9]{5,17}")
                .matcher(password).matches();
    }

    public static boolean checkVCode(String vcode) {
        return Pattern
                .compile("\\d{6}")
                .matcher(vcode).matches();
    }

    public static boolean checkNickname(String name) {
        if (name != null) {
            name = name.trim();
            if (name.length() <= 12 && name.length() > 0) {
                return true;
            }
        }
        return false;
    }
}
