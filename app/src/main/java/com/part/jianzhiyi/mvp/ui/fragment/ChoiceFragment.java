package com.part.jianzhiyi.mvp.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
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
import com.part.jianzhiyi.adapter.ChoiceAdapter;
import com.part.jianzhiyi.adapter.ChoiceRecommendAdapter;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.model.entity.ChoiceEntity;
import com.part.jianzhiyi.mvp.contract.ChoiceContract2;
import com.part.jianzhiyi.mvp.presenter.ChoicePresenter;
import com.part.jianzhiyi.mvp.ui.activity.ChoiceRecommendListActivity;
import com.part.jianzhiyi.mvp.ui.activity.RankActivity;
import com.part.jianzhiyi.mvp.ui.activity.VocationActivity;
import com.part.jianzhiyi.mvp.ui.activity.VocationListActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by jyx on 2020/7/14
 *
 * @author jyx
 * @describe
 */
@Route(path = "/app/fragment/choice")
public class ChoiceFragment extends BaseFragment<ChoicePresenter> implements ChoiceContract2.IChoiceView, View.OnClickListener {

    private SmartRefreshLayout mSmartRefresh;
    private TextView mTvEditionMore;
    private ImageView mIvEditionMore;
    private ListViewInScrollView mLvRecommend;
    private TextView mTvRankMore;
    private ImageView mIvRankMore;
    private RelativeLayout mOneRl;
    private RelativeLayout mTwoRl;
    private RelativeLayout mThreeRl;
    private TextView mOneTvTitle;
    private TextView mOneTvMethod;
    private TextView mOneTvTime;
    private TextView mOneTvSex;
    private TextView mOneTvNum;
    private TextView mTwoTvTitle;
    private TextView mTwoTvMethod;
    private TextView mTwoTvTime;
    private TextView mTwoTvSex;
    private TextView mTwoTvNum;
    private TextView mThreeTvTitle;
    private TextView mThreeTvMethod;
    private TextView mThreeTvTime;
    private TextView mThreeTvSex;
    private TextView mThreeTvNum;
    private TextView mTvSelectedMore;
    private ImageView mIvSelectedMore;
    private ListViewInScrollView mLvSelected;
    private FrameLayout mExpressContainer;
    private ImageView mIvAdClose;
    private RelativeLayout mExpressRlContainer;

    private ChoiceAdapter choiceAdapter;
    private List<ChoiceEntity.ChoiceBean> choiceList;
    private List<ChoiceEntity.WeeklyBean> rankList;
    private ChoiceRecommendAdapter adapter;
    private List<ChoiceEntity.XiaobianBean> recommendList;

    private ChoiceEntity.AdvertisingBean advertisingBean;
    //广告
    private TTAdNative mTTAdNative;
    private TTNativeExpressAd mTTAd;
    private Long startTime;
    private boolean mHasShowDownloadActive = false;

    @Override
    protected ChoicePresenter createPresenter() {
        return new ChoicePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choice;
    }

    @Override
    protected void afterCreate() {
        mPresenter.getChoice();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSmartRefresh = view.findViewById(R.id.smartRefresh);
        mTvEditionMore = view.findViewById(R.id.tv_edition_more);
        mIvEditionMore = view.findViewById(R.id.iv_edition_more);
        mLvRecommend = view.findViewById(R.id.lv_recommend);
        mTvRankMore = view.findViewById(R.id.tv_rank_more);
        mIvRankMore = view.findViewById(R.id.iv_rank_more);
        mOneRl = view.findViewById(R.id.one_rl);
        mTwoRl = view.findViewById(R.id.two_rl);
        mThreeRl = view.findViewById(R.id.three_rl);
        mOneTvTitle = view.findViewById(R.id.one_tv_title);
        mOneTvMethod = view.findViewById(R.id.one_tv_method);
        mOneTvTime = view.findViewById(R.id.one_tv_time);
        mOneTvSex = view.findViewById(R.id.one_tv_sex);
        mOneTvNum = view.findViewById(R.id.one_tv_num);
        mTwoTvTitle = view.findViewById(R.id.two_tv_title);
        mTwoTvMethod = view.findViewById(R.id.two_tv_method);
        mTwoTvTime = view.findViewById(R.id.two_tv_time);
        mTwoTvSex = view.findViewById(R.id.two_tv_sex);
        mTwoTvNum = view.findViewById(R.id.two_tv_num);
        mThreeTvTitle = view.findViewById(R.id.three_tv_title);
        mThreeTvMethod = view.findViewById(R.id.three_tv_method);
        mThreeTvTime = view.findViewById(R.id.three_tv_time);
        mThreeTvSex = view.findViewById(R.id.three_tv_sex);
        mThreeTvNum = view.findViewById(R.id.three_tv_num);
        mTvSelectedMore = view.findViewById(R.id.tv_selected_more);
        mIvSelectedMore = view.findViewById(R.id.iv_selected_more);
        mLvSelected = view.findViewById(R.id.lv_selected);
        mExpressRlContainer = view.findViewById(R.id.express_rl_container);
        mExpressContainer = view.findViewById(R.id.express_container);
        mIvAdClose = view.findViewById(R.id.iv_ad_close);
        setToolbarVisible(false);
        choiceList = new ArrayList<>();
        recommendList = new ArrayList<>();
        rankList = new ArrayList<>();
        choiceAdapter = new ChoiceAdapter(getActivity(), choiceList);
        mLvSelected.setAdapter(choiceAdapter);
        adapter = new ChoiceRecommendAdapter(getActivity(),recommendList);
        mLvRecommend.setAdapter(adapter);
        initTTad();
    }

