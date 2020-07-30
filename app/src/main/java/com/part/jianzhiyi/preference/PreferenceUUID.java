package com.part.jianzhiyi.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class PreferenceUUID {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static PreferenceUUID preferenceUUID;

    private PreferenceUUID() {
        sharedPreferences = Tools.getApplicationByReflection().getApplicationContext().getSharedPreferences(Constants.SP_UUID_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static PreferenceUUID getInstence() {
        if (preferenceUUID == null) {
            preferenceUUID = new PreferenceUUID();
        }
        return preferenceUUID;
    }

    public void setNotFirst() {
        editor.putBoolean("isFirst", false);
        editor.apply();
    }

    public boolean getFirst() {
        return sharedPreferences.getBoolean("isFirst", true);
    }


    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean isUserLogin() {
        return sharedPreferences.getBoolean("isUserLogin", false);
    }

    public void loginIn() {
        editor.putBoolean("isUserLogin", true);
        editor.apply();
    }

    public void loginOut() {
        //退出登录，清除数据
        editor.putBoolean("isUserLogin", false);
        editor.apply();
        putUserId(-1);
        putUserPhone("");
        putOaid("");
    }


    public void putUserId(long id) {
        editor.putString("userId", id == -1 ? "" : String.valueOf(id));
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString("userId", "");
    }

    public void putUserPhone(String phone) {
        editor.putString("userPhone", phone);
        editor.apply();
    }

    public String getUserPhone() {
        return sharedPreferences.getString("userPhone", "");
    }

    public void putPush_id(String push_id) {
        editor.putString("push_id", push_id);
        editor.apply();
    }

    public String getPush_id() {
        return sharedPreferences.getString("push_id", "");
    }


    public void putCurrentTime(long time) {
        editor.putLong("currentTime", time);
        editor.apply();
    }

    public long getCurrentTime() {
        return sharedPreferences.getLong("currentTime", 0);
    }

    public long getActionTime() {
        return sharedPreferences.getLong("actionTime", 0);
    }

    public void putActionTime(long time) {
        editor.putLong("actionTime", time);
        editor.apply();
    }

    public void changePrivacyState() {
        editor.putBoolean("privacy", true);
        editor.apply();
    }

    public boolean getPrivacyState() {
        return sharedPreferences.getBoolean("privacy", false);
    }

    public void changeShowResume(boolean show) {
        editor.putBoolean("showResume", show);
        editor.apply();
    }

    public boolean getShowResume() {
        return sharedPreferences.getBoolean("showResume", true);
    }

    public void putOaid(String oaid) {
        editor.putString("oaid", oaid);
        editor.apply();
    }

    public String getOaid() {
        return sharedPreferences.getString("oaid", "");
    }

    public void putShowWx(int show_wx) {
        editor.putInt("show_wx", show_wx);
        editor.apply();
    }

    public int getShowWx() {
        return sharedPreferences.getInt("show_wx",1);
    }


    public void putCity(String city) {
        editor.putString("city", city);
        editor.apply();
    }

    public String getCity() {
        return sharedPreferences.getString("city", "");
    }
}
