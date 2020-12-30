package com.part.jianzhiyi.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.appbar.AppBarLayout;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.HomeAdapter;
import com.part.jianzhiyi.adapter.HomeLableAdapter;
import com.part.jianzhiyi.adapter.TodayListAdapter;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.corecommon.utils.SharedPrefUtils;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.customview.CenterLayoutManager;
import com.part.jianzhiyi.customview.SpeedScroller;
import com.part.jianzhiyi.loader.GlideImageLoader;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.integral.SignEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.adapter.MerHomeListAdapter;
import com.part.jianzhiyi.modulemerchants.customview.MyViewpager;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.HomeContract;
import com.part.jianzhiyi.mvp.presenter.HomePresenter;
import com.part.jianzhiyi.mvp.ui.activity.ActionListActivity;
import com.part.jianzhiyi.mvp.ui.activity.CityActivity;
import com.part.jianzhiyi.mvp.ui.activity.HomeVocationListActivity;
import com.part.jianzhiyi.mvp.ui.activity.HtmlActivity;
import com.part.jianzhiyi.mvp.ui.activity.HtmlIntegralActivity;
import com.part.jianzhiyi.mvp.ui.activity.IntegralActivity;
import com.part.jianzhiyi.mvp.ui.activity.LoginActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineDeliveryActivity;
import com.part.jianzhiyi.mvp.ui.activity.MineUpdateResumeActivity;
import com.part.jianzhiyi.mvp.ui.activity.SearchActivity;
import com.part.jianzhiyi.mvp.ui.activity.SeeMineActivity;
import com.part.jianzhiyi.mvp.ui.activity.VocationActivity;
import com.part.jianzhiyi.utils.IntentUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by jyx on 2020/7/14
 *
 * @author jyx
 * @describe
 */
