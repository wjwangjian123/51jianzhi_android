package com.part.jianzhiyi.utils;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.R;

import java.util.List;

/**
 * Created by jyx on 2020/7/24
 *
 * @author jyx
 * @describe
 */
public class OpenUtils {

    public static boolean isQQInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                //通过遍历应用所有包名进行判断
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void openQQ(Context context) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.SplashActivity");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);
    }

    public static void openWx(Context context) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);
    }

    private static AlertDialog.Builder alertDialog;
    private static AlertDialog alertDialog1;

    public static void initDialog(Context context, String text) {
        alertDialog = new AlertDialog.Builder(context);
        alertDialog1 = alertDialog.create();
        View view = View.inflate(context, R.layout.dialog_tiaozhuan_tip, null);
        TextView dialog_text = view.findViewById(R.id.dialog_text);
        dialog_text.setText("正在跳转" + text + "...");
        alertDialog1.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog1.setView(view);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.setCancelable(false);
        //显示
        alertDialog1.show();
    }

    public static void cancelDialog() {
        if (alertDialog != null && alertDialog1.isShowing()) {
            alertDialog1.dismiss();
        }
    }
}
