package com.part.jianzhiyi.modulemerchants.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.corecommon.constants.ConstantsDimens;
import com.part.jianzhiyi.corecommon.customview.LoadDataTipView;
import com.part.jianzhiyi.corecommon.ui.CustomToolbarView;
import com.part.jianzhiyi.corecommon.utils.ActivityUtils;
import com.part.jianzhiyi.corecommon.utils.UiDimens;
import com.part.jianzhiyi.corecommon.utils.UiUtils;
import com.part.jianzhiyi.corecommon.utils.toast.CustomToast;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @author:
 * @content：
 * @vision:V1.0.1
 **/
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;
    private FrameLayout container;
    private LoadDataTipView rlLoad;
    private CustomToolbarView toolBar;
    protected View mView;
    Dialog mLoadingDialog;

    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);
        // 设置竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setShowProgress(this, HttpAPI.MODE_CUSTOM);
        mPresenter = createPresenter();
        if (!ConstantsDimens.isUpdate) {
            UiDimens.initPadding();
        }
        setContentView(getLayout());
        init();
        initView();
        afterCreate(savedInstanceState);
        initData();
        setListener();
    }

    @SuppressLint({"InflateParams", "WrongConstant"})
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_main_base, null);
        //设置填充activity_base布局
        super.setContentView(view);
        setImmerseLayout(view.findViewById(R.id.custom_toolbar));
        //加载子类Activity的布局
        initDefaultView(layoutResID);
        rlLoad = container.findViewById(R.id.rl_load);
        toolBar = view.findViewById(R.id.custom_toolbar);

        rlLoad.setOnRetryListener(new LoadDataTipView.OnRetryListener() {
            @Override
            public void onRetry() {
                retry();
            }
        });
    }

    private void initDefaultView(int layoutResID) {
        container = (FrameLayout) findViewById(R.id.fl_activity_child_container);
        mView = LayoutInflater.from(this).inflate(layoutResID, null);
        container.addView(mView);
    }

    public void setImmerseLayout(View view) {
        if (isMaxVersion()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(option);
            int topMargin = ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin;
            UiUtils.margin(view, 0, topMargin + UiUtils.getStatusBarHeight(this), 0, 0);
        }
    }

    public boolean isMaxVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    protected void init() {

    }

    protected void setListener() {

    }

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract P createPresenter();

    protected void retry() {

    }

    @Override
    public void reStartLogin() {
    }

    @Override
    public void showToast(String msg) {
        CustomToast.normalToast(msg);
    }

    protected void setToolBarVisible(boolean isVisible) {
        toolBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    protected void initToolbar(String title, int resId, int textResId, int backImageId) {
        initToolbar(title);
        toolBar.setToolbarBackground(resId);
        toolBar.setTitleColor(textResId);
        toolBar.setBackResource(backImageId);
    }

    protected void initToolbar(String title) {
        toolBar.initNormalToolbar(title, new CustomToolbarView.OnLeftToolbarListener() {
            @Override
            public void onLeftToolbarListener() {
                finish();
            }
        });
    }

    public void addStatusViewWithColor(Activity activity, int color) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UiUtils.getStatusBarHeight(this));
        statusBarView.setBackgroundColor(color);
        contentView.addView(statusBarView, lp);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter == null) {
            return;
        }
        mPresenter.detachView();
        ActivityUtils.removeActivity(this);
    }

    //    默认图相关 start

    protected void setLoadMode(LoadDataTipView.MODE mode) {
        rlLoad.setNoDataUI(mode);
        for (int i = 1; i < container.getChildCount(); i++) {
            container.getChildAt(i).setVisibility(View.GONE);
        }
    }

    protected void setLoadHidden() {
        for (int i = 1; i < container.getChildCount(); i++) {
            container.getChildAt(i).setVisibility(View.VISIBLE);
        }
        rlLoad.setNoDataHidden();
    }

    //    权限申请相关start
    /**
     * 权限组
     */
    private List<String> mPermissionList = new ArrayList<>();


    private String permissionName;//权限名字

    /**
     * 获取权限成功
     */
    protected void permissionResult() {

    }

    protected void permissonFailed() {

    }

    ;

    /**
     * 用于申请权限处理
     * 子类复写 permissionSuccess
     * 用于权限申请成功回调
     * permissionName 权限名字
     *
     * @param permissions
     */
    protected void showPermission(String[] permissions, String permissionName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionList.clear();
            this.permissionName = permissionName;
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            //未授予的权限为空，表示都授予了
            if (mPermissionList.isEmpty()) {
                permissionSuccess(permissionName);
                permissionResult();
            } else {
                //请求权限方法
                //将List转为数组
                permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        } else {
            permissionSuccess(permissionName);
            permissionResult();
        }
    }

    /**
     * 获取权限成功
     */
    protected void permissionSuccess(String permissionName) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 123) {
//            ActivityCompat.requestPermissions(BaseActivity.this, mPermissionList.toArray(new String[mPermissionList.size()]), 1);
//        }
    }

    //处理权限申请回调(写在Activity中)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // 授权被允许
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionSuccess(permissionName);
                    permissionResult();
                } else {
                    permissonFailed();
                }
                return;
            }
        }
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }
//    权限申请相关end


    /**
     * 是否弹出转菊花，在请求之前调用
     * * @param context_activity 必须为Activity的不能是Application的
     */
    protected Dialog setShowProgress(Context context_activity, int mode) {
        if (mLoadingDialog != null) {
            return mLoadingDialog;
        }
        if (context_activity instanceof Activity) {
            LayoutInflater inflater = LayoutInflater.from(context_activity);
            View v = inflater.inflate(R.layout.loading_popup, null);        // 得到加载view
            View layout = v.findViewById(R.id.dialog_view);  // 加载布局
            mLoadingDialog = new Dialog(context_activity, R.style.LoadingDialogTheme); // 创建自定义样式dialog
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));//固定大小的转菊花，如果是全屏，需要设置具体的大小不可以是match_parent
            mLoadingDialog.setVolumeControlStream(mode);
            return mLoadingDialog;
        }
        return null;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
