package com.part.jianzhiyi.mvp.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.ad.PositionId;
import com.part.jianzhiyi.ad.TTAdManagerHolder;
import com.part.jianzhiyi.adapter.HomeLoveAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.constants.ConstantsDimens;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.corecommon.utils.CopyTextLibrary;
import com.part.jianzhiyi.corecommon.utils.DateUtils;
import com.part.jianzhiyi.corecommon.utils.UiUtils;
import com.part.jianzhiyi.dbmodel.GreenDaoManager;
import com.part.jianzhiyi.dialog.SignUpJoinDialog;
import com.part.jianzhiyi.model.entity.JobDetailEntity;
import com.part.jianzhiyi.model.entity.JoinJobEntity;
import com.part.jianzhiyi.model.entity.MessageResponseEntity;
import com.part.jianzhiyi.mvp.contract.VocationContract;
import com.part.jianzhiyi.mvp.presenter.VocationPresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.part.jianzhiyi.utils.IntentUtils;
import com.part.jianzhiyi.utils.OpenUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Route(path = "/app/activity/vocation")
public class VocationActivity extends BaseActivity<VocationPresenter> implements VocationContract.IVocationView {

    private TextView tvJoined;
    private ImageView mVoIvReturn;
    private TextView tvJobTitle;
    private TextView tvFavourite;
    private TextView mTvMethod;
    private TextView mTvTime;
    private TextView mTvSex;
    private TextView tvPrice;
    private TextView tvPrice2;
    private TextView tvCompany;
    private TextView tvContract;
    private ImageView ivContract;
    private TextView ivCopy;
    private RelativeLayout mRlCompany;
    private WebView webView;
    private RelativeLayout mExpressRlContainer;
    private FrameLayout mExpressContainer;
    private ImageView mIvAdClose;
    private ListViewInScrollView lvLove;
    private ImageView mImgTip;
    private View mViewMethod;
    private View mViewTime;

    private String id;
    private String position;
    private String sortId;
    private JobDetailEntity.DataBean.InfoBean entity;
    private List<JobDetailEntity.DataBean.JobListBean> mJobListBeanList;
    private HomeLoveAdapter adapter;
    private Handler mHandler;
    private List<JobDetailEntity.DataBean.LoveBean> mLoveBeanList;
    //type 1:单个报名  type 2:多个报名
    private int join_type = 1;
    private int copy_type;
    private String contract;
    private String contact_type;
    private int i = 0;
    //广告
    private TTAdNative mTTAdNative;
    private TTNativeExpressAd mTTAd;
    private long startTime = 0;
    private boolean mHasShowDownloadActive = false;

    @Override
    protected void init() {
        super.init();
        id = getIntent().getStringExtra("id");
        position = getIntent().getStringExtra("position");
        sortId = getIntent().getStringExtra("sortId");
        if (!TextUtils.isEmpty(sortId)) {
            int position = Integer.parseInt(sortId);
            position++;
            sortId = String.valueOf(position);
        }
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_vocation;
    }

