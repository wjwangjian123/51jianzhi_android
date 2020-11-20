package com.part.jianzhiyi.mvp.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.constants.IntentConstant;
import com.part.jianzhiyi.customview.CircularProgressView;
import com.part.jianzhiyi.dialog.SignDialog;
import com.part.jianzhiyi.model.entity.AddSignEntity;
import com.part.jianzhiyi.model.entity.DaySignEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.user.MineContract;
import com.part.jianzhiyi.mvp.presenter.mine.MinePresenter;
import com.part.jianzhiyi.mvp.ui.activity.BusinessActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineAboutActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineDeliveryActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineFavouriteActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineFeekbackActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineSettingActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineUpdateProfileActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineUpdateResumeActivity;
import com.part.jianzhiyi.mvp.ui.activity.MyWalletActivity;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.umeng.analytics.MobclickAgent;

import androidx.annotation.Nullable;

/**
 * Created by jyx on 2020/7/14
 *
 * @author jyx
 * @describe
 */
@Route(path = "/app/fragment/mine")
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.IMineView, View.OnClickListener {

    private LinearLayout mLlContent;
    private TextView mMineTvPhone;
    private ImageView mMineIvEdit;
    private TextView mMineTvAdvantage1;
    private TextView mMineTvAdvantage2;
    private TextView mMineTvAdvantage3;
    private CircularProgressView mMineProgressCircular;
    private TextView mMineTvActive;
    private TextView mMineTvSign;
    private LinearLayout mMineLinearSee;
    private LinearLayout mTvJoined;
    private LinearLayout mTvApproved;
    private LinearLayout mTvDoned;
    private RelativeLayout mRlInfo;
    private RelativeLayout mRlFavourite;
    private RelativeLayout mRlBusiness;
    private RelativeLayout mRlFeekback;
    private RelativeLayout mRlAbout;
    private RelativeLayout mRlSet;
    private TextView mMineTvMoney;
    private TextView mMineTvTixian;
    private DaySignEntity mDaySignEntity;

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
        mLlContent = view.findViewById(R.id.ll_content);
        mMineTvPhone = view.findViewById(R.id.mine_tv_phone);
        mMineIvEdit = view.findViewById(R.id.mine_iv_edit);
        mMineTvAdvantage1 = view.findViewById(R.id.mine_tv_advantage1);
        mMineTvAdvantage2 = view.findViewById(R.id.mine_tv_advantage2);
        mMineTvAdvantage3 = view.findViewById(R.id.mine_tv_advantage3);
        mMineProgressCircular = view.findViewById(R.id.mine_progress_circular);
        mMineTvActive = view.findViewById(R.id.mine_tv_active);
        mMineTvSign = view.findViewById(R.id.mine_tv_sign);
        mMineLinearSee = view.findViewById(R.id.mine_linear_see);
        mTvJoined = view.findViewById(R.id.tv_joined);
        mTvApproved = view.findViewById(R.id.tv_approved);
        mTvDoned = view.findViewById(R.id.tv_doned);
        mRlInfo = view.findViewById(R.id.rl_info);
        mRlFavourite = view.findViewById(R.id.rl_favourite);
        mRlBusiness = view.findViewById(R.id.rl_business);
        mRlFeekback = view.findViewById(R.id.rl_feekback);
        mRlAbout = view.findViewById(R.id.rl_about);
        mRlSet = view.findViewById(R.id.rl_set);
        mMineTvMoney = view.findViewById(R.id.mine_tv_money);
        mMineTvTixian = view.findViewById(R.id.mine_tv_tixian);
        setToolbarVisible(false);
        mPresenter.userInfo(PreferenceUUID.getInstence().getUserId());
        if (mPresenter.isUserLogin()) {
            mMineTvPhone.setText(PreferenceUUID.getInstence().getUserPhone());
        } else {
            mMineTvPhone.setText("点击登录");
        }
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
        mMineLinearSee.setOnClickListener(this);
        mTvJoined.setOnClickListener(this);
        mTvApproved.setOnClickListener(this);
        mTvDoned.setOnClickListener(this);

        mRlInfo.setOnClickListener(this);
        mRlFavourite.setOnClickListener(this);
        mRlBusiness.setOnClickListener(this);
        mRlFeekback.setOnClickListener(this);
        mRlAbout.setOnClickListener(this);
        mRlSet.setOnClickListener(this);
        mMineTvTixian.setOnClickListener(this);
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
                //签到
                mPresenter.addDaySign(PreferenceUUID.getInstence().getUserId(), String.valueOf(mDaySignEntity.getData().getDay() + 1));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_about) {
            Intent intent = new Intent(mActivity, MineAboutActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
            return;
        }
        if (!mPresenter.isUserLogin()) {
            mActivity.reStartLogin();
            return;
        }
        if (v.getId() == R.id.rl_set) {
            Intent intent = new Intent(mActivity, MineSettingActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_feekback) {
            Intent intent = new Intent(mActivity, MineFeekbackActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
        }
        if (v.getId() == R.id.rl_business) {
            Intent intent = new Intent(getActivity(), BusinessActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_favourite) {
            Intent intent = new Intent(getActivity(), MineFavouriteActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
        }
        if (v.getId() == R.id.rl_info) {
            Intent intent = new Intent(getActivity(), MineUpdateResumeActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
        }
        if (v.getId() == R.id.mine_iv_edit) {
            Intent intent = new Intent(mActivity, MineUpdateProfileActivity.class);
            startActivityForResult(intent, IntentConstant.REQEUST_CODE);
        }
        if (v.getId() == R.id.mine_linear_see) {
            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
            intent.putExtra("positionType", 1);
            startActivity(intent);
        }
        if (v.getId() == R.id.tv_joined) {
            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
            intent.putExtra("positionType", 2);
            startActivity(intent);
        }
        if (v.getId() == R.id.tv_approved) {
            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
            intent.putExtra("positionType", 3);
            startActivity(intent);
        }
        if (v.getId() == R.id.tv_doned) {
            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
            intent.putExtra("positionType", 4);
            startActivity(intent);
        }
        if (v.getId() == R.id.mine_tv_tixian) {
            Intent intent = new Intent(getActivity(), MyWalletActivity.class);
            intent.putExtra("type", 0);
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
        int active = Integer.parseInt(userInfoEntity.getData().getResume_active());
        mMineTvActive.setText(userInfoEntity.getData().getResume_active());
        mMineProgressCircular.setProgress(active);
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
            int active = Integer.parseInt(responseData.getData().getResume());
            mMineTvActive.setText(responseData.getData().getResume() + "");
            mMineProgressCircular.setProgress(active);
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
}
