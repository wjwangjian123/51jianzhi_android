package com.part.jianzhiyi.mvp.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.ad.PositionId;
import com.part.jianzhiyi.ad.TTAdManagerHolder;
import com.part.jianzhiyi.adapter.JoinSuccessListAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.constants.ConstantsDimens;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.corecommon.utils.CopyTextLibrary;
import com.part.jianzhiyi.corecommon.utils.UiUtils;
import com.part.jianzhiyi.dialog.ContactBusinessDialog;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JoinJobEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.JoinSuccessContract;
import com.part.jianzhiyi.mvp.presenter.JoinSuccessPresenter;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.utils.IntentUtils;
import com.part.jianzhiyi.utils.OpenUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

public class JoinSuccessActivity extends BaseActivity<JoinSuccessPresenter> implements JoinSuccessContract.IJoinSuccessView {

    private ImageView mJoinIvReturn;
    private ImageView mJoinIvContact;
    private TextView mJoinTvContact;
    private TextView mJoinTvCopy;
    private TextView mJoinTvTip2;
    private LinearLayout mJoinLinearOne;
    private TextView mJoinTvTip3;
    private TextView mJoinTvContact2;
    private LinearLayout mJoinLinearTwo;
    private ImageView mIvType;
    private TextView mTvOptimization;
    private ConstraintLayout mRlOptimization;
    private ListViewInScrollView mLvOptimization;
    private TextView mJoinTvTip;

    private JoinSuccessListAdapter mJoinSuccessListAdapter;
    private List<SearchEntity.DataBean> mList;
    private int join_type;
    private JoinJobEntity joinJobEntity;
    private List<JoinJobEntity.DataBean> jobListBean;
    private int contact_type;
    private String contact;
    private String id;
    private String position;
    private String sortId;
    private int type = 1;
    private Handler mHandler;
    //广告
    private TTAdNative mTTAdNative;
    private FrameLayout mExpressContainer;
    private TTNativeExpressAd mTTAd;
    private long startTime = 0;
    private boolean mHasShowDownloadActive = false;
    private ImageView mIvAdClose;
    private RelativeLayout mExpressRlContainer;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.search("", "");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_join_success;
    }

    @Override
    protected void initView() {
        mJoinIvReturn = (ImageView) findViewById(R.id.join_iv_return);
        mJoinIvContact = (ImageView) findViewById(R.id.join_iv_contact);
        mJoinTvContact = (TextView) findViewById(R.id.join_tv_contact);
        mJoinTvCopy = (TextView) findViewById(R.id.join_tv_copy);
        mJoinTvTip2 = (TextView) findViewById(R.id.join_tv_tip2);
        mJoinLinearOne = (LinearLayout) findViewById(R.id.join_linear_one);
        mJoinTvTip3 = (TextView) findViewById(R.id.join_tv_tip3);
        mJoinTvContact2 = (TextView) findViewById(R.id.join_tv_contact2);
        mJoinLinearTwo = (LinearLayout) findViewById(R.id.join_linear_two);
        mIvType = (ImageView) findViewById(R.id.iv_type);
        mTvOptimization = (TextView) findViewById(R.id.tv_Optimization);
        mRlOptimization = (ConstraintLayout) findViewById(R.id.rl_Optimization);
        mLvOptimization = (ListViewInScrollView) findViewById(R.id.lv_Optimization);
        mExpressContainer = findViewById(R.id.express_container);
        mIvAdClose = findViewById(R.id.iv_ad_close);
        mExpressRlContainer = findViewById(R.id.express_rl_container);
        mJoinTvTip = findViewById(R.id.join_tv_tip);

        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.join_rl_title));

        Intent intent = getIntent();
        join_type = intent.getIntExtra("join_type", 0);
        id = intent.getStringExtra("id");
        position = intent.getStringExtra("position");
        sortId = intent.getStringExtra("sortId");
        joinJobEntity = (JoinJobEntity) intent.getSerializableExtra("joinJobEntity");
        jobListBean = joinJobEntity.getData();
        mHandler = new Handler();

        if (join_type == 1) {
            mJoinLinearOne.setVisibility(View.VISIBLE);
            mJoinLinearTwo.setVisibility(View.GONE);
            //单个报名
            initDialog1();
        } else if (join_type == 2) {
            mJoinLinearOne.setVisibility(View.GONE);
            mJoinLinearTwo.setVisibility(View.VISIBLE);
            //多个报名
            initDialog2();
        }
        if (jobListBean.size() > 0) {
            contact = joinJobEntity.getData().get(0).getContact();
            contact_type = joinJobEntity.getData().get(0).getContact_type();
            mJoinTvTip3.setText("成功报名" + jobListBean.size() + "家门店");
            if (PreferenceUUID.getInstence().getShowWx() == 1) {
                //1是微信，2是QQ，3是公众号，4是手机号，5是网址
                if (jobListBean.get(0).getContact_type() == 1) {
                    mJoinTvTip.setText("请加微信联系商家，完成录取");
                    mJoinTvCopy.setText("复制微信号并打开微信");
                    mJoinTvTip2.setVisibility(View.VISIBLE);
                    mJoinTvTip2.setText("加微信申请提交姓名、性别、年龄");
                } else if (jobListBean.get(0).getContact_type() == 2) {
                    mJoinTvTip.setText("请加QQ联系商家，完成录取");
                    mJoinTvCopy.setText("复制QQ号并打开QQ");
                    mJoinTvTip2.setVisibility(View.VISIBLE);
                    mJoinTvTip2.setText("加QQ申请提交姓名、性别、年龄");
                } else if (jobListBean.get(0).getContact_type() == 3) {
                    mJoinTvTip.setText("请关注商家公众号联系商家，完成录取");
                    mJoinTvCopy.setText("复制公众号");
                    mJoinTvTip2.setVisibility(View.GONE);
                } else if (jobListBean.get(0).getContact_type() == 4) {
                    mJoinTvTip.setText("请联系商家，完成录取");
                    mJoinTvCopy.setText("复制手机号");
                    mJoinTvTip2.setVisibility(View.GONE);
                } else if (jobListBean.get(0).getContact_type() == 5) {
                    mJoinTvTip.setText("请打开链接联系商家，完成录取");
                    mJoinTvCopy.setText("复制网址");
                    mJoinTvTip2.setVisibility(View.GONE);
                } else {
                    mJoinTvTip2.setVisibility(View.GONE);
                    mJoinTvTip.setText("请联系商家，完成录取");
                }
                String name = "icon_detail" + jobListBean.get(0).getContact_type();
                int imageResId = UiUtils.getImageResId(this, name);
                mJoinIvContact.setImageResource(imageResId);
            } else {
                mJoinTvTip.setText("请联系商家，完成录取");
                mJoinTvCopy.setText("复制联系方式");
                mJoinTvTip2.setVisibility(View.GONE);
            }
            mJoinTvContact.setText(jobListBean.get(0).getContact());
        }