@Route(path = "/app/fragment/home")
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.IHomeView, View.OnClickListener {

    private TextView mTvSearch;
    private Banner mBanner;
    private TextView mTvCity;
    private ImageView mIvCityMore;
    private RecyclerView mRecycleLabel;
    private LinearLayout mLlSalary;
    private LinearLayout mLlSpeed;
    private LinearLayout mLlGold;
    private LinearLayout mLlReliable;
    private ListViewInScrollView mListToday;
    //    private ListViewInScrollView mListRecommend;
    private SmartRefreshLayout mSmartRefresh;
    private ImageView mIvSalary;
    private TextView mTvSalary;
    private ImageView mIvSpeed;
    private TextView mTvSpeed;
    private ImageView mIvGold;
    private TextView mTvGold;
    private ImageView mIvReliable;
    private TextView mTvReliable;
    private ImageView mHomeIvYin;
    private SimpleDraweeView mHomeIvSign;

    private String type_recommend = Constants.TYPE_HOME_RECOMMEND;
    private String position_recommend;
    private List<BannerEntity> bannerList;
    private List<CategoryEntity> categoryEntityList;
    private List<SearchEntity.DataBean> searchList;
    private List<LableEntity.DataBean> lableList;
//    private List<JobListResponseEntity2.DataBean> recommendList;

    private TodayListAdapter mTodayListAdapter;
    private HomeLableAdapter mHomeLableAdapter;
    //    private HomeAdapter recommendAdapter;
    private int recommendPage = Constants.PAGE_INDEX;
    private String lable = "0";
    private MyViewpager mViewpager;
    private List<LinearLayout> mListView;
    private MerHomeListAdapter mMerHomeListAdapter;
    private List<HomeAdapter> mHomeAdapters;
    private List<List<JobListResponseEntity2.DataBean>> mLists;
    private int mposition = 0;
    private int mType = 0;
    private AppBarLayout mAppbar;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        super.initView(view);
        mTvCity = view.findViewById(R.id.tv_city);
        mIvCityMore = view.findViewById(R.id.iv_city_more);
        mTvSearch = view.findViewById(R.id.tv_search);
        mSmartRefresh = view.findViewById(R.id.smartRefresh);
        mBanner = view.findViewById(R.id.banner);
        mLlSalary = view.findViewById(R.id.ll_salary);
        mLlSpeed = view.findViewById(R.id.ll_speed);
        mLlGold = view.findViewById(R.id.ll_gold);
        mLlReliable = view.findViewById(R.id.ll_reliable);
        mListToday = view.findViewById(R.id.list_today);
        mRecycleLabel = view.findViewById(R.id.recycle_label);
//        mListRecommend = view.findViewById(R.id.list_recommend);
        mIvSalary = view.findViewById(R.id.iv_salary);
        mTvSalary = view.findViewById(R.id.tv_salary);
        mIvSpeed = view.findViewById(R.id.iv_speed);
        mTvSpeed = view.findViewById(R.id.tv_speed);
        mIvGold = view.findViewById(R.id.iv_gold);
        mTvGold = view.findViewById(R.id.tv_gold);
        mIvReliable = view.findViewById(R.id.iv_reliable);
        mTvReliable = view.findViewById(R.id.tv_reliable);
        mHomeIvYin = view.findViewById(R.id.home_iv_yin);
        mHomeIvSign = view.findViewById(R.id.home_iv_sign);
        mViewpager = view.findViewById(R.id.viewpager);
        mAppbar = view.findViewById(R.id.appbar);

        setToolbarVisible(false);
        mActivity.setImmerseLayout(view.findViewById(R.id.home_rl_title));

        mBanner.setDelayTime(5000);
    }

    private static BtnClickListener mBtnClickListener;

    public static void setmBtnClickListener(BtnClickListener mBtnClickListener) {
        HomeFragment.mBtnClickListener = mBtnClickListener;
    }

    public interface BtnClickListener {
        void onTabClick(int type);
    }

    @Override
    protected void afterCreate() {
        bannerList = new ArrayList<>();
        categoryEntityList = new ArrayList<>();
        searchList = new ArrayList<>();
        lableList = new ArrayList<>();
//        recommendList = new ArrayList<>();
        mListView = new ArrayList<>();
        mHomeAdapters = new ArrayList<>();
        mLists = new ArrayList<>();

        mPresenter.getBanner();
        mPresenter.getCategory();
        mPresenter.search("", "");
        mPresenter.getHomeLabel();
        //兼职推荐
        mSmartRefresh.setEnableAutoLoadMore(false);

        mTodayListAdapter = new TodayListAdapter(getActivity(), searchList);
        mListToday.setAdapter(mTodayListAdapter);
//        recommendAdapter = new HomeAdapter(getActivity(), recommendList);

        CenterLayoutManager centerLayoutManager = new CenterLayoutManager(getActivity());
        centerLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleLabel.setLayoutManager(centerLayoutManager);
        mHomeLableAdapter = new HomeLableAdapter(getActivity());
        mRecycleLabel.setAdapter(mHomeLableAdapter);

        mSmartRefresh.setEnableNestedScroll(true);//是否启用嵌套滚动
        mSmartRefresh.setEnableOverScrollBounce(true);//是否启用越界回弹
        PreferenceUUID.getInstence().putCity(mTvCity.getText().toString());
        mHomeLableAdapter.setmOnItemClickListener(new HomeLableAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                if (position == 0) {
                    MobclickAgent.onEvent(getActivity(), "home_lable_recommend");
                    mPresenter.getaddMd("29");
                }
                if (position == 1) {
                    MobclickAgent.onEvent(getActivity(), "home_lable_one");
                    mPresenter.getaddMd("40");
                }
                if (position == 2) {
                    MobclickAgent.onEvent(getActivity(), "home_lable_two");
                    mPresenter.getaddMd("41");
                }
                if (position == 3) {
                    MobclickAgent.onEvent(getActivity(), "home_lable_three");
                    mPresenter.getaddMd("42");
                }
                if (position == 4) {
                    MobclickAgent.onEvent(getActivity(), "home_lable_four");
                    mPresenter.getaddMd("43");
                }
                mViewpager.setCurrentItem(position);
                CoordinatorLayout.Behavior behavior =
                        ((CoordinatorLayout.LayoutParams) mAppbar
                                .getLayoutParams())
                                .getBehavior();
                if (behavior instanceof AppBarLayout.Behavior) {
                    AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout
                            .Behavior) behavior;
                    int hight = mAppbar.getHeight();
                    // 滑动 计算的高度
                    appBarLayoutBehavior.setTopAndBottomOffset(-hight);
                }

            }
        });
        mMerHomeListAdapter = new MerHomeListAdapter();
        mViewpager.setAdapter(mMerHomeListAdapter);
        mViewpager.setCurrentItem(0);
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            SpeedScroller scroller = new SpeedScroller(mViewpager.getContext(),
                    new AccelerateInterpolator());
            field.set(mViewpager, scroller);
            scroller.setmDuration(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mposition = position;
                int count = lableList.size();
                for (int i = 0; i < count; i++) {
                    if (position == i) {
                        lableList.get(i).setSelect(1);
                    } else {
                        lableList.get(i).setSelect(0);
                    }
                }
                centerLayoutManager.smoothScrollToPosition(mRecycleLabel, new RecyclerView.State(), position);
                mHomeLableAdapter.setList(lableList);
                if (position == 0) {
                    recommendPage = Constants.PAGE_INDEX;
                    lable = "0";
                    position_recommend = Constants.POSITION_HOME_RECOMMEND;
                    mPresenter.jobList(type_recommend, position_recommend, recommendPage, lable);
                } else {
                    recommendPage = Constants.PAGE_INDEX;
                    lable = lableList.get(position).getId();
                    position_recommend = Constants.POSITION_HOME_LABLE;
                    mPresenter.jobList("8", position_recommend, recommendPage, lable);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        mLlSalary.setOnClickListener(this);
        mLlSpeed.setOnClickListener(this);
        mLlGold.setOnClickListener(this);
        mLlReliable.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
        mTvCity.setOnClickListener(this);
        mIvCityMore.setOnClickListener(this);

        mListToday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mPresenter.getaddMd("25");
                    MobclickAgent.onEvent(getActivity(), "home_todaylist_one");
                } else if (position == 1) {
                    mPresenter.getaddMd("26");
                    MobclickAgent.onEvent(getActivity(), "home_todaylist_two");
                } else if (position == 2) {
                    mPresenter.getaddMd("27");
                    MobclickAgent.onEvent(getActivity(), "home_todaylist_three");
                } else if (position == 3) {
                    mPresenter.getaddMd("28");
                    MobclickAgent.onEvent(getActivity(), "home_todaylist_four");
                }
                Intent intent = new Intent(getActivity(), VocationActivity.class);
                intent.putExtra("id", searchList.get(position).getId());
                intent.putExtra("position", Constants.POSITION_HOME_TODAY);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
        mSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++recommendPage;
                if (lable.equals("0")) {
                    position_recommend = Constants.POSITION_HOME_RECOMMEND;
                    mPresenter.jobList(type_recommend, position_recommend, recommendPage, lable);
                } else {
                    position_recommend = Constants.POSITION_HOME_LABLE;
                    mPresenter.jobList("8", position_recommend, recommendPage, lable);
                }
            }
        });
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                recommendPage = Constants.PAGE_INDEX;
                if (lable.equals("0")) {
                    position_recommend = Constants.POSITION_HOME_RECOMMEND;
                    mPresenter.jobList(type_recommend, position_recommend, recommendPage, lable);
                } else {
                    position_recommend = Constants.POSITION_HOME_LABLE;
                    mPresenter.jobList("8", position_recommend, recommendPage, lable);
                }
                mPresenter.getBanner();
                mPresenter.getCategory();
                mPresenter.search("", "");
