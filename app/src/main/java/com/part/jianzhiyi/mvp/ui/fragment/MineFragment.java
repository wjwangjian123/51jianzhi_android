package com.part.jianzhiyi.mvp.ui.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meiqia.core.MQManager;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.corecommon.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.dialog.SignDialog;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddSignEntity;
import com.part.jianzhiyi.model.entity.DaySignEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.mvp.contract.user.MineContract;
import com.part.jianzhiyi.mvp.presenter.mine.MinePresenter;
import com.part.jianzhiyi.mvp.ui.activity.IntegralActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineAboutActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineDeliveryActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineFavouriteActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineFeekbackActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineSettingActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineUpdateProfileActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineUpdateResumeActivity;
import com.part.jianzhiyi.mvp.ui.activity.MyWalletActivity;
import com.part.jianzhiyi.utils.IntentUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by jyx on 2020/7/14
 *
 * @author jyx
 * @describe
 */
@Route(path = "/app/fragment/mine")
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.IMineView, View.OnClickListener {

    private TextView mMineTvPhone;
    private ImageView mMineIvEdit;
    private TextView mMineTvAdvantage1;
    private TextView mMineTvAdvantage2;
    private TextView mMineTvAdvantage3;
    private LinearLayout mMineLinearYue;
    private TextView mMineTvMoney;
    private LinearLayout mMineLinearIntegral;
    private TextView mMineTvIntegral;
    private TextView mMineTvSign;
    private LinearLayout mMineLlSee;
    private LinearLayout mMineLlJoined;
    private LinearLayout mMineLlApproved;
    private LinearLayout mMineLlDoned;
    private RelativeLayout mRlInfo;
    private RelativeLayout mRlSwitch;
    private RelativeLayout mRlFavourite;
    private RelativeLayout mRlService;
    private RelativeLayout mRlFeekback;
    private RelativeLayout mRlAbout;
    private RelativeLayout mRlSet;
    private DaySignEntity mDaySignEntity;
    private String username;

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMineTvPhone = view.findViewById(R.id.mine_tv_phone);
        mMineIvEdit = view.findViewById(R.id.mine_iv_edit);
        mMineTvAdvantage1 = view.findViewById(R.id.mine_tv_advantage1);
        mMineTvAdvantage2 = view.findViewById(R.id.mine_tv_advantage2);
        mMineTvAdvantage3 = view.findViewById(R.id.mine_tv_advantage3);
        mMineLinearYue = view.findViewById(R.id.mine_linear_yue);
        mMineTvMoney = view.findViewById(R.id.mine_tv_money);
        mMineLinearIntegral = view.findViewById(R.id.mine_linear_integral);
        mMineTvIntegral = view.findViewById(R.id.mine_tv_integral);
        mMineTvSign = view.findViewById(R.id.mine_tv_sign);
        mMineLlSee = view.findViewById(R.id.mine_ll_see);
        mMineLlJoined = view.findViewById(R.id.mine_ll_joined);
        mMineLlApproved = view.findViewById(R.id.mine_ll_approved);
        mMineLlDoned = view.findViewById(R.id.mine_ll_doned);
        mRlInfo = view.findViewById(R.id.rl_info);
        mRlSwitch = view.findViewById(R.id.rl_switch);
        mRlFavourite = view.findViewById(R.id.rl_favourite);
        mRlService = view.findViewById(R.id.rl_service);
        mRlFeekback = view.findViewById(R.id.rl_feekback);
        mRlAbout = view.findViewById(R.id.rl_about);
        mRlSet = view.findViewById(R.id.rl_set);
        setToolbarVisible(false);
        if (mPresenter.isUserLogin()) {
            mMineTvPhone.setText(PreferenceUUID.getInstence().getUserPhone());
        } else {
            mMineTvPhone.setText("点击登录");
        }
        mPresenter.userInfo(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void afterCreate() {
        //获取签到情况
        mPresenter.getDaySign(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    protected void setListener() {
        super.setListener();
        mMineIvEdit.setOnClickListener(this);
        mMineLinearYue.setOnClickListener(this);
        mMineLinearIntegral.setOnClickListener(this);
        mMineLlSee.setOnClickListener(this);
        mMineLlJoined.setOnClickListener(this);
        mMineLlApproved.setOnClickListener(this);
        mMineLlDoned.setOnClickListener(this);
        mRlInfo.setOnClickListener(this);
        mRlSwitch.setOnClickListener(this);
        mRlFavourite.setOnClickListener(this);

        mRlService.setOnClickListener(this);
        mRlFeekback.setOnClickListener(this);
        mRlAbout.setOnClickListener(this);
        mRlSet.setOnClickListener(this);
        mMineTvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPresenter.isUserLogin()) {
                    mActivity.reStartLogin();
                    return;
                }
            }
        });
        mMineTvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "mine_refresh_resume");
                mPresenter.getaddMd("64");
                //签到
                mPresenter.addDaySign(PreferenceUUID.getInstence().getUserId(), String.valueOf(mDaySignEntity.getData().getDay() + 1));
            }
        });
    }

    private long clickTime = 0;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_about) {
            MobclickAgent.onEvent(getActivity(), "mine_about");
            Intent intent = new Intent(mActivity, MineAboutActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
            return;
        }
        if (!mPresenter.isUserLogin()) {
            mActivity.reStartLogin();
            return;
        }
        if (v.getId() == R.id.rl_set) {
            MobclickAgent.onEvent(getActivity(), "mine_set");
            Intent intent = new Intent(mActivity, MineSettingActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_feekback) {
            MobclickAgent.onEvent(getActivity(), "mine_feekback");
            Intent intent = new Intent(mActivity, MineFeekbackActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
        }
        if (v.getId() == R.id.rl_favourite) {
            MobclickAgent.onEvent(getActivity(), "mine_favourite");
            Intent intent = new Intent(getActivity(), MineFavouriteActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
        }
        if (v.getId() == R.id.rl_info) {
            MobclickAgent.onEvent(getActivity(), "mine_resume");
            mPresenter.getaddMd("67");
            Intent intent = new Intent(getActivity(), MineUpdateResumeActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
        }
        if (v.getId() == R.id.mine_iv_edit) {
            MobclickAgent.onEvent(getActivity(), "mine_edit");
            Intent intent = new Intent(mActivity, MineUpdateProfileActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
        }
        if (v.getId() == R.id.mine_ll_see) {
            MobclickAgent.onEvent(getActivity(), "mine_see");
            mPresenter.getaddMd("65");
            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
            intent.putExtra("positionType", 1);
            startActivity(intent);
        }
        if (v.getId() == R.id.mine_ll_joined) {
            MobclickAgent.onEvent(getActivity(), "mine_joined");
            mPresenter.getaddMd("66");
            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
            intent.putExtra("positionType", 2);
            startActivity(intent);
        }
        if (v.getId() == R.id.mine_ll_approved) {
            MobclickAgent.onEvent(getActivity(), "mine_approved");
            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
            intent.putExtra("positionType", 3);
            startActivity(intent);
        }
        if (v.getId() == R.id.mine_ll_doned) {
            MobclickAgent.onEvent(getActivity(), "mine_doned");
            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
            intent.putExtra("positionType", 4);
            startActivity(intent);
        }
        if (v.getId() == R.id.mine_linear_yue) {
            Intent intent = new Intent(getActivity(), MyWalletActivity.class);
            intent.putExtra("type", 0);
            startActivity(intent);
        }
        if (v.getId() == R.id.mine_linear_integral) {
            if (!PreferenceUUID.getInstence().getUserId().equals(null) && !PreferenceUUID.getInstence().getUserId().equals("")) {
                mPresenter.getAddInteg(PreferenceUUID.getInstence().getUserId(), 1, "0");
            }
        }
        if (v.getId() == R.id.rl_switch) {
            MobclickAgent.onEvent(getActivity(), "mine_switch");
            mPresenter.getaddMd("68");
            ARouter.getInstance().build("/merchants/activity/choose").withInt("type", 0).navigation();
        }
        if (v.getId() == R.id.rl_service) {
            if (System.currentTimeMillis() - clickTime > 3000) {
                clickTime = System.currentTimeMillis();
                //跳转到美洽客服
                //联系客服
                checkPermission();
            } else {
                showToast("点击过于频繁请稍后再试");
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        // 快手SDK所需相关权限，存储权限，此处配置作用于流量分配功能，关于流量分配，详情请咨询商务;如果您的APP不需要快手SDK的流量分配功能，则无需申请SD卡权限
        if (!(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        // 如果需要的权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            HashMap<String, String> clientInfo = new HashMap<>();
            clientInfo.put("name", username);
            clientInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            clientInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            clientInfo.put("身份", "用户");
            HashMap<String, String> updateInfo = new HashMap<>();
            updateInfo.put("name", username);
            updateInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            updateInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            updateInfo.put("身份", "用户");
            Intent intent = new MQIntentBuilder(getActivity())
                    .setClientInfo(clientInfo)
                    .updateClientInfo(updateInfo)
                    .setCustomizedId(PreferenceUUID.getInstence().getUserId())
                    .build();
            startActivity(intent);
        } else {
            // 否则，建议请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            HashMap<String, String> clientInfo = new HashMap<>();
            clientInfo.put("name", username);
            clientInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            clientInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            clientInfo.put("身份", "用户");
            HashMap<String, String> updateInfo = new HashMap<>();
            updateInfo.put("name", username);
            updateInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            updateInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            updateInfo.put("身份", "用户");
            Intent intent = new MQIntentBuilder(getActivity())
                    .setClientInfo(clientInfo)
                    .updateClientInfo(updateInfo)
                    .setCustomizedId(PreferenceUUID.getInstence().getUserId())
                    .build();
            startActivity(intent);
        }
    }

    @Override
    public void updateUserInfo(LoginResponseEntity entity) {
        String username = entity.getUsername();
        if (TextUtils.isEmpty(username)) {
            username = entity.getPhone();
        }
        mMineTvPhone.setText(username);
    }

    @Override
    public void updateUserInfoPer(UserInfoEntity userInfoEntity) {
        mPresenter.loadUserInfo();
        mMineTvMoney.setText(userInfoEntity.getData().getMoney() + "元");
        mMineTvIntegral.setText(userInfoEntity.getData().getIntegral());
        username = userInfoEntity.getData().getUsername();
        if (userInfoEntity.getData().getMyitem().size() > 0) {
            mMineTvAdvantage1.setVisibility(View.VISIBLE);
            mMineTvAdvantage1.setText(userInfoEntity.getData().getMyitem().get(0).getItem());
        }
        if (userInfoEntity.getData().getMyitem().size() > 1) {
            mMineTvAdvantage2.setVisibility(View.VISIBLE);
            mMineTvAdvantage2.setText(userInfoEntity.getData().getMyitem().get(1).getItem());
        }
        if (userInfoEntity.getData().getMyitem().size() > 2) {
            mMineTvAdvantage3.setVisibility(View.VISIBLE);
            mMineTvAdvantage3.setText(userInfoEntity.getData().getMyitem().get(2).getItem());
        }
    }

    @Override
    public void updategetDaySign(DaySignEntity daySignEntity) {
        mDaySignEntity = daySignEntity;
    }

    @Override
    public void updateaddDaySign(AddSignEntity responseData) {
        if (responseData.getCode().equals("200")) {
            showToast(responseData.getMsg());
            SignDialog signDialog = new SignDialog(getActivity(), responseData.getData());
            signDialog.show();
            Window window = signDialog.getWindow();
            window.setGravity(Gravity.CENTER);
        } else {
            showToast(responseData.getMsg());
        }
    }

    @Override
    public void updategetDelUser(ResponseData responseData) {

    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void updategetAddInteg(SignInfoEntity responseData) {
        if (responseData != null) {
            if (responseData.getCode().equals("200")) {
                mPresenter.getaddMd("18");
            }
            Bundle bundle = new Bundle();
            bundle.putString("code", responseData.getCode());
            bundle.putSerializable("SignInfoEntity", responseData.getData());
            IntentUtils.getInstence().intent(getActivity(), IntegralActivity.class, bundle);
        }
    }

    @Override
    public void updategetCheck(MCheckVersionEntity mCheckVersionEntity) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            mPresenter.loadUserInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.userInfo(PreferenceUUID.getInstence().getUserId());
        }
        MobclickAgent.onPageStart("我的页面");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的页面");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MQManager.getInstance(getActivity()).closeMeiqiaService();
    }
}
