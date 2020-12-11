package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthResult;
import com.part.jianzhiyi.modulemerchants.model.entity.MMinMoneyEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MPayResult;
import com.part.jianzhiyi.modulemerchants.model.entity.MZfbPayEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MerWalletContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MerWalletPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

public class MerPayActivity extends BaseActivity<MerWalletPresenter> implements MerWalletContract.IMerWalletView, View.OnClickListener {

    private InputFilteEditText mEtMoney;
    private TextView mTvPay;
    private TextView mTvMsg;
    private String Minmoney;
    private String msg;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getmdAdd("80");
        mPresenter.getMinMoney();
        MobclickAgent.onEvent(MerPayActivity.this, "mer_pay_in");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_pay;
    }

    @Override
    protected void initView() {
        mEtMoney = (InputFilteEditText) findViewById(R.id.et_money);
        mTvPay = (TextView) findViewById(R.id.tv_pay);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        initToolbar("充值页面");
    }

    private long clickTime = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mTvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MerPayActivity.this, "mer_pay_btn");
                mPresenter.getmdAdd("81");
                if (TextUtils.isEmpty(mEtMoney.getText().toString().trim())) {
                    showToast("请输入充值金额");
                    return;
                }
                if (mEtMoney.getText().toString().trim().compareTo(Minmoney) < 0) {
                    showToast(msg);
                    return;
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    mPresenter.getOrder(mEtMoney.getText().toString().trim());
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected void initData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerPayActivity.this, R.style.dialogNoBg);
        alertDialogSuccess = builder.create();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MerPayActivity.this, R.style.dialogNoBg);
        alertDialogFail = builder1.create();
        if (alertDialogSuccess.isShowing()) {
            alertDialogSuccess.dismiss();
        }
        if (alertDialogFail.isShowing()) {
            alertDialogFail.dismiss();
        }
    }

    @Override
    protected MerWalletPresenter createPresenter() {
        return new MerWalletPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void updategetMinMoney(MMinMoneyEntity mMinMoneyEntity) {
        if (mMinMoneyEntity != null) {
            if (mMinMoneyEntity.getCode().equals("200")) {
                Minmoney = mMinMoneyEntity.getData().getMin_money();
                mEtMoney.setHint(mMinMoneyEntity.getData().getPay_msg());
                msg = mMinMoneyEntity.getData().getPay_msg();
                if (mMinMoneyEntity.getData().getIs_security() == 0) {
                    mTvMsg.setVisibility(View.VISIBLE);
                    mTvMsg.setText(mMinMoneyEntity.getData().getMsg());
                } else if (mMinMoneyEntity.getData().getIs_security() == 1) {
                    mTvMsg.setVisibility(View.INVISIBLE);
                }
            } else {
                showToast(mMinMoneyEntity.getMsg());
            }
        }
    }

    @Override
    public void updategetMyMoney(MMyWalletEntity mMyWalletEntity) {

    }

    private String mResponseData;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private AlertDialog alertDialogSuccess;
    private AlertDialog alertDialogFail;
    private Handler mHandler1 = new Handler();

    @Override
    public void updategetOrder(MZfbPayEntity mZfbPayEntity) {
        if (mZfbPayEntity != null) {
            if (mZfbPayEntity.getCode().equals("200")) {
                //拉起支付宝
                mResponseData = mZfbPayEntity.getData();
                pay(mResponseData);
            } else {
                showToast(mZfbPayEntity.getMsg());
            }
        }
    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    /**
     * 支付宝支付业务示例
     */
    protected void pay(final String orderInfo) {
        if (TextUtils.isEmpty(mResponseData)) {
            return;
        }
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MerPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    MPayResult payResult = new MPayResult((Map<String, String>) msg.obj);
                    /**
                     *对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        mPresenter.getmdAdd("82");
                        MobclickAgent.onEvent(MerPayActivity.this, "mer_pay_success");
                        initDialogSuccess();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        MobclickAgent.onEvent(MerPayActivity.this, "mer_pay_fail");
                        initDialogFail();
                    } else {
                        MobclickAgent.onEvent(MerPayActivity.this, "mer_pay_fail");
                        initDialogFail();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    MAuthResult authResult = new MAuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showToast(authResult.getResult());
                    } else {
                        // 其他状态值则为授权失败
                        showToast(authResult.getResult());
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void initDialogSuccess() {
        View inflate = LayoutInflater.from(MerPayActivity.this).inflate(R.layout.dialog_success, null, false);
        alertDialogSuccess.setView(inflate);
        ImageView payCancel = inflate.findViewById(R.id.dialog_iv_cancel);
        alertDialogSuccess.show();
        Window window = alertDialogSuccess.getWindow();
        window.setGravity(Gravity.CENTER);
        payCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogSuccess.dismiss();
            }
        });
        mHandler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(1000);
                MerPayActivity.this.finish();
            }
        }, 2000);
    }

    private void initDialogFail() {
        View inflate = LayoutInflater.from(MerPayActivity.this).inflate(R.layout.dialog_fail, null, false);
        alertDialogFail.setView(inflate);
        ImageView payCancel = inflate.findViewById(R.id.dialog_iv_cancel);
        alertDialogFail.show();
        Window window = alertDialogFail.getWindow();
        window.setGravity(Gravity.CENTER);
        payCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogFail.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mHandler1 != null) {
            mHandler1.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端支付页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端支付页面");
        MobclickAgent.onPause(this);
    }
}
