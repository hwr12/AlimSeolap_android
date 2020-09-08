package com.whysly.alimseolap1.Util;

public class LoginMethod {

    private static String LOGIN_METHOD;
    private static String USER_NAME;

    public static String getLoginMethod() {
        return LOGIN_METHOD;
    }

    public static void setLoginMethod(String loginMethod) {
        LOGIN_METHOD = loginMethod;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static void setUserName(String userName) {
        USER_NAME = userName;
    }
}
