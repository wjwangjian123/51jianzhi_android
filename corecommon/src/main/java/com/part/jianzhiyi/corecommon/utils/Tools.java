package com.part.jianzhiyi.corecommon.utils;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

import androidx.core.app.ActivityCompat;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/23
 * @modified by:shixinxin
 **/
public class Tools {
    public static Application getApplicationByReflection() {
        try {
            return (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication").invoke(null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取操作系统版本
     */
    public static String getPhoneOSVersion() {
        String model = Build.VERSION.RELEASE;
        return model;
    }

    /**
     * 返回厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }


    /**
     * 获取手机型号
     */
    public static String getPhoneType() {
        String model = Build.MODEL;
        return model;
    }

    /**
     * 获取设备ID
     */
    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    // 获取IMEI码
    public static String getIMEI(Context context) {

        try {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                Activity topActivity = ActivityUtils.getTopActivity();
//                if (topActivity != null) {
//                    ActivityCompat.requestPermissions(topActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1001);
//                }
                return "";
            }
            String IMEI = telephonyManager.getDeviceId();
            return IMEI;
        } catch (SecurityException e) {
            return "";
        }
    }

    /**
     * 获取外网的IP(必须放到子线程里处理)
     */
    public static String getNetIp() {
        String ip = "";
        InputStream inStream = null;
        try {
            URL infoUrl = new URL("http://1212.ip138.com/ic.asp");
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "gb2312"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    //builder.append(line).append("\n");
                }
                inStream.close();
                int start = builder.indexOf("[");
                int end = builder.indexOf("]");
                ip = builder.substring(start + 1, end);
                return ip;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5(String input) {
        String result = input;
        if (input != null) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                md.update(input.getBytes("UTF-8"));
                byte[] hash = md.digest();
                StringBuilder hex = new StringBuilder(hash.length * 2);
                for (byte b : hash) {
                    if ((b & 0xFF) < 0x10) hex.append("0");
                    hex.append(Integer.toHexString(b & 0xFF));
                }
                return hex.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getUa(){
        return System.getProperty("http.agent");
    }

    public static String getUa2(Context context){
        WebView webview;
        webview = new WebView(context);
        webview.layout(0, 0, 0, 0);
        WebSettings settings = webview.getSettings();
        String ua = settings.getUserAgentString();
        //Log.e("HJJ", "User Agent:" + ua);
        return webview.getSettings().getUserAgentString();
    }

}
