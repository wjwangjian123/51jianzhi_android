package com.part.jianzhiyi.corecommon.utils;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * @author wavewave
 * @CreateDate: 2019/4/9 10:45 AM
 * @Description:
 * @Version: 1.0
 */
public class NetworkUtils {

    /**
     * 检查网络是否连接
     *
     * @param context 全局context
     * @return true 已连接 false 未连接
     */
    public static Boolean checkNetworkConnect(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 获取当前的网络状态
     *
     * @param context 全局context
     * @return 没有网络-NO
     * WIFI网络-WIFI
     * 4G网络-4G
     * 3G网络-3G
     * 2G网络-2G
     * 未知-Unknown
     */
    public static String getAPNType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {      //无网络
            return "NO";
        }

        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {       //wifi
            return "WIFI";
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                return "4G";
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSUPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_B
                    || nSubType == TelephonyManager.NETWORK_TYPE_EHRPD
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSPAP
                    && !telephonyManager.isNetworkRoaming()) {
                return "3G";
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    || nSubType == TelephonyManager.NETWORK_TYPE_1xRTT
                    || nSubType == TelephonyManager.NETWORK_TYPE_IDEN
                    && !telephonyManager.isNetworkRoaming()) {
                return "2G";
            } else {
                return "Unknown";
            }
        }

        return "Unknown";
    }

    /**
     * 检查手机联网
     *
     * @return
     */
    public static boolean checkNetState() {

        WifiManager wifiManager = (WifiManager) Tools.getApplicationByReflection().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED && wifiInfo != null) { // wifi可用
            return true;
        } else {
            TelephonyManager mTelephonyManager = (TelephonyManager) Tools.getApplicationByReflection().getApplicationContext()
                    .getSystemService(Service.TELEPHONY_SERVICE);
            if (mTelephonyManager.getSimState() != TelephonyManager.SIM_STATE_READY) // SIM卡没有
            {
                return false;
            } else {
                ConnectivityManager cManager = (ConnectivityManager) Tools.getApplicationByReflection().getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    // 能联
                    return true;
                } else {
                    // 不能联
                    return false;
                }
            }
        }
    }

    /**
     * 是否连接上wifi
     *
     * @return
     */
    public static boolean checkWifiConn() {
        ConnectivityManager con =(ConnectivityManager)  Tools.getApplicationByReflection().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return wifi;
    }

    public static boolean checkIsNetConnect(){
        if (checkNetState()){
            return true;
        }else if (checkWifiConn()){
            return true;
        }
        return false;
    }

    /**
     * @param context 判断是否开代理
     * @return
     */
    public static boolean isWifiProxy(Context context) {

        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        String proxyAddress;

        int proxyPort;

        if (IS_ICS_OR_LATER) {

            proxyAddress = System.getProperty("http.proxyHost");

            String portStr = System.getProperty("http.proxyPort");

            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));

        } else {

            proxyAddress = android.net.Proxy.getHost(context);

            proxyPort = android.net.Proxy.getPort(context);

        }

        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }
}