    @Override
    protected void initView() {
        tvJoined = findViewById(R.id.tv_joined);
        mVoIvReturn = findViewById(R.id.vo_iv_return);
        tvJobTitle = findViewById(R.id.tv_job_title);
        tvFavourite = findViewById(R.id.tv_favourite);
        mTvMethod = findViewById(R.id.tv_method);
        mTvTime = findViewById(R.id.tv_time);
        mTvSex = findViewById(R.id.tv_sex);
        tvPrice = findViewById(R.id.tv_price1);
        tvPrice2 = findViewById(R.id.tv_price2);
        tvCompany = findViewById(R.id.tv_company);
        tvContract = findViewById(R.id.tv_contract);
        ivContract = findViewById(R.id.iv_contract);
        ivCopy = findViewById(R.id.iv_copy);
        mRlCompany = findViewById(R.id.rl_company);
        webView = findViewById(R.id.webView);
        mExpressRlContainer = findViewById(R.id.express_rl_container);
        mExpressContainer = findViewById(R.id.express_container);
        mIvAdClose = findViewById(R.id.iv_ad_close);
        lvLove = findViewById(R.id.lv_love);
        mImgTip = findViewById(R.id.img_tip);
        mViewMethod = findViewById(R.id.view_method);
        mViewTime = findViewById(R.id.view_time);

        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.vo_rl_title));

        mJobListBeanList = new ArrayList<>();
        MobclickAgent.onEvent(VocationActivity.this, "vocation_in");
        mLoveBeanList = new ArrayList<>();
        adapter = new HomeLoveAdapter(this, mLoveBeanList);
        lvLove.setAdapter(adapter);
        mHandler = new Handler();
        initTTad();
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
                .setCodeId(PositionId.DETAIL_POS_ID)
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
                mExpressRlContainer.setVisibility(View.VISIBLE);
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
    protected void initData() {
        mPresenter.jobDetailv(id, position, sortId);
    }

    @Override
    protected VocationPresenter createPresenter() {
        return new VocationPresenter(this);
    }

    @Override
    public void startIntent() {

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
        mVoIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRlCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(VocationActivity.this, "vocation_company");
            }
        });
        lvLove.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(VocationActivity.this, "vocation_guess_you");
                VocationActivity.this.id = mLoveBeanList.get(position).getId();
                VocationActivity.this.position = "7";
                sortId = "" + position;
                if (!TextUtils.isEmpty(sortId)) {
                    int position1 = Integer.parseInt(sortId);
                    position1++;
                    sortId = String.valueOf(position1);
                }
                mPresenter.jobDetailv(mLoveBeanList.get(position).getId(), "7", "" + position);
            }
        });
        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity == null) {
                    return;
                }
                if (copy_type == 1) {
                    MobclickAgent.onEvent(VocationActivity.this, "vocation_copy");
                    if (contract != null && contract != "") {
                        CopyTextLibrary copyButtonLibrary = new CopyTextLibrary(getApplicationContext(), contract);
                        copyButtonLibrary.init();
                        mPresenter.joincopyContact(id, sortId, entity.getContact(), 5);
                        if (PreferenceUUID.getInstence().getShowWx() == 1) {
                            if (contact_type.equals("1")) {
                                //微信
                                if (OpenUtils.isWeixinAvilible(VocationActivity.this)) {
                                    //弹框
                                    OpenUtils.initDialog(VocationActivity.this, "微信");
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //弹框消失
                                            //拉起微信
                                            OpenUtils.cancelDialog();
                                            OpenUtils.openWx(VocationActivity.this);
                                        }
                                    }, 1000);
                                } else {
                                    showToast("请安装微信");
                                }
                            } else if (contact_type.equals("2")) {
                                //QQ
                                if (OpenUtils.isQQInstall(VocationActivity.this)) {
                                    //弹框
                                    OpenUtils.initDialog(VocationActivity.this, "QQ");
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //弹框消失
                                            //拉起微信
                                            OpenUtils.cancelDialog();
                                            OpenUtils.openQQ(VocationActivity.this);
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
                } else if (copy_type == 2) {
                    //引导报名提示
                    MobclickAgent.onEvent(VocationActivity.this, "vocation_see_contact");
                    mImgTip.setVisibility(View.VISIBLE);
                    mHandler = new Handler();
                    if (mImgTip.getVisibility() == View.VISIBLE) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mImgTip.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }
                }
                Log.d("type:", entity.getContact_type());
            }
        });

        tvJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(VocationActivity.this, "vocation_signUp");
                if (!PreferenceUUID.getInstence().isUserLogin()) {
                    Intent intent = new Intent(VocationActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("ToLogin", 2);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return;
                }
                if (entity == null || TextUtils.equals("1", entity.getIsJoin())) {
                    return;
                }
                if (entity.isCheck_join()) {
                    if (mJobListBeanList.size() > 0 && mJobListBeanList != null) {
                        //可多个报名 显示弹框
                        Dialog dialog = new SignUpJoinDialog(VocationActivity.this, mPresenter.loadUserInfo(), mJobListBeanList, entity, new SignUpJoinDialog.OnJoinedClickListener() {
                            @Override
                            public void onJoinedClick(String jobid) {
                                mPresenter.joinJobV2(jobid, sortId, entity.getContact());
                            }
                        });
                        dialog.show();
                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.BOTTOM);
                        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                        p.width = ConstantsDimens.SCREEN_WIDTH;
                        dialog.getWindow().setAttributes(p);
                    } else {
                        //单个报名 直接调用报名接口
                        mPresenter.joinJobV2(id, sortId, entity.getContact());
                    }
                } else {
                    showToast(entity.getJoin_msg());
                    //200没问题   201性别不符合   202年龄   203简历  204地理位置
                    if (entity.getError_type() == 201) {
                        mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(VocationActivity.this, ResumeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("ToResume", 2);
                                bundle.putInt("errorType", 201);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 1000);
                            }
                        }, 1000);
                    } else if (entity.getError_type() == 202) {
                        mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(VocationActivity.this, ResumeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("ToResume", 2);
                                bundle.putInt("errorType", 202);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 1000);
                            }
                        }, 1000);
                    } else if (entity.getError_type() == 203) {
                        mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(VocationActivity.this, ResumeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("ToResume", 2);
                                bundle.putInt("errorType", 203);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 1000);
                            }
                        }, 1000);
                    }
                }
            }
        });

        tvFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(VocationActivity.this, "vocation_collect");
                if (TextUtils.equals("1", entity.getIsfavourite())) {
                    mPresenter.cancelFavourite(id, sortId);
                    return;
                }
                mPresenter.addFavourite(id, sortId);
            }
        });

        tvJobTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i == 5) {
                    showToast("jobId:" + id);
                    i = 0;
                }
            }
        });
    }

    @Override
    public void updateEntity(JobDetailEntity.DataBean.InfoBean entity) {
        this.entity = entity;
        this.id = entity.getId();
        tvJobTitle.setText(entity.getTitle());
        tvPrice.setText(entity.getPrice1());
        tvPrice2.setText(entity.getPrice2());
        tvCompany.setText(entity.getCompany());
        if (entity.getMethod().equals("")||entity.getMethod().equals(null)){
            mViewMethod.setVisibility(View.GONE);
        }else {
            mTvMethod.setText(entity.getMethod());
            mViewMethod.setVisibility(View.VISIBLE);
        }
        if (entity.getTime().equals("")||entity.getTime().equals(null)){
            mViewTime.setVisibility(View.GONE);
        }else {
            mTvTime.setText(entity.getTime());
            mViewTime.setVisibility(View.VISIBLE);
        }
        mTvSex.setText(entity.getSex());
        contract = entity.getContact();
        contact_type = entity.getContact_type();
        String name = "icon_detail" + entity.getContact_type();
        int imageResId = UiUtils.getImageResId(this, name);
        if (PreferenceUUID.getInstence().getShowWx() == 1) {
            ivContract.setVisibility(View.VISIBLE);
            ivContract.setImageResource(imageResId);
        } else {
            ivContract.setVisibility(View.GONE);
        }
        webView.loadData(entity.getContent(), "text/html; charset=UTF-8", null);
        boolean isCollect = PreferenceUUID.getInstence().isUserLogin() && TextUtils.equals("1", entity.getIsfavourite());
        tvFavourite.setSelected(isCollect);
        tvFavourite.setText(isCollect ? "已收藏" : "收藏");
        boolean isJoined = PreferenceUUID.getInstence().isUserLogin() && TextUtils.equals("1", entity.getIsJoin());
        tvJoined.setSelected(isJoined);
        tvJoined.setText(isJoined ? "已报名" : "立即报名");
        if (!isJoined) {
            mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mImgTip.setVisibility(View.VISIBLE);
                    if (mImgTip.getVisibility() == View.VISIBLE) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mImgTip.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }
                }
            }, 3000);
        }
        if (entity.getIs_copy().equals("1")) {
            ivCopy.setText("复制");
            copy_type = 1;
            tvContract.setText(entity.getContact());
        } else {
            ivCopy.setText("查看联系方式");
            copy_type = 2;
            tvContract.setText(entity.getContactXing());
        }
    }

    @Override
    public void updateRecommend(List<JobDetailEntity.DataBean.LoveBean> list) {
        if (mLoveBeanList != null) {
            this.mLoveBeanList.clear();
        }
        if (list != null) {
            this.mLoveBeanList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateErrorTip(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(VocationActivity.this);
        final AlertDialog alertDialog1 = alertDialog.create();
        View view = View.inflate(VocationActivity.this, R.layout.dialog_vocation_error_tip, null);
        TextView content = view.findViewById(R.id.tv_content);
        TextView back = view.findViewById(R.id.tv_back);
        alertDialog1.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog1.setView(view);
        //显示
        alertDialog1.show();
        content.setText(msg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
                VocationActivity.this.finish();
            }
        });
    }

    @Override
    public void updateJobList(List<JobDetailEntity.DataBean.JobListBean> job_list) {
        if (mJobListBeanList != null) {
            mJobListBeanList.clear();
        }
        if (job_list.size() > 0 && job_list != null) {
            for (int i = 0; i < job_list.size(); i++) {
                if (!job_list.get(i).isIs_join()) {
                    this.mJobListBeanList.add(job_list.get(i));
                }
            }
        }
    }

