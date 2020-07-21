package com.part.jianzhiyi.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.ad.TTAdManagerHolder;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-27
 **/
public class CSJAdView extends RelativeLayout {

    //广告
    private final static String TAG = CSJAdView.class.getSimpleName();
    private Context context;
    private FrameLayout mExpressContainer;
    private RelativeLayout mExpressRlContainer;
    private ImageView mIvAdClose;
    //广告
    private TTAdNative mTTAdNative;
    private TTNativeExpressAd mTTAd;
    private long startTime = 0;
    private boolean mHasShowDownloadActive = false;
    private OnAdListener onAdListener;
    private boolean isReceive = false;
    private boolean isClose = false;
    private String posId;

    public CSJAdView(Context context) {
        super(context);
        init(context);
    }

    public CSJAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CSJAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public void setOnAdListener(OnAdListener onAdListener) {
        this.onAdListener = onAdListener;
    }

    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.native_ad, null);
        initView(view);
        setListener();
    }

    private void initView(View view) {
        mExpressContainer = view.findViewById(R.id.express_container);
        mExpressRlContainer = view.findViewById(R.id.express_rl_container);
        mIvAdClose = view.findViewById(R.id.iv_ad_close);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        addView(view);
        setVisibility(GONE);
    }

    public void loadAd() {
        if (this.mTTAd != null) {
            return;
        }
        this.getBanner();
    }

    /**
     * 穿山甲广告
     */
    private void getBanner() {
        if (this.mTTAdNative != null && this.posId.equals(posId)) {
            return;
        }
        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                //广告位id
                .setCodeId(posId)
                .setSupportDeepLink(true)
                //请求广告数量为1到3条
                .setAdCount(1)
                //期望模板广告view的size,单位dp
                .setExpressViewAcceptedSize(320, 94)
                .setImageAcceptedSize(320, 94)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                if (!isReceive) {
                    closeAd();
                }
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                if (!isClose) {
                    mTTAd = ads.get(0);
//                    mTTAd.setSlideIntervalTime(10 * 1000);
                    bindAdListener(mTTAd);
                    startTime = System.currentTimeMillis();
                    mTTAd.render();
                }
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
                if (!isClose) {
                    if (mExpressContainer.getChildCount() > 0) {
                        mExpressContainer.removeAllViews();
                    }
                    mIvAdClose.setVisibility(VISIBLE);
                    mExpressContainer.addView(view);
                    isReceive = true;
                    if (onAdListener != null) {
                        onAdListener.onAdShowListener();
                    }
                }
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

    private void setListener() {
        mIvAdClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAd();
            }
        });
    }

    public interface OnAdListener {
        void onAdShowListener();

        void onAdCloseListener();
    }

    private void closeAd() {
        isClose = true;
        //用户选择不喜欢原因后，移除广告展示
        mExpressContainer.removeAllViews();
        mExpressRlContainer.setVisibility(View.GONE);
        if (mTTAd != null) {
            //调用destroy()方法释放
            mTTAd.destroy();
            mTTAd = null;
        }
        if (onAdListener == null) {
            return;
        }
        onAdListener.onAdCloseListener();
    }
}
