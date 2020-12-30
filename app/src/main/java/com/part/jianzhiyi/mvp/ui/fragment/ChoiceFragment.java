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
import com.part.jianzhiyi.adapter.ChoiceRankAdapter;
import com.part.jianzhiyi.adapter.ChoiceRecommendAdapter;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.model.base.ResponseData;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/7/14
 *
 * @author jyx
 * @describe
 */
@Route(path = "/app/fragment/choice")
public class ChoiceFragment extends BaseFragment<ChoicePresenter> implements ChoiceContract2.IChoiceView, View.OnClickListener {

    private SmartRefreshLayout mSmartRefresh;
    private TextView mTvRankMore;
    private ImageView mIvRankMore;
    private RecyclerView mLvRank;

    private TextView mTvEditionMore;
    private ImageView mIvEditionMore;
    private RecyclerView mLvRecommend;

    private TextView mTvSelectedMore;
    private ImageView mIvSelectedMore;
    private ListViewInScrollView mLvSelected;

    private FrameLayout mExpressContainer;
    private ImageView mIvAdClose;
    private RelativeLayout mExpressRlContainer;

    private ChoiceAdapter choiceAdapter;
    private ChoiceRankAdapter mRankAdapter;
    private ChoiceRecommendAdapter mChoiceRecommendAdapter;
    private List<ChoiceEntity.ChoiceBean> choiceList;
    private List<ChoiceEntity.WeeklyBean> rankList;
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
        mTvRankMore = view.findViewById(R.id.tv_rank_more);
        mIvRankMore = view.findViewById(R.id.iv_rank_more);
        mLvRank = view.findViewById(R.id.lv_rank);
        mLvRecommend = view.findViewById(R.id.lv_recommend);
        mTvEditionMore = view.findViewById(R.id.tv_edition_more);
        mIvEditionMore = view.findViewById(R.id.iv_edition_more);

        mTvSelectedMore = view.findViewById(R.id.tv_selected_more);
        mIvSelectedMore = view.findViewById(R.id.iv_selected_more);
        mLvSelected = view.findViewById(R.id.lv_selected);

        mExpressRlContainer = view.findViewById(R.id.express_rl_container);
        mExpressContainer = view.findViewById(R.id.express_container);
        mIvAdClose = view.findViewById(R.id.iv_ad_close);

        setToolbarVisible(false);
        rankList = new ArrayList<>();
        recommendList = new ArrayList<>();
        choiceList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mLvRank.setLayoutManager(linearLayoutManager);
        mRankAdapter = new ChoiceRankAdapter(getActivity());
        mLvRank.setAdapter(mRankAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        mLvRecommend.setLayoutManager(linearLayoutManager1);
        mChoiceRecommendAdapter = new ChoiceRecommendAdapter(getActivity());
        mLvRecommend.setAdapter(mChoiceRecommendAdapter);

        choiceAdapter = new ChoiceAdapter(getActivity(), choiceList);
        mLvSelected.setAdapter(choiceAdapter);
//        initTTad();
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
        mRankAdapter.setmOnItemClickListener(new ChoiceRankAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                mPresenter.getaddMd("50");
                if (position == 0) {
                    MobclickAgent.onEvent(getActivity(), "choice_weekly_ranking_one");
                }
                if (position == 1) {
                    MobclickAgent.onEvent(getActivity(), "choice_weekly_ranking_two");
                }
                if (position == 2) {
                    MobclickAgent.onEvent(getActivity(), "choice_weekly_ranking_three");
                }
                goToDetail(id, position, Constants.POSITION_RANK);
            }
        });
        mLvSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (choiceList.get(position).getUiType() == 1) {
                    return;
                }
                if (position == 0) {
                    MobclickAgent.onEvent(getActivity(), "choice_recommend_one");
                }
                if (position == 1) {
                    MobclickAgent.onEvent(getActivity(), "choice_recommend_two");
                }
                if (position == 2) {
                    MobclickAgent.onEvent(getActivity(), "choice_recommend_three");
                }
                mPresenter.getaddMd("52");
                goToDetail(choiceList.get(position).getId(), position, Constants.POSITION_CHOICE);
            }
        });
        mChoiceRecommendAdapter.setmOnItemClickListener(new ChoiceRecommendAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                mPresenter.getaddMd("51");
                if (position == 0) {
                    MobclickAgent.onEvent(getActivity(), "choice_editors_one");
                }
                if (position == 1) {
                    MobclickAgent.onEvent(getActivity(), "choice_editors_two");
                }
                if (position == 2) {
                    MobclickAgent.onEvent(getActivity(), "choice_editors_three");
                }
                goToDetail(id, position, Constants.POSITION_CHOICE_RECOMMEND);
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
            MobclickAgent.onEvent(getActivity(), "choice_editors_more");
            Intent intent = new Intent(getActivity(), ChoiceRecommendListActivity.class);
            intent.putExtra("type", Constants.TYPE_CHOICE);
            startActivity(intent);
        } else {
            MobclickAgent.onEvent(getActivity(), "choice_recommend_more");
            Intent intent = new Intent(getActivity(), VocationListActivity.class);
            intent.putExtra("type", Constants.TYPE_CHOICE_SPECIAL);
            intent.putExtra("title", "优质精选");
            startActivity(intent);
        }
    }

    private void goToRank() {
        MobclickAgent.onEvent(getActivity(), "choice_weekly_ranking_more");
        Intent intent = new Intent(getActivity(), RankActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateRecommendList(List<ChoiceEntity.XiaobianBean> xiaobianBeanList) {
        mSmartRefresh.finishRefresh();
        if (recommendList != null) {
            recommendList.clear();
        }
        if (xiaobianBeanList.size() > 0) {
            for (int i = 0; i < xiaobianBeanList.size(); i++) {
                if (i < 3) {
                    this.recommendList.add(xiaobianBeanList.get(i));
                }
            }
            mChoiceRecommendAdapter.setList(recommendList);
        }
    }

    @Override
    public void updateRankList(List<ChoiceEntity.WeeklyBean> weeklyList) {
        mSmartRefresh.finishRefresh();
        if (rankList != null) {
            this.rankList.clear();
        }
        if (weeklyList.size() > 0) {
            for (int i = 0; i < weeklyList.size(); i++) {
                if (i < 3) {
                    this.rankList.add(weeklyList.get(i));
                }
            }
            mRankAdapter.setList(rankList);
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
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (choiceAdapter != null) {
                choiceAdapter.notifyDataSetChanged();
            }
            if (mRankAdapter != null) {
                mRankAdapter.notifyDataSetChanged();
            }
            if (mChoiceRecommendAdapter != null) {
                mChoiceRecommendAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("精选页面");
        MobclickAgent.onResume(getActivity());
        if (choiceAdapter != null) {
            choiceAdapter.notifyDataSetChanged();
        }
        if (mRankAdapter != null) {
            mRankAdapter.notifyDataSetChanged();
        }
        if (mChoiceRecommendAdapter != null) {
            mChoiceRecommendAdapter.notifyDataSetChanged();
        }
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