//        initTTad();
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mJoinSuccessListAdapter = new JoinSuccessListAdapter(JoinSuccessActivity.this, mList);
        mLvOptimization.setAdapter(mJoinSuccessListAdapter);
        mLvOptimization.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(JoinSuccessActivity.this, "joinsuccess_joblist");
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                bundle.putString("position", Constants.POSITION_SERACH);
                bundle.putString("sortId", "" + position);
                IntentUtils.getInstence().setResult(JoinSuccessActivity.this, bundle, 100);
            }
        });
    }

    /**
     * 穿山甲广告
     */
    private void initTTad() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
        mExpressContainer.removeAllViews();
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                //广告位id
                .setCodeId(PositionId.JOIN_SUCCESS_POS_ID)
                .setSupportDeepLink(true)
                //请求广告数量为1到3条
                .setAdCount(1)
                //期望模板广告view的size,单位dp
                .setExpressViewAcceptedSize(300, 75)
                .setImageAcceptedSize(320, 50)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                mExpressContainer.removeAllViews();
                mExpressRlContainer.setVisibility(View.GONE);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                mExpressRlContainer.setVisibility(View.VISIBLE);
                mTTAd = ads.get(0);
                mTTAd.setSlideIntervalTime(30 * 1000);
                bindAdListener(mTTAd);
                startTime = System.currentTimeMillis();
                mTTAd.render();
            }
        });
    }

    private void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
            }

            @Override
            public void onAdShow(View view, int type) {
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e("ExpressView", "render fail:" + (System.currentTimeMillis() - startTime));
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e("ExpressView", "render suc:" + (System.currentTimeMillis() - startTime));
                mExpressContainer.removeAllViews();
                mExpressContainer.addView(view);
            }
        });
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onInstalled(String fileName, String appName) {
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        mIvAdClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户选择不喜欢原因后，移除广告展示
                mExpressContainer.removeAllViews();
                mExpressRlContainer.setVisibility(View.GONE);
            }
        });
        mJoinIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    initConfirm();
                } else {
                    finish();
                }
            }
        });
        mJoinTvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getaddMd("49");
                MobclickAgent.onEvent(JoinSuccessActivity.this, "joinsuccess_one_copy");
                if (contact != null && contact != "") {
                    type = 2;
                    mPresenter.joincopyContact(id, sortId, contact, 2);
                    CopyTextLibrary copyButtonLibrary = new CopyTextLibrary(JoinSuccessActivity.this, contact);
                    copyButtonLibrary.init();
                    if (PreferenceUUID.getInstence().getShowWx() == 1) {
                        if (contact_type == 1) {
                            //微信
                            if (OpenUtils.isWeixinAvilible(JoinSuccessActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(JoinSuccessActivity.this, "微信");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openWx(JoinSuccessActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装微信");
                            }
                        } else if (contact_type == 2) {
                            //QQ
                            if (OpenUtils.isQQInstall(JoinSuccessActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(JoinSuccessActivity.this, "QQ");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openQQ(JoinSuccessActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装QQ");
                            }
                        } else {
                            showToast("复制成功");
                        }
                    } else {
                        showToast("复制成功");
                    }
                }
            }
        });
        mJoinTvContact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(JoinSuccessActivity.this, "joinsuccess_two_contact");
                initDialog2();
            }
        });
    }

    private void initConfirm() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(JoinSuccessActivity.this);
        final AlertDialog alertDialog1 = alertDialog.create();
        View view = View.inflate(JoinSuccessActivity.this, R.layout.dialog_confirm_tip, null);
        alertDialog1.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog1.setView(view);
        //显示
        alertDialog1.show();
        TextView yilianxi = view.findViewById(R.id.re_tv_yilianxi);
        TextView lianxi = view.findViewById(R.id.re_tv_lianxi);
        yilianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(JoinSuccessActivity.this, "joinsuccess_one_dialog_cancel");
                alertDialog1.dismiss();
                finish();
            }
        });
        lianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(JoinSuccessActivity.this, "joinsuccess_one_dialog_confirm");
                alertDialog1.dismiss();
                if (contact != null && contact != "") {
                    type = 2;
                    mPresenter.joincopyContact(id, sortId, contact, 6);
                    CopyTextLibrary copyButtonLibrary = new CopyTextLibrary(JoinSuccessActivity.this, contact);
                    copyButtonLibrary.init();
                    if (PreferenceUUID.getInstence().getShowWx() == 1) {
                        if (contact_type == 1) {
                            //微信
                            if (OpenUtils.isWeixinAvilible(JoinSuccessActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(JoinSuccessActivity.this, "微信");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openWx(JoinSuccessActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装微信");
                            }
                        } else if (contact_type == 2) {
                            //QQ
                            if (OpenUtils.isQQInstall(JoinSuccessActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(JoinSuccessActivity.this, "QQ");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openQQ(JoinSuccessActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装QQ");
                            }
                        } else {
                            showToast("复制成功");
                        }
                    } else {
                        showToast("复制成功");
                    }
                }
//                finish();
            }
        });
    }

    private CountDownTimer timer;
    private String count;

    private void initDialog1() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(JoinSuccessActivity.this);
        final AlertDialog alertDialog1 = alertDialog.create();
        View view = View.inflate(JoinSuccessActivity.this, R.layout.dialog_join_success, null);
        alertDialog1.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog1.setView(view);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.setCancelable(false);
        //显示
        alertDialog1.show();
        TextView tip = view.findViewById(R.id.dialog_tv_tip);
        TextView copy = view.findViewById(R.id.dialog_tv_copy);
        ImageView cancel = view.findViewById(R.id.dialog_iv_cancel);
        TextView tv_cancel = view.findViewById(R.id.dialog_tv_cancel);
        if (joinJobEntity.getData().size() > 0) {
            contact = joinJobEntity.getData().get(0).getContact();
            contact_type = joinJobEntity.getData().get(0).getContact_type();
            if (PreferenceUUID.getInstence().getShowWx() == 1) {
                if (contact_type == 1) {
                    copy.setText("复制微信号:" + contact);
                    tip.setText("添加商家微信，马上开启赚钱之旅！");
                } else if (contact_type == 2) {
                    copy.setText("复制QQ号:" + contact);
                    tip.setText("添加商家QQ，马上开启赚钱之旅！");
                } else if (contact_type == 3) {
                    copy.setText("复制公众号:" + contact);
                    tip.setText("关注商家公众号，马上开启赚钱之旅！");
                } else if (contact_type == 4) {
                    copy.setText("复制手机号:" + contact);
                    tip.setText("联系商家，马上开启赚钱之旅！");
                } else if (contact_type == 5) {
                    copy.setText("复制网址:" + contact);
                    tip.setText("联系商家，马上开启赚钱之旅！");
                }
            } else {
                tip.setText("联系商家，马上开启赚钱之旅！");
                copy.setText("复制联系方式:" + contact);
            }
        }
        tv_cancel.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count = millisUntilFinished / 1000 + "";
                if (!count.equals("0")) {
                    tv_cancel.setText(count);
                }
            }

            @Override
            public void onFinish() {
                if (timer != null) {
                    tv_cancel.setVisibility(View.GONE);
                    cancel.setVisibility(View.VISIBLE);
                    timer.cancel();
                    timer = null;
                }
            }
        };
        timer.start();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getaddMd("48");
                MobclickAgent.onEvent(JoinSuccessActivity.this, "joinsuccess_one_dialog_copy");
                alertDialog1.dismiss();
                if (joinJobEntity.getData().get(0) == null) {
                    return;
                }
                if (contact != null && contact != "") {
                    type = 2;
                    mPresenter.joincopyContact(id, sortId, contact, 1);
                    CopyTextLibrary copyButtonLibrary = new CopyTextLibrary(JoinSuccessActivity.this, contact);
                    copyButtonLibrary.init();
                    if (PreferenceUUID.getInstence().getShowWx() == 1) {
                        if (contact_type == 1) {
                            //微信
                            if (OpenUtils.isWeixinAvilible(JoinSuccessActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(JoinSuccessActivity.this, "微信");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openWx(JoinSuccessActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装微信");
                            }
                        } else if (contact_type == 2) {
                            //QQ
                            if (OpenUtils.isQQInstall(JoinSuccessActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(JoinSuccessActivity.this, "QQ");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openQQ(JoinSuccessActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装QQ");
                            }
                        } else {
                            showToast("复制成功");
                        }
                    } else {
                        showToast("复制成功");
                    }
                }
            }
        });
    }

    private void initDialog2() {
        Dialog dialog = new ContactBusinessDialog(JoinSuccessActivity.this, jobListBean, new ContactBusinessDialog.OnJoinedClickListener() {
            @Override
            public void onJoinedClick(String jobid, int position) {
                mPresenter.getaddMd("48");
                MobclickAgent.onEvent(JoinSuccessActivity.this, "joinsuccess_two_dialog_copy");
                if (contact != null && contact != "") {
                    type = 2;
                    mPresenter.joincopyContact(jobid, position + "", jobListBean.get(position).getContact(), 1);
                    CopyTextLibrary copyButtonLibrary = new CopyTextLibrary(JoinSuccessActivity.this, jobListBean.get(position).getContact());
                    copyButtonLibrary.init();
                    if (PreferenceUUID.getInstence().getShowWx() == 1) {
                        if (contact_type == 1) {
                            //微信
                            if (OpenUtils.isWeixinAvilible(JoinSuccessActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(JoinSuccessActivity.this, "微信");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openWx(JoinSuccessActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装微信");
                            }
                        } else if (contact_type == 2) {
                            //QQ
                            if (OpenUtils.isQQInstall(JoinSuccessActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(JoinSuccessActivity.this, "QQ");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openQQ(JoinSuccessActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装QQ");
                            }
                        } else {
                            showToast("复制成功");
                        }
                    } else {
                        showToast("复制成功");
                    }
                }
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = ConstantsDimens.SCREEN_WIDTH;
        dialog.getWindow().setAttributes(p);
    }

    @Override
    protected JoinSuccessPresenter createPresenter() {
        return new JoinSuccessPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updatesearch(SearchEntity searchEntity) {
        if (mList != null) {
            mList.clear();
        }
        if (searchEntity != null) {
            mList.addAll(searchEntity.getData());
            mJoinSuccessListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (type == 1) {
                initConfirm();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("报名成功后的页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("报名成功后的页面");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mTTAd != null) {
            mTTAd.destroy();
        }
    }
}
