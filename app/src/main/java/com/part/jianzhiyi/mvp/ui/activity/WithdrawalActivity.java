package com.part.jianzhiyi.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.model.entity.moku.KuaibaoEntity;
import com.part.jianzhiyi.model.entity.moku.TxBindingEntity;
import com.part.jianzhiyi.model.entity.moku.TxInfoEntity;
import com.part.jianzhiyi.model.entity.moku.TxTypeEntity;
import com.part.jianzhiyi.model.entity.pay.AuthInfoEntity;
import com.part.jianzhiyi.model.entity.pay.AuthResult;
import com.part.jianzhiyi.mogu.adapter.WithdrawalAdapter;
import com.part.jianzhiyi.mogu.view.MyRecyclerView;
import com.part.jianzhiyi.mvp.contract.moku.TxContract;
import com.part.jianzhiyi.mvp.presenter.moku.TxPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WithdrawalActivity extends BaseActivity<TxPresenter> implements TxContract.ITxView, View.OnClickListener {

    private ImageView mTixianIvReturn;
    private TextView mTixianTvMoney;
    private LinearLayout mTixianLinearWeixin;
    private LinearLayout mTixianLinearZhifubao;
    private LinearLayout mTixianRlMethod;
    private MyRecyclerView mTixianRecycleKb;

    private WithdrawalAdapter adapter;
    private List<KuaibaoEntity.DataBean> mlist = new ArrayList<>();
    private Handler mHandlerKb = new Handler();
    private int ali_switch = 0;
    private int wechat_switch = 0;
    private int ali = 0;
    private int wechat = 0;
    private String ali_msg;
    private String wechat_msg;
    private int tx_check = 0;
    private String tx_msg;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getTxtype();
        mPresenter.getTxkb();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void initView() {
        mTixianIvReturn = (ImageView) findViewById(R.id.tixian_iv_return);
        mTixianTvMoney = (TextView) findViewById(R.id.tixian_tv_money);
        mTixianLinearWeixin = (LinearLayout) findViewById(R.id.tixian_linear_weixin);
        mTixianLinearZhifubao = (LinearLayout) findViewById(R.id.tixian_linear_zhifubao);
        mTixianRlMethod = (LinearLayout) findViewById(R.id.tixian_rl_method);
        mTixianRecycleKb = (MyRecyclerView) findViewById(R.id.tixian_recycle_kb);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.tixian_rl_title));
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        }
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WithdrawalActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mTixianRecycleKb.setLayoutManager(linearLayoutManager);
        adapter = new WithdrawalAdapter(WithdrawalActivity.this);
        mTixianRecycleKb.setAdapter(adapter);
    }

    @Override
    protected TxPresenter createPresenter() {
        return new TxPresenter(this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mTixianIvReturn.setOnClickListener(this);
        mTixianLinearWeixin.setOnClickListener(this);
        mTixianLinearZhifubao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tixian_iv_return) {
            finish();
        } else if (v.getId() == R.id.tixian_linear_weixin) {
            if (wechat_switch == 1) {
                //??????
            } else if (wechat_switch == 0) {
                showToast(wechat_msg);
            }
        } else if (v.getId() == R.id.tixian_linear_zhifubao) {
            if (ali_switch == 1) {
                if (ali == 1) {
                    if (tx_check == 1) {
                        showToast(tx_msg);
                    } else if (tx_check == 0) {
                        Intent intent = new Intent(WithdrawalActivity.this, ZfbWithdrawalActivity.class);
                        startActivity(intent);
                    }
                } else {
                    //zfb??????
                    mPresenter.getAuthInfo();
                }
            } else if (ali_switch == 0) {
                showToast(ali_msg);
            }
        }
    }

    //?????????******************************************************
    private static final int SDK_AUTH_FLAG = 2;
    //????????????????????? RequestCode
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // ??????resultStatus ??????9000??????result_code
                    // ??????200?????????????????????????????????????????????????????????????????????????????????
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // ??????alipay_open_id???????????????????????????extern_token ???value
                        // ??????????????????????????????????????????
                        showToast("????????????");
                        // ??????
                        mPresenter.getTxbinding(authResult.getAuthCode());
                    } else {
                        // ?????????????????????????????????
                        showToast("????????????");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * ??????????????? SDK ??????????????????????????????????????????????????????
     * ??? targetSDK = 23 ?????????READ_PHONE_STATE ??? WRITE_EXTERNAL_STORAGE ???????????????????????????????????????
     * ????????????????????? SDK ????????? targetSdk ??? 23 ????????????????????????????????????
     */
    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_REQUEST_CODE);

        }
    }

    /**
     * ??????????????????
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                // ???????????????????????????
                if (grantResults.length == 0) {
                    showToast("???????????????????????????");
                    return;
                }

                // ???????????????????????????
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        showToast("?????????????????????");
                        return;
                    }
                }
            }
        }
    }

    /**
     * ?????????????????????????????????
     */
    public void authV2(String authInfo) {
        /*
         * authInfo ?????????????????????????????????
         */
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // ??????AuthTask ??????
                AuthTask authTask = new AuthTask(WithdrawalActivity.this);
                // ???????????????????????????????????????
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // ??????????????????
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @Override
    public void updategetTxtype(TxTypeEntity txTypeEntity) {
        if (txTypeEntity != null) {
            mTixianTvMoney.setText(txTypeEntity.getData().getMoney());
            ali = txTypeEntity.getData().getAli();
            wechat = txTypeEntity.getData().getWechat();
            ali_switch = txTypeEntity.getData().getAli_switch();
            wechat_switch = txTypeEntity.getData().getWechat_switch();
            ali_msg = txTypeEntity.getData().getAli_msg();
            wechat_msg = txTypeEntity.getData().getWechat_msg();
            tx_check = txTypeEntity.getData().getTx_check();
            tx_msg = txTypeEntity.getData().getTx_msg();
            if (txTypeEntity.getData().getAli_switch() == 1) {
                mTixianLinearZhifubao.setSelected(true);
            } else if (txTypeEntity.getData().getAli_switch() == 0) {
                mTixianLinearZhifubao.setSelected(false);
            }
            if (txTypeEntity.getData().getWechat_switch() == 1) {
                mTixianLinearWeixin.setSelected(true);
            } else if (txTypeEntity.getData().getWechat_switch() == 0) {
                mTixianLinearWeixin.setSelected(false);
            }
        }
    }

    @Override
    public void updategetTxbinding(TxBindingEntity txBindingEntity) {
        if (txBindingEntity != null) {
            showToast(txBindingEntity.getMsg());
            if (txBindingEntity.getCode().equals("200")) {
                Intent intent = new Intent(WithdrawalActivity.this, ZfbWithdrawalActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void updategetTxInfo(TxInfoEntity txInfoEntity) {

    }

    @Override
    public void updategetTxcode(TxBindingEntity txBindingEntity) {

    }

    @Override
    public void updategetTxapp(TxBindingEntity txBindingEntity) {

    }

    @Override
    public void updategetAuthInfo(AuthInfoEntity authInfoEntity) {
        if (authInfoEntity != null) {
            if (!authInfoEntity.getInfostr().equals(null) && !authInfoEntity.getInfostr().equals("")) {
                authV2(authInfoEntity.getInfostr());
            }
        }
    }

    @Override
    public void updategetTxkb(KuaibaoEntity responseData) {
        mlist.clear();
        if (responseData.getData() != null) {
            if (responseData.getData().size() > 0) {
                mlist.addAll(responseData.getData());
                adapter.setList(mlist);
                //recycleview
                Runnable mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        //RecyclerView??????200????????????????????????
                        mTixianRecycleKb.smoothScrollBy(0, 10);
                        mHandlerKb.postDelayed(this, 200);
                    }
                };
                mHandlerKb.post(mRunnable);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mPresenter != null) {
            mPresenter.getTxtype();
        }
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mHandlerKb != null) {
            mHandlerKb.removeCallbacksAndMessages(null);
        }
    }
}