//    @Override
//    public void advertising(VocationEntity.AdvertisingBean advertisingBean) {
//
//        Log.i("tag","image:"+advertisingBean.getImage());
//    }

    @Override
    public void favouriteSuccess() {
        showToast("收藏成功");
        entity.setIsfavourite("1");
        updateEntity(entity);
    }

    @Override
    public void cancelFavoriteSuccess() {
        showToast("取消收藏");
        entity.setIsfavourite("0");
        updateEntity(entity);
    }

    @Override
    public void joinSuccess(JoinJobEntity joinJobEntity) {
        showToast("报名成功");
        entity.setIsJoin("1");
        entity.setIs_copy("1");
        updateEntity(entity);
        String date = DateUtils.getDate();
        String dateMonth = DateUtils.getDateMonth();
        String contact_type = null;
        for (int i = 0; i < joinJobEntity.getData().size(); i++) {
            MessageResponseEntity messageResponseEntity = new MessageResponseEntity();
            if (PreferenceUUID.getInstence().getShowWx() == 1) {
                if (joinJobEntity.getData().get(i).getContact_type() == 1) {
                    contact_type = "微信号";
                } else if (joinJobEntity.getData().get(i).getContact_type() == 2) {
                    contact_type = "QQ号";
                } else if (joinJobEntity.getData().get(i).getContact_type() == 3) {
                    contact_type = "公众号";
                } else if (joinJobEntity.getData().get(i).getContact_type() == 4) {
                    contact_type = "手机号";
                } else if (joinJobEntity.getData().get(i).getContact_type() == 5) {
                    contact_type = "网址";
                }
            } else {
                contact_type = "联系方式";
            }
            if (joinJobEntity.getData().get(i).getImg() == null || joinJobEntity.getData().get(i).getImg() == "") {
                messageResponseEntity.setHeadimg("https://jxw-img.oss-cn-beijing.aliyuncs.com/tx.png");
            } else {
                messageResponseEntity.setHeadimg(joinJobEntity.getData().get(i).getImg());
            }
            messageResponseEntity.setHeadimg1(R.drawable.icon_pic1);
            messageResponseEntity.setCompany(joinJobEntity.getData().get(i).getCompany());
            messageResponseEntity.setTitle(joinJobEntity.getData().get(i).getTitle());
            messageResponseEntity.setPrice(joinJobEntity.getData().get(i).getPrice());
            messageResponseEntity.setTime(date);
            messageResponseEntity.setDateMonth(dateMonth);
            messageResponseEntity.setIsRed(2);
            messageResponseEntity.setMsg1(joinJobEntity.getData().get(i).getMsg1());
            messageResponseEntity.setMsg2(joinJobEntity.getData().get(i).getMsg2());
            messageResponseEntity.setCompanyId(joinJobEntity.getData().get(i).getId());
            messageResponseEntity.setHeadimg2(R.drawable.icon_default_headpic);
            messageResponseEntity.setContact(joinJobEntity.getData().get(i).getContact());
            messageResponseEntity.setContactType(joinJobEntity.getData().get(i).getContact_type());
            messageResponseEntity.setMsg3("我的" + contact_type + ":" + joinJobEntity.getData().get(i).getContact() + ",添加时请备注找工作哦");
            GreenDaoManager.getInstance().getDaoSession().getMessageResponseEntityDao().insertOrReplace(messageResponseEntity);
        }
        if (joinJobEntity.getData().size() == 3) {
            MobclickAgent.onEvent(VocationActivity.this, "joinsuccess_three_position");
            mPresenter.getSussOrErrLog("4", "1");
        } else if (joinJobEntity.getData().size() == 2) {
            MobclickAgent.onEvent(VocationActivity.this, "joinsuccess_two_position");
            mPresenter.getSussOrErrLog("3", "1");
        } else if (joinJobEntity.getData().size() == 1) {
            mPresenter.getSussOrErrLog("2", "1");
        }
        if (joinJobEntity.getData().size() > 1) {
            MobclickAgent.onEvent(VocationActivity.this, "join_success_two");
            join_type = 2;
        } else {
            MobclickAgent.onEvent(VocationActivity.this, "join_success");
            join_type = 1;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("join_type", join_type);
        bundle.putString("id", id);
        bundle.putString("position", position);
        bundle.putString("sortId", sortId);
        bundle.putSerializable("joinJobEntity", joinJobEntity);
        IntentUtils.getInstence().startActivityForResult(VocationActivity.this, JoinSuccessActivity.class, 1002, bundle);
    }

    public static boolean isInteger(String str) {
        String regx = "^[-\\+]?[\\d]*$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("兼职详情");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("兼职详情");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        id = getIntent().getStringExtra("id");
        position = getIntent().getStringExtra("position");
        sortId = getIntent().getStringExtra("sortId");
        if (!TextUtils.isEmpty(sortId)) {
            int position = Integer.parseInt(sortId);
            position++;
            sortId = String.valueOf(position);
        }
//        mPresenter.jobDetail(id, position, sortId);
        mPresenter.jobDetailv(id, position, sortId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null) {
            id = intent.getStringExtra("id");
            position = intent.getStringExtra("position");
            sortId = intent.getStringExtra("sortId");
            if (!TextUtils.isEmpty(sortId)) {
                int position = Integer.parseInt(sortId);
                position++;
                sortId = String.valueOf(position);
            }
            if (id != "" || id != null) {
                mPresenter.jobDetailv(id, position, sortId);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == 100) {
            if (data != null) {
                this.id = data.getStringExtra("id");
                this.position = data.getStringExtra("position");
                this.sortId = data.getStringExtra("sortId");
                if (!TextUtils.isEmpty(sortId)) {
                    int position = Integer.parseInt(sortId);
                    position++;
                    sortId = String.valueOf(position);
                }
                if (this.id != "" || this.id != null) {
                    mPresenter.jobDetailv(id, position, sortId);
                }
            }
        }
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