    /**
     * 穿山甲广告
     */
    private void initTTad() {
        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(getActivity());
        mExpressContainer.removeAllViews();
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder() //广告位id
                .setCodeId(PositionId.CHOICE_POS_ID)
                .setSupportDeepLink(true) //请求广告数量为1到3条
                .setAdCount(1) //期望模板广告view的size,单位dp
                .setExpressViewAcceptedSize(300f, 75f)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
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
            public void onAdClicked(View view, int i) {

            }

            @Override
            public void onAdShow(View view, int i) {

            }

            @Override
            public void onRenderFail(View view, String s, int i) {
                Log.e("ExpressView", "render fail:" + (System.currentTimeMillis() - startTime));
            }

            @Override
            public void onRenderSuccess(View view, float v, float v1) {
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
            public void onDownloadActive(long l, long l1, String s, String s1) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                }
            }

            @Override
            public void onDownloadPaused(long l, long l1, String s, String s1) {

            }

            @Override
            public void onDownloadFailed(long l, long l1, String s, String s1) {

            }

            @Override
            public void onDownloadFinished(long l, String s, String s1) {

            }

            @Override
            public void onInstalled(String s, String s1) {

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
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getChoice();
            }
        });
        mTwoRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "choice_weekly_ranking");
                goToDetail(rankList.get(0).getId(), 0, Constants.POSITION_RANK);
            }
        });
        mOneRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "choice_weekly_ranking");
                goToDetail(rankList.get(1).getId(), 1, Constants.POSITION_RANK);
            }
        });
        mThreeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "choice_weekly_ranking");
                goToDetail(rankList.get(2).getId(), 2, Constants.POSITION_RANK);
            }
        });
        mLvSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (choiceList.get(position).getUiType() == 1) {
                    return;
                }
                MobclickAgent.onEvent(getActivity(), "choice_reecommend");
                goToDetail(choiceList.get(position).getId(), position, Constants.POSITION_CHOICE);
            }
        });
        mLvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(getActivity(), "choice_editors");
                goToDetail(recommendList.get(position).getId(), position, Constants.POSITION_CHOICE_RECOMMEND);
            }
        });
        mIvEditionMore.setOnClickListener(this);
        mTvEditionMore.setOnClickListener(this);
        mIvSelectedMore.setOnClickListener(this);
        mTvSelectedMore.setOnClickListener(this);
        mIvRankMore.setOnClickListener(this);
        mTvRankMore.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && choiceAdapter != null) {
            updateAdvertising(this.advertisingBean);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_rank_more) {
            goToRank();
        } else if (v.getId() == R.id.tv_rank_more) {
            goToRank();
        } else if (v.getId() == R.id.iv_edition_more) {
            goToMore(true);
        } else if (v.getId() == R.id.tv_edition_more) {
            goToMore(true);
        } else if (v.getId() == R.id.iv_selected_more) {
            goToMore(false);
        } else if (v.getId() == R.id.tv_selected_more) {
            goToMore(false);
        }
    }

    private void goToDetail(String id, int position, String requestPosition) {
        Intent intent = new Intent(getActivity(), VocationActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("position", requestPosition);
        intent.putExtra("sortId", "" + position);
        startActivity(intent);
    }

    private void goToMore(Boolean isRecommend) {
        if (isRecommend) {
            Intent intent = new Intent(getActivity(), ChoiceRecommendListActivity.class);
            intent.putExtra("type", Constants.TYPE_CHOICE);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), VocationListActivity.class);
            intent.putExtra("type", Constants.TYPE_CHOICE_SPECIAL);
            intent.putExtra("title", "推荐精选");
            startActivity(intent);
        }
    }

    private void goToRank() {
        Intent intent = new Intent(getActivity(), RankActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateRecommendList(List<ChoiceEntity.XiaobianBean> list) {
        mSmartRefresh.finishRefresh();
        if (recommendList != null) {
            recommendList.clear();
        }
        if (list.size() > 0) {
            recommendList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateRankList(List<ChoiceEntity.WeeklyBean> weeklyList) {
        mSmartRefresh.finishRefresh();
        if (rankList != null) {
            this.rankList.clear();
        }
        if (weeklyList != null) {
            rankList.addAll(weeklyList);
            if (weeklyList.size() > 0) {
                mTwoRl.setVisibility(View.VISIBLE);
                ChoiceEntity.WeeklyBean weeklyBean = weeklyList.get(0);
                mTwoTvTitle.setText(weeklyBean.getTitle());
                mTwoTvMethod.setText(weeklyBean.getMethod());
                if (weeklyBean.getTime()==null||weeklyBean.getTime()==""){
                    mTwoTvTime.setText("不限");
                }else {
                    mTwoTvTime.setText(weeklyBean.getTime());
                }
                mTwoTvSex.setText(weeklyBean.getSex() == null ? "男女不限" : weeklyBean.getSex());
                mTwoTvNum.setText("已报名"+weeklyBean.getOrder_number()+"人");
            }else {
                mTwoRl.setVisibility(View.GONE);
            }
            if (weeklyList.size() > 1) {
                mOneRl.setVisibility(View.VISIBLE);
                ChoiceEntity.WeeklyBean weeklyBean = weeklyList.get(1);
                mOneTvTitle.setText(weeklyBean.getTitle());
                mOneTvMethod.setText(weeklyBean.getMethod());
                if (weeklyBean.getTime()==null||weeklyBean.getTime()==""){
                    mOneTvTime.setText("不限");
                }else {
                    mOneTvTime.setText(weeklyBean.getTime());
                }
                mOneTvSex.setText(weeklyBean.getSex() == null ? "男女不限" : weeklyBean.getSex());
                mOneTvNum.setText("已报名"+weeklyBean.getOrder_number()+"人");
            }else {
                mOneRl.setVisibility(View.GONE);
            }
            if (weeklyList.size() > 2) {
                mThreeRl.setVisibility(View.VISIBLE);
                ChoiceEntity.WeeklyBean weeklyBean = weeklyList.get(2);
                mThreeTvTitle.setText(weeklyBean.getTitle());
                mThreeTvMethod.setText(weeklyBean.getMethod());
                if (weeklyBean.getTime()==null||weeklyBean.getTime()==""){
                    mThreeTvTime.setText("不限");
                }else {
                    mThreeTvTime.setText(weeklyBean.getTime());
                }
                mThreeTvSex.setText(weeklyBean.getSex() == null ? "男女不限" : weeklyBean.getSex());
                mThreeTvNum.setText("已报名"+weeklyBean.getOrder_number()+"人");
            }else {
                mThreeRl.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void updateChoiceList(List<ChoiceEntity.ChoiceBean> choiceBeanList) {
        mSmartRefresh.finishRefresh();
        if (choiceList != null) {
            choiceList.clear();
        }
        if (choiceBeanList.size() > 0) {
            this.choiceList.addAll(choiceBeanList);
        }
        choiceAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateAdvertising(ChoiceEntity.AdvertisingBean advertisingBean) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (choiceAdapter != null) {
                choiceAdapter.notifyDataSetChanged();
            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (choiceAdapter != null) {
            choiceAdapter.notifyDataSetChanged();
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        MobclickAgent.onPageStart("精选页面");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("精选页面");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTTAd != null) {
            //调用destroy()方法释放
            mTTAd.destroy();
            mTTAd = null;
        }
    }
}
