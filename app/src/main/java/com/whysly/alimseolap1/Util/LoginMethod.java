package com.whysly.alimseolap1.Util;

import android.content.Context;

import com.whysly.alimseolap1.views.Activity.MainActivity;

public class LoginMethod {

    private static String LOGIN_METHOD = "";
    private static String USER_NAME = "알리미";
    private static String PROFILE_PIC_URL;
    private static String EMAIL = "";
    private static String SNS_TOKEN = "default";
    private static String SNS_UID;
    private static Context applicationContext = MainActivity.getContextOfApplication();
    //private static SharedPreferences sf = applicationContext.getSharedPreferences("user_data", applicationContext.MODE_PRIVATE);

//    public static LoginMethod getInstance() {
//        if(loginMethod == null) {
//            loginMethod = new LoginMethod();
//        }
//        return loginMethod;
//    }


    public static String getEMAIL() {
        return EMAIL;
    }

    public static void setEMAIL(String EMAIL) {
        LoginMethod.EMAIL = EMAIL;
    }

    public static String getSnsToken() {
        return SNS_TOKEN;
    }

    public static void setSnsToken(String snsToken) {
        SNS_TOKEN = snsToken;
    }

    public static String getSnsUid() {
        return SNS_UID;
    }

    public static void setSnsUid(String snsUid) {
        SNS_UID = snsUid;
    }

    public static String getLoginMethod() {
        return LOGIN_METHOD;
    }

    public static void setLoginMethod(String loginMethod) {
        //Context applicationContext = MainActivity.getContextOfApplication();

        LOGIN_METHOD = loginMethod;
//        SharedPreferences.Editor editor = sf.edit();
//        editor.putString("method", loginMethod);
//        editor.apply();
        //SharedPreferences sf = applicationContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);

    }


    public static String getUserName() {
        return USER_NAME;
    }

    public static void setUserName(String userName) {
        USER_NAME = userName;
//        SharedPreferences.Editor editor = sf.edit();
//        editor.putString("name", userName);
//        editor.apply();
    }

    public static String getProfilePicUrl() {
        return PROFILE_PIC_URL;
    }

    public static void setProfilePicUrl(String profilePicUrl) {
        PROFILE_PIC_URL = profilePicUrl;
//        SharedPreferences.Editor editor = sf.edit();
//        editor.putString("profilepicUrl", profilePicUrl);
//        editor.apply();
    }

}
