package com.part.jianzhiyi.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.KuaibaoEntity;
import com.part.jianzhiyi.model.entity.moku.TxBindingEntity;
import com.part.jianzhiyi.model.entity.moku.TxInfoEntity;
import com.part.jianzhiyi.model.entity.moku.TxTypeEntity;
import com.part.jianzhiyi.model.entity.pay.AuthInfoEntity;
import com.part.jianzhiyi.mvp.contract.moku.TxContract;
import com.part.jianzhiyi.mvp.presenter.moku.TxPresenter;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.KeyBoardHelperUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class ZfbWithdrawalActivity extends BaseActivity<TxPresenter> implements TxContract.ITxView, View.OnClickListener {

    private ImageView mZfbIvReturn;
    private TextView mZfbTvTitle;
    private TagFlowLayout mZfbTfl;
    private LinearLayout mZfbLinearJine;
    private TextView mZfbTvNickname;
    private TextView mZfbTvNickShow;
    private View mZfbViewNickname;
    private TextView mZfbTvCode;
    private InputFilteEditText mZfbEtCode;
    private TextView mZfbTvSend;
    private View mZfbViewCode;
    private RelativeLayout mZfbRlInfo;
    private TextView mZfbTvYue;
    private TextView mZfbTvMoney;
    private TextView mZfbTvTixian;
    private ScrollView mZfbScroll;
    private RelativeLayout mLayoutBottom;
    private LinearLayout mZfbLayoutContent;
    private List<TxInfoEntity.MoneyBean> mlist;
    private String money;
    private String code;
    private long sendSmsCodeTime = 60000;
    private CountDownTimer timer;
    private KeyBoardHelperUtil boardHelper;
    private int bottomHeight = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getTxInfo();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_zfb_withdrawal;
    }

    @Override
    protected void initView() {
        mZfbIvReturn = (ImageView) findViewById(R.id.zfb_iv_return);
        mZfbTvTitle = (TextView) findViewById(R.id.zfb_tv_title);
        mZfbTfl = (TagFlowLayout) findViewById(R.id.zfb_tfl);
        mZfbLinearJine = (LinearLayout) findViewById(R.id.zfb_linear_jine);
        mZfbTvNickname = (TextView) findViewById(R.id.zfb_tv_nickname);
        mZfbTvNickShow = (TextView) findViewById(R.id.zfb_tv_nickShow);
        mZfbViewNickname = (View) findViewById(R.id.zfb_view_nickname);
        mZfbTvCode = (TextView) findViewById(R.id.zfb_tv_code);
        mZfbEtCode = (InputFilteEditText) findViewById(R.id.zfb_et_code);
        mZfbTvSend = (TextView) findViewById(R.id.zfb_tv_send);
        mZfbViewCode = (View) findViewById(R.id.zfb_view_code);
        mZfbRlInfo = (RelativeLayout) findViewById(R.id.zfb_rl_info);
        mZfbTvYue = (TextView) findViewById(R.id.zfb_tv_yue);
        mZfbTvMoney = (TextView) findViewById(R.id.zfb_tv_money);
        mZfbTvTixian = (TextView) findViewById(R.id.zfb_tv_tixian);
        mZfbScroll = (ScrollView) findViewById(R.id.zfb_scroll);
        mLayoutBottom = (RelativeLayout) findViewById(R.id.layout_bottom);
        mZfbLayoutContent = (LinearLayout) findViewById(R.id.zfb_layout_content);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.zfb_rl_title));
        mlist = new ArrayList<>();
    }

    @Override
    protected void initData() {
        //为 Activity 指定 windowSoftInputMode
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        boardHelper = new KeyBoardHelperUtil(ZfbWithdrawalActivity.this);
        boardHelper.onCreate();
        boardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);
        mLayoutBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = mLayoutBottom.getHeight();
            }
        });
    }

    private KeyBoardHelperUtil.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardHelperUtil.OnKeyBoardStatusChangeListener() {
        @Override
        public void OnKeyBoardPop(int keyBoardheight) {
            if (bottomHeight > keyBoardheight) { //底部空白区域高度大于软键盘高度，没遮住
                mLayoutBottom.setVisibility(View.GONE);
            } else {
                int offset = bottomHeight - keyBoardheight;
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mZfbScroll.getLayoutParams();
                lp.topMargin = offset;
                mLayoutBottom.setLayoutParams(lp);
            }
        }

        @Override
        public void OnKeyBoardClose(int oldKeyBoardheight) {
            if (View.VISIBLE != mLayoutBottom.getVisibility()) {
                mLayoutBottom.setVisibility(View.VISIBLE);
            }
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mZfbScroll.getLayoutParams();
            if (lp.topMargin != 0) {
                lp.topMargin = 0;
                mLayoutBottom.setLayoutParams(lp);
            }
        }
    };

    @Override
    protected void setListener() {
        super.setListener();
        mZfbIvReturn.setOnClickListener(this);
        mZfbTvSend.setOnClickListener(this);
        mZfbTvTixian.setOnClickListener(this);
        mZfbTfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                money = mlist.get(position).getMoney();
                return true;
            }
        });
    }

    @Override
    protected TxPresenter createPresenter() {
        return new TxPresenter(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.zfb_iv_return) {
            finish();
        } else if (v.getId() == R.id.zfb_tv_send) {
            if (timer != null) {
                return;
            }
            String userId = PreferenceUUID.getInstence().getUserId();
            long currentTimeMillis = System.currentTimeMillis();
            String content = code + "51jianzhi" + currentTimeMillis;
            mPresenter.getTxcode(userId, String.valueOf(currentTimeMillis), Tools.md5(content));
        } else if (v.getId() == R.id.zfb_tv_tixian) {
            if (TextUtils.isEmpty(mZfbEtCode.getText().toString().trim())) {
                showToast("请输入正确的验证码");
                return;
            }
            if (TextUtils.isEmpty(money)) {
                showToast("请选择提现金额");
                return;
            }
            mPresenter.getTxapp(mZfbEtCode.getText().toString().trim(), money);
        }
    }

    @Override
    public void updategetTxtype(TxTypeEntity txTypeEntity) {

    }

    @Override
    public void updategetTxbinding(TxBindingEntity txBindingEntity) {

    }

    @Override
    public void updategetTxInfo(TxInfoEntity txInfoEntity) {
        this.mlist.clear();
        if (txInfoEntity != null) {
            mZfbTvMoney.setText(txInfoEntity.getData().getMoney());
            mZfbTvNickShow.setText(txInfoEntity.getData().getAli_nick());
            code = txInfoEntity.getData().getPhone();
            if (txInfoEntity.getData().getPhone().equals(null) || txInfoEntity.getData().getPhone().equals("")) {
                mZfbTvCode.setText("验证码");
            } else {
                mZfbTvCode.setText("验证码（尾号" + txInfoEntity.getData().getPhone() + "）");
            }
            if (txInfoEntity.getMoney().size() > 0) {
                mlist.addAll(txInfoEntity.getMoney());
                mZfbTfl.setAdapter(new TagAdapter<TxInfoEntity.MoneyBean>(mlist) {
                    @Override
                    public View getView(FlowLayout parent, int position, TxInfoEntity.MoneyBean databean) {
                        TextView tv = (TextView) LayoutInflater.from(ZfbWithdrawalActivity.this).inflate(R.layout.item_zfb_flow, mZfbTfl, false);
                        tv.setText(databean.getMoney_s());
                        return tv;
                    }
                });
                mZfbTfl.getAdapter().setSelectedList(0);
                money = mlist.get(0).getMoney();
            }
        }
    }

    @Override
    public void updategetTxcode(TxBindingEntity txBindingEntity) {
        if (txBindingEntity != null) {
            showToast(txBindingEntity.getMsg());
            if (txBindingEntity.getCode().equals("200")) {
                startTimer();
            }
        }
    }

    @Override
    public void updategetTxapp(TxBindingEntity txBindingEntity) {
        if (txBindingEntity != null) {
            showToast(txBindingEntity.getMsg());
            if (txBindingEntity.getCode().equals("200")) {
                initDialogTip();
            }
        }
    }

    private void initDialogTip() {
        //提现提示框
        AlertDialog.Builder builder = new AlertDialog.Builder(ZfbWithdrawalActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(ZfbWithdrawalActivity.this).inflate(R.layout.dialog_tixian_tip, null, false);
        TextView know = inflate.findViewById(R.id.tv_know);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setView(inflate);
        //显示
        alertDialog.show();
        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //提现记录
                Intent intent = new Intent(ZfbWithdrawalActivity.this, MyWalletActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void updategetAuthInfo(AuthInfoEntity authInfoEntity) {

    }

    @Override
    public void updategetTxkb(KuaibaoEntity responseData) {

    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    private void startTimer() {
        timer = new CountDownTimer(sendSmsCodeTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mZfbTvSend.setText((millisUntilFinished / 1000) + " 秒后重试");
                mZfbTvSend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                if (timer != null) {
                    mZfbTvSend.setText("发送验证码");
                    mZfbTvSend.setEnabled(true);
                    timer.cancel();
                    timer = null;
                }
            }
        };
        timer.start();
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
