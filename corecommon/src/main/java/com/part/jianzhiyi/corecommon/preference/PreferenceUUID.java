package com.part.jianzhiyi.corecommon.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.part.jianzhiyi.corecommon.constants.Constants;
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
        putStatus(0);
        putToken("");
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
        return sharedPreferences.getInt("show_wx", 1);
    }


    public void putCity(String city) {
        editor.putString("city", city);
        editor.apply();
    }

    public String getCity() {
        return sharedPreferences.getString("city", "");
    }

    //pv
    public void putPv(String pv) {
        editor.putString("pv", pv);
        editor.apply();
    }

    public String getPv() {
        return sharedPreferences.getString("pv", "");
    }

    //pe
    public void putPe(String pe) {
        editor.putString("pe", pe);
        editor.apply();
    }

    public String getPe() {
        return sharedPreferences.getString("pe", "");
    }

    //pt
    public void putPt(String pt) {
        editor.putString("pt", pt);
        editor.apply();
    }

    public String getPt() {
        return sharedPreferences.getString("pt", "");
    }

    //androidid
    public void putAndroidid(String androidid) {
        editor.putString("androidid", androidid);
        editor.apply();
    }

    public String getAndroidid() {
        return sharedPreferences.getString("androidid", "");
    }

    //imei
    public void putImei(String imei) {
        editor.putString("imei", imei);
        editor.apply();
    }

    public String getImei() {
        return sharedPreferences.getString("imei", "");
    }

    //ua
    public void putUa(String ua) {
        editor.putString("ua", ua);
        editor.apply();
    }

    public String getUa() {
        return sharedPreferences.getString("ua", "");
    }

    //ua2
    public void putUa2(String ua2) {
        editor.putString("ua2", ua2);
        editor.apply();
    }

    public String getUa2() {
        return sharedPreferences.getString("ua2", "");
    }

    //version
    public void putVersion(String version) {
        editor.putString("version", version);
        editor.apply();
    }

    public String getVersion() {
        return sharedPreferences.getString("version", "");
    }

    //token
    public void putToken(String token) {
        editor.putString("token", token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    //status 商户端1 用户端0
    public void putStatus(int status) {
        editor.putInt("status", status);
        editor.apply();
    }

    public int getStatus() {
        return sharedPreferences.getInt("status", 0);
    }

    //实名的名称
    public void putMerName(String merName) {
        editor.putString("merName", merName);
        editor.apply();
    }

    public String getMerName() {
        return sharedPreferences.getString("merName", "");
    }

    //判断是企业还是个人
    public void putIsEnterprise(int isEnterprise) {
        editor.putInt("isEnterprise", isEnterprise);
        editor.apply();
    }

    public int getIsEnterprise() {
        return sharedPreferences.getInt("isEnterprise", 0);
    }

//    //判断是否实名认证
//    public void putIsAuth(int isAuth) {
//        editor.putInt("isAuth", isAuth);
//        editor.apply();
//    }

//    public int getIsAuth() {
//        return sharedPreferences.getInt("isAuth", 0);
//    }

    public boolean getisMerGuide() {
        return sharedPreferences.getBoolean("isMerGuide", false);
    }

    public void putisMerGuide() {
        editor.putBoolean("isMerGuide", true);
        editor.apply();
    }
}