//                mPresenter.getHomeLabel();
                Log.i(TAG, "首页刷新了数据，推荐页码：" + recommendPage);
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                mPresenter.getaddMd("23");
                MobclickAgent.onEvent(getActivity(), "home_banner");
                if (!PreferenceUUID.getInstence().isUserLogin()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("ToLogin", 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return;
                }
                if (bannerList.size() == 0) {
                    return;
                } else {
                    String type = bannerList.get(position).getType();
                    if (type != null && type != "") {
                        if (type.equals("1")) {
                            //职位详情
                            Intent intent = new Intent(getActivity(), VocationActivity.class);
                            intent.putExtra("id", bannerList.get(position).getJob_id());
                            intent.putExtra("position", Constants.POSITION_BANNER);
                            intent.putExtra("sortId", "" + position);
                            startActivity(intent);
                        } else if (type.equals("2")) {
                            //跳转链接
                            int url_redirect = bannerList.get(position).getUrl_redirect();
                            String urls = bannerList.get(position).getUrls();
                            if (urls != null && urls != "") {
                                if (url_redirect == 1) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse(urls);
                                    intent.setData(content_url);
                                    startActivity(intent);
                                } else if (url_redirect == 0) {
                                    Intent intent = new Intent(mActivity, HtmlActivity.class);
                                    intent.putExtra(IntentConstant.HTML_URL, urls);
                                    intent.putExtra("title", "");
                                    startActivity(intent);
                                }
                            }
                        } else if (type.equals("3")) {
                            //爱变现
                            String imei;
                            if (Tools.getIMEI(getActivity()) != null) {
                                imei = Tools.getIMEI(getActivity());
                                mPresenter.getBannerUrl(imei);
                            } else if (PreferenceUUID.getInstence().getOaid() != null) {
                                imei = PreferenceUUID.getInstence().getOaid();
                                mPresenter.getBannerUrl(imei);
                            }
                        } else if (type.equals("4")) {
                            //职位列表
                            Intent intent = new Intent(getActivity(), ActionListActivity.class);
                            intent.putExtra("id", "");
                            intent.putExtra("type", "0");
                            startActivity(intent);
                        } else if (type.equals("5")) {
                            //橙券活动
                            String urls = bannerList.get(position).getUrls();
                            if (urls != null && urls != "") {
                                Intent intent = new Intent(getActivity(), HtmlIntegralActivity.class);
                                intent.putExtra("url", urls);
                                intent.putExtra("title", "");
                                startActivity(intent);
                            }
                        } else if (type.equals("6")) {
                            //活动页面
                            String urls = bannerList.get(position).getUrls();
                            if (urls != null && urls != "") {
                                Intent intent = new Intent(getActivity(), HtmlActivity.class);
                                intent.putExtra(IntentConstant.HTML_URL, urls);
                                intent.putExtra("title", "");
                                startActivity(intent);
                            }
                        } else if (type.equals("7")) {
                            //积分页面
                            if (PreferenceUUID.getInstence().getUserId() != null && PreferenceUUID.getInstence().getUserId() != "") {
                                mPresenter.getAddInteg(PreferenceUUID.getInstence().getUserId(), 1, "0");
                            }
                        } else if (type.equals("8")) {
                            //我的简历页面
                            Intent intent = new Intent(getActivity(), MineUpdateResumeActivity.class);
                            startActivity(intent);
                        } else if (type.equals("9")) {
                            //切换身份页面
                            ARouter.getInstance().build("/merchants/activity/choose").withInt("type", 0).navigation();
                        } else if (type.equals("10")) {
                            //被查看列表
                            Intent intent = new Intent(getActivity(), MineDeliveryActivity.class);
                            intent.putExtra("positionType", 1);
                            startActivity(intent);
                        } else if (type.equals("11")) {
                            //消息列表
                            if (mBtnClickListener != null) {
                                mBtnClickListener.onTabClick(2);
                            }
                        } else if (type.equals("12")) {
                            //看过我列表
                            Intent intent = new Intent(getActivity(), SeeMineActivity.class);
                            startActivity(intent);
                        } else if (type.equals("13")) {
                            //城市选择页面
                            Intent intent = new Intent(getActivity(), CityActivity.class);
                            startActivityForResult(intent, 1001);
                        } else if (type.equals("14")) {
                            //精选页面
                            if (mBtnClickListener != null) {
                                mBtnClickListener.onTabClick(3);
                            }
                        }
                    }
                }
            }
        });
        mHomeIvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PreferenceUUID.getInstence().getUserId().equals(null) && !PreferenceUUID.getInstence().getUserId().equals("")) {
                    mPresenter.getAddInteg(PreferenceUUID.getInstence().getUserId(), 1, "0");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_salary) {
            mPresenter.getaddMd("24");
            MobclickAgent.onEvent(getActivity(), "home_category_one");
            if (categoryEntityList == null || categoryEntityList.size() < 1) {
                return;
            }
            if (categoryEntityList.get(0).getRdtype().equals("0")) {
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick(1);
                }
            } else {
                Intent intent = new Intent(getActivity(), HomeVocationListActivity.class);
                intent.putExtra("position", categoryEntityList.get(0).getPosition());
                intent.putExtra("type", categoryEntityList.get(0).getType());
                intent.putExtra("title", categoryEntityList.get(0).getName());
                intent.putExtra("category", categoryEntityList.get(0).getId());
                SharedPrefUtils.putString(getActivity(), "type_one", categoryEntityList.get(0).getType());
                startActivity(intent);
            }
        } else if (v.getId() == R.id.ll_speed) {
            mPresenter.getaddMd("24");
            MobclickAgent.onEvent(getActivity(), "home_category_two");
            if (categoryEntityList == null || categoryEntityList.size() < 2) {
                return;
            }
            if (categoryEntityList.get(1).getRdtype().equals("0")) {
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick(1);
                }
            } else {
                Intent intent = new Intent(getActivity(), HomeVocationListActivity.class);
                intent.putExtra("position", categoryEntityList.get(1).getPosition());
                intent.putExtra("type", categoryEntityList.get(1).getType());
                intent.putExtra("title", categoryEntityList.get(1).getName());
                intent.putExtra("category", categoryEntityList.get(1).getId());
                SharedPrefUtils.putString(getActivity(), "type_two", categoryEntityList.get(1).getType());
                startActivity(intent);
            }
        } else if (v.getId() == R.id.ll_gold) {
            mPresenter.getaddMd("24");
            MobclickAgent.onEvent(getActivity(), "home_category_three");
            if (categoryEntityList == null || categoryEntityList.size() < 3) {
                return;
            }
            if (categoryEntityList.get(2).getRdtype().equals("0")) {
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick(1);
                }
            } else {
                Intent intent = new Intent(getActivity(), HomeVocationListActivity.class);
                intent.putExtra("position", categoryEntityList.get(2).getPosition());
                intent.putExtra("type", categoryEntityList.get(2).getType());
                intent.putExtra("title", categoryEntityList.get(2).getName());
                intent.putExtra("category", categoryEntityList.get(2).getId());
                SharedPrefUtils.putString(getActivity(), "type_three", categoryEntityList.get(2).getType());
                startActivity(intent);
            }
        } else if (v.getId() == R.id.ll_reliable) {
            mPresenter.getaddMd("24");
            MobclickAgent.onEvent(getActivity(), "home_category_four");
            if (categoryEntityList == null || categoryEntityList.size() < 4) {
                return;
            }
            if (categoryEntityList.get(3).getRdtype().equals("0")) {
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick(1);
                }
            } else {
                Intent intent = new Intent(getActivity(), HomeVocationListActivity.class);
                intent.putExtra("position", categoryEntityList.get(3).getPosition());
                intent.putExtra("type", categoryEntityList.get(3).getType());
                intent.putExtra("title", categoryEntityList.get(3).getName());
                intent.putExtra("category", categoryEntityList.get(3).getId());
                SharedPrefUtils.putString(getActivity(), "type_four", categoryEntityList.get(3).getType());
                startActivity(intent);
            }
        } else if (v.getId() == R.id.tv_search) {
            MobclickAgent.onEvent(getActivity(), "home_search");
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_city) {
            mPresenter.getaddMd("14");
            MobclickAgent.onEvent(getActivity(), "home_city");
            Intent intent = new Intent(getActivity(), CityActivity.class);
            startActivityForResult(intent, 1001);
        } else if (v.getId() == R.id.iv_city_more) {
            MobclickAgent.onEvent(getActivity(), "home_city");
            Intent intent = new Intent(getActivity(), CityActivity.class);
            startActivityForResult(intent, 1001);
        }
    }

    public void locationCity(String city) {
        mTvCity.setText(city);
        PreferenceUUID.getInstence().putCity(city);
    }

    public String getCity() {
        return mTvCity.getText().toString();
    }

    @Override
    public void updateNewList(String position, List<JobListResponseEntity2.DataBean> dataBeanList) {
        mSmartRefresh.finishRefresh();
        mSmartRefresh.finishLoadMore();
        JobListResponseEntity2.DataBean bean = new JobListResponseEntity2.DataBean(1);
        if (dataBeanList.size() > 3) {
            dataBeanList.add(3, bean);
        } else if (dataBeanList.size() > 0) {
            dataBeanList.add(bean);
        }
        if (mLists.get(mposition).size() > 0) {
            mSmartRefresh.setEnableAutoLoadMore(true);
        }
        if (recommendPage == Constants.PAGE_INDEX) {
            this.mLists.get(mposition).clear();
        }
        if (dataBeanList.size() > 0) {
            this.mLists.get(mposition).addAll(dataBeanList);
        }
        mHomeAdapters.get(mposition).notifyDataSetChanged();
        if (mType == 0) {
            if (mposition < lableList.size() - 1) {
                ++mposition;
                recommendPage = Constants.PAGE_INDEX;
                lable = lableList.get(mposition).getId();
                position_recommend = Constants.POSITION_HOME_LABLE;
                mPresenter.jobList("8", position_recommend, recommendPage, lable);
            } else {
                mType = 1;
            }
        }
    }

    @Override
    public void updateAdvertising(String postion, JobListResponseEntity2.AdvertisingBean bean) {

    }

    @Override
    public void updateBanner(List<BannerEntity> bannerEntityList) {
        bannerList.clear();
        mHomeIvYin.setVisibility(View.VISIBLE);
        if (bannerEntityList.size() == 0) {
            List<Integer> imageList = new ArrayList<>();
            imageList.add(R.drawable.ic_banner_one);
            imageList.add(R.drawable.ic_banner_two);
            //设置图片加载器
            mBanner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            mBanner.setImages(imageList);
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
        } else {
            List<String> imageList = new ArrayList<>();
            bannerList.addAll(bannerEntityList);
            for (int i = 0; i < bannerEntityList.size(); i++) {
                String image = bannerEntityList.get(i).getImage();
                imageList.add(image);
                Log.i("tag", "image url:" + image);
            }
            //设置图片加载器
            mBanner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            mBanner.setImages(imageList);
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
        }
    }

    @Override
    public void updategetBannerUrl(ResponseData responseData) {
        if (responseData != null) {
            Object data = responseData.getData();
            if (!TextUtils.isEmpty(data.toString())) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(data.toString());
                intent.setData(content_url);
                startActivity(intent);
            }
        }
    }

    @Override
    public void updateCategory(List<CategoryEntity> categoryEntityList) {
        this.categoryEntityList = categoryEntityList;
        if (categoryEntityList.size() == 0) {
            mIvSalary.setImageResource(R.drawable.icon_salary);
            mTvSalary.setText("短期日结");
            mIvSpeed.setImageResource(R.drawable.icon_speed);
            mTvSpeed.setText("极速可做");
            mIvGold.setImageResource(R.drawable.icon_gold);
            mTvGold.setText("日进斗金");
            mIvReliable.setImageResource(R.drawable.icon_reliable);
            mTvReliable.setText("靠谱兼职");
        }
        if (categoryEntityList.size() > 0) {
            CategoryEntity categoryEntity = categoryEntityList.get(0);
            Glide.with(getActivity()).load(categoryEntity.getImage()).into(mIvSalary);
            mTvSalary.setText(categoryEntity.getName());
        }
        if (categoryEntityList.size() > 1) {
            CategoryEntity categoryEntity = categoryEntityList.get(1);
            Glide.with(getActivity()).load(categoryEntity.getImage()).into(mIvSpeed);
            mTvSpeed.setText(categoryEntity.getName());
        }
        if (categoryEntityList.size() > 2) {
            CategoryEntity categoryEntity = categoryEntityList.get(2);
            Glide.with(getActivity()).load(categoryEntity.getImage()).into(mIvGold);
            mTvGold.setText(categoryEntity.getName());
        }
        if (categoryEntityList.size() > 3) {
            CategoryEntity categoryEntity = categoryEntityList.get(3);
            Glide.with(getActivity()).load(categoryEntity.getImage()).into(mIvReliable);
            mTvReliable.setText(categoryEntity.getName());
        }
    }

    @Override
    public void updatesearch(SearchEntity searchEntity) {
        if (searchList != null) {
            searchList.clear();
        }
        if (searchEntity.getData() != null) {
            searchList.addAll(searchEntity.getData());
        }
        mTodayListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updategetHomeLabel(LableEntity searchEntity) {
        if (lableList != null) {
            lableList.clear();
        }
        mListView.clear();
        mHomeAdapters.clear();
        LableEntity.DataBean dataBean = new LableEntity.DataBean();
        dataBean.setId("1");
        dataBean.setSelect(1);
        dataBean.setTitle("推荐");
        lableList.add(dataBean);
        if (searchEntity.getData().size() > 0) {
            lableList.addAll(searchEntity.getData());
            mHomeLableAdapter.setList(lableList);
        }
        for (int i = 0; i < lableList.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            ListViewInScrollView listViewInScrollView = new ListViewInScrollView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.shape_list_line_bg);
            listViewInScrollView.setDivider(drawable);
            listViewInScrollView.setDividerHeight(1);
            listViewInScrollView.setOverScrollMode(ListViewInScrollView.OVER_SCROLL_NEVER);
            listViewInScrollView.setVerticalScrollBarEnabled(false);
            listViewInScrollView.setLayoutParams(layoutParams);
            List<JobListResponseEntity2.DataBean> recommendList = new ArrayList<>();
            HomeAdapter homeAdapter = new HomeAdapter(getActivity(), recommendList);
            listViewInScrollView.setAdapter(homeAdapter);
            mHomeAdapters.add(homeAdapter);
            mLists.add(recommendList);
            //兼职推荐
            listViewInScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (recommendList.get(position).getUiType() == 1) {
                        return;
                    }
                    if (lable.equals("0")) {
                        if (position == 0) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_one");
                            mPresenter.getaddMd("30");
                        }
                        if (position == 1) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_two");
                            mPresenter.getaddMd("31");
                        }
                        if (position == 2) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_three");
                            mPresenter.getaddMd("32");
                        }
                        if (position == 4) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_four");
                            mPresenter.getaddMd("33");
                        }
                        if (position == 5) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_five");
                            mPresenter.getaddMd("34");
                        }
                        if (position == 6) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_six");
                            mPresenter.getaddMd("35");
                        }
                        if (position == 7) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_seven");
                            mPresenter.getaddMd("36");
                        }
                        if (position == 8) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_eight");
                            mPresenter.getaddMd("37");
                        }
                        if (position == 9) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_nine");
                            mPresenter.getaddMd("38");
                        }
                        if (position == 10) {
                            MobclickAgent.onEvent(getActivity(), "home_recommend_ten");
                            mPresenter.getaddMd("39");
                        }
                    }
                    Intent intent = new Intent(getActivity(), VocationActivity.class);
                    intent.putExtra("id", recommendList.get(position).getId());
                    intent.putExtra("position", position_recommend);
                    intent.putExtra("sortId", "" + position);
                    startActivity(intent);
                }
            });
            linearLayout.addView(listViewInScrollView);
            mListView.add(linearLayout);
        }
        mMerHomeListAdapter.setData(mListView);
    }

    @Override
    public void updategetSignInfos(SignEntity signEntity) {
        if (signEntity != null) {
            mHomeIvSign.setVisibility(View.VISIBLE);
            if (signEntity.getData().isSing()) {
                FrescoUtil.setGifPic(("res://" + getActivity().getPackageName() + "/" + R.drawable.icon_earn), mHomeIvSign);
            } else {
                FrescoUtil.setGifPic(("res://" + getActivity().getPackageName() + "/" + R.drawable.icon_sign), mHomeIvSign);
            }
        }
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
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && data != null) {
            String city = data.getStringExtra("city");
            mTvCity.setText(city);
            PreferenceUUID.getInstence().putCity(city);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("首页");
        MobclickAgent.onResume(getActivity());
        if (mPresenter != null) {
            if (lable.equals("0")) {
                position_recommend = Constants.POSITION_HOME_RECOMMEND;
                mPresenter.jobList(type_recommend, position_recommend, recommendPage, lable);
            } else {
                position_recommend = Constants.POSITION_HOME_LABLE;
                mPresenter.jobList("8", position_recommend, recommendPage, lable);
            }
            if (!PreferenceUUID.getInstence().getUserId().equals(null) && !PreferenceUUID.getInstence().getUserId().equals("")) {
                mPresenter.getSignInfos(PreferenceUUID.getInstence().getUserId());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("首页");
        MobclickAgent.onPause(getActivity());
    }
}
