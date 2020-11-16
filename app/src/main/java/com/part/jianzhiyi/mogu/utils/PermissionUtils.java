package com.part.jianzhiyi.mogu.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.fendasz.moku.planet.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;


/**
 * @author Trailwalker
 * @create 2020-07-22 14:35
 * @des
 */
public class PermissionUtils {

    private static String TAG = PermissionUtils.class.getSimpleName();

    public final static int CODE_RECORD_AUDIO = 0;
    public final static int CODE_GET_ACCOUNTS = 1;
    public final static int CODE_READ_PHONE_STATE = 2;
    public final static int CODE_CALL_PHONE = 3;
    public final static int CODE_CAMERA = 4;
    public final static int CODE_ACCESS_FINE_LOCATION = 5;
    public final static int CODE_ACCESS_COARSE_LOCATION = 6;
    public final static int CODE_READ_EXTERNAL_STORAGE = 7;
    public final static int CODE_WRITE_EXTERNAL_STORAGE = 8;

    public final static int CODE_ALL_PERMISSION = 8848;
    public final static int CODE_MULTI_PERMISSION = 8880;


    public final static String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public final static String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public final static String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public final static String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public final static String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public final static String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public final static String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public final static String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public final static String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String[] REQUEST_PERMISSIONS = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };

    public interface PermissionGrantInterface {
        /**
         * 已授权
         *
         * @param requestCode
         */
        void onPermissionGranted(int requestCode);
    }

    /**
     * 动态请求单个权限
     *
     * @param activity
     */
    public static void requestPermission(Activity activity, int requestCode, PermissionGrantInterface grant) {
        if (activity == null) {
            return;
        }
        if (requestCode < 0 || requestCode > REQUEST_PERMISSIONS.length) {
            return;
        }
        if (Build.VERSION.SDK_INT < 23) {
            //版本号小于安卓6.0
            grant.onPermissionGranted(requestCode);
        } else {
            //版本号大于等于安卓6.0
            String requestPermission = REQUEST_PERMISSIONS[requestCode];
            int checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            //判断是否授权了
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                //没有授权，需要进行申请
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    shouldShowRationale(activity, requestCode, requestPermission);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
                }
            } else {
                //用户授权了，可以直接调用相关功能
                grant.onPermissionGranted(requestCode);
            }
        }
    }

    /**
     * 动态请求单个权限的回调
     *
     * @param activity
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param grant
     */
    public static void requestPermissionResult(Activity activity, int requestCode, String[] permissions, int[] grantResults, PermissionGrantInterface grant) {
        if (activity == null) {
            return;
        }
        if (requestCode < 0 || requestCode > REQUEST_PERMISSIONS.length) {
            LogUtils.log(TAG, "illegal requestCode:" + requestCode);
            return;
        }
        if (grantResults != null && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                grant.onPermissionGranted(requestCode);
            } else {
                String permissionError = permissions[requestCode];
                openSettingActivity(activity, "Result:" + permissionError);
            }
        }
    }

    /**
     * 动态请求常量列表中的所有权限
     *
     * @param activity
     * @param codeWriteExternalStorage
     * @param grant
     */
    public static void requestAllPermissions(Activity activity, int codeWriteExternalStorage, PermissionGrantInterface grant) {
        //获取没有被授权的权限
        ArrayList<String> permissionList = getNoGrantedPermission(activity, REQUEST_PERMISSIONS, false);
        ArrayList<String> shouldRationalePermissionList = getNoGrantedPermission(activity, REQUEST_PERMISSIONS, true);
        if (permissionList == null || shouldRationalePermissionList == null) {
            return;
        }

        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), CODE_ALL_PERMISSION);
        } else if (shouldRationalePermissionList.size() > 0) {
            showMessageOkCancel(activity, "should open those permissions", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(activity, shouldRationalePermissionList.toArray(new String[shouldRationalePermissionList.size()]), CODE_ALL_PERMISSION);

                }
            });
        } else {
            grant.onPermissionGranted(CODE_ALL_PERMISSION);
        }
    }

    public static void requestMultiPermissions(Activity activity, String[] requestPermissions, PermissionGrantInterface grant) {
        //获取没有被授权的权限
        ArrayList<String> permissionList = getNoGrantedPermission(activity, requestPermissions, false);
        ArrayList<String> shouldRationalePermissionList = getNoGrantedPermission(activity, requestPermissions, true);
        if (permissionList == null || shouldRationalePermissionList == null) {
            return;
        }

        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), CODE_MULTI_PERMISSION);
        } else if (shouldRationalePermissionList.size() > 0) {
            showMessageOkCancel(activity, "should open those permissions", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(activity, shouldRationalePermissionList.toArray(new String[shouldRationalePermissionList.size()]), CODE_MULTI_PERMISSION);

                }
            });
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
        }
    }

    /**
     * 动态请求常量列表中的所有权限的回调
     *
     * @param activity
     * @param permissions
     * @param grantResults
     * @param grant
     */
    public static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults, PermissionGrantInterface grant) {
        if (activity == null) {
            return;
        }
        HashMap<String, Integer> perms = new HashMap<>();
        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }
        if (notGranted.size() == 0) {
            LogUtils.log(TAG, "all permission success");
            grant.onPermissionGranted(CODE_ALL_PERMISSION);
        } else {
            openSettingActivity(activity, "这些权限需要授权");
        }
    }

    /**
     * 是否应该弹出提示
     *
     * @param activity
     * @param requestCode
     * @param requestPermission
     */
    private static void shouldShowRationale(Activity activity, int requestCode, String requestPermission) {
        showMessageOkCancel(activity, "Rationale:" + requestPermission, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            }
        });
    }

    /**
     * 打开设置界面
     *
     * @param activity
     * @param msg
     */
    private static void openSettingActivity(Activity activity, String msg) {
        showMessageOkCancel(activity, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        });
    }

    /**
     * 弹出对话框
     *
     * @param activity
     * @param msg
     * @param okListener
     */
    private static void showMessageOkCancel(Activity activity, String msg, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(msg)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create().show();
    }

    /**
     * 获取没有授权的权限列表
     *
     * @param activity
     * @param isShouldRationale
     * @return
     */
    private static ArrayList<String> getNoGrantedPermission(Activity activity, String[] requestPermissions, boolean isShouldRationale) {
        ArrayList<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT < 23) {
            //版本号小于安卓6.0
            return permissions;
        } else {
            for (int i = 0; i < requestPermissions.length; i++) {
                String requestPermission = requestPermissions[i];
                int checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
                if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                    //没有被授权，需要去申请
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                        if (isShouldRationale) {
                            permissions.add(requestPermission);
                        }
                    } else {
                        if (!isShouldRationale) {
                            permissions.add(requestPermission);
                        }
                    }
                }
            }
        }
        return permissions;
    }

    /**
     * 查看应用信息的权限 5.0+
     * PACKAGE_USAGE_STATS
     *
     * @param context
     * @return
     */
    public static boolean checkUsageStatsPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //查看是否拥有查看应用信息的权限
            AppOpsManager appOpt = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpt.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
            boolean isGranted = mode == AppOpsManager.MODE_ALLOWED;
            return isGranted;
        }
        return true;
    }

    public static void applyUsageStatsPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            context.startActivity(intent);
        }
    }

}
