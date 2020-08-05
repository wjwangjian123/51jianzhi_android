package com.part.jianzhiyi.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.HomeAdapter;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.enums.MARGIN;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.corecommon.utils.SharedPrefUtils;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.corecommon.utils.UiUtils;
import com.part.jianzhiyi.loader.GlideImageLoader;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.mvp.contract.HomeContract;
import com.part.jianzhiyi.mvp.presenter.HomePresenter;
import com.part.jianzhiyi.mvp.ui.activity.CityActivity;
import com.part.jianzhiyi.mvp.ui.activity.HomeVocationListActivity;
import com.part.jianzhiyi.mvp.ui.activity.HtmlActivity;
import com.part.jianzhiyi.mvp.ui.activity.LoginActivity;
import com.part.jianzhiyi.mvp.ui.activity.SearchActivity;
import com.part.jianzhiyi.mvp.ui.activity.VocationActivity;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Created by jyx on 2020/7/14
 *
 * @author jyx
 * @describe
 */
@Route(path = "/app/fragment/home")
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.IHomeView, View.OnClickListener {

    private Banner mBanner;
    private TextView mTvCity;
    private ImageView mIvCityMore;
    private LinearLayout mLinearSearch;
    private LinearLayout mHomeLinearSearch;
    private ImageView mIvSalary;
    private TextView mTvSalary;
    private LinearLayout mLlSalary;
    private ImageView mIvSpeed;
    private TextView mTvSpeed;
    private LinearLayout mLlSpeed;
    private ImageView mIvGold;
    private TextView mTvGold;
    private LinearLayout mLlGold;
    private ImageView mIvReliable;
    private TextView mTvReliable;
    private LinearLayout mLlReliable;
    private ImageView mIvRecommend;
    private TextView mTvRecommend;
    private ConstraintLayout mClRecommend;
    private ImageView mIvSimple;
    private TextView mTvSimple;
    private ConstraintLayout mClSimple;
    private ListViewInScrollView mListRecommend;
    private ListViewInScrollView mListSimple;
    private SmartRefreshLayout mSmartRefresh;

    private String type_recommend = Constants.TYPE_HOME_RECOMMEND;
    private String type_simple = Constants.TYPE_HOME_SIMPLE;
    private String position_recommend = Constants.POSITION_HOME_RECOMMEND;
    private String position_simple = Constants.POSITION_HOME_SIMPLE;
    private List<JobListResponseEntity2.DataBean> recommendList;
    private List<JobListResponseEntity2.DataBean> simpleList;
    private List<CategoryEntity> categoryEntityList;
    private List<Integer> imageList;
    private List<BannerEntity> bannerList;
    private HomeAdapter recommendAdapter;
    private HomeAdapter simpleAdapter;
    private Boolean isRecommend = true;
    private int recommendPage = Constants.PAGE_INDEX;
    private int simplePage = Constants.PAGE_INDEX;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mBanner = view.findViewById(R.id.banner);
        mTvCity = view.findViewById(R.id.tv_city);
        mIvCityMore = view.findViewById(R.id.iv_city_more);
        mLinearSearch = view.findViewById(R.id.linear_search);
        mHomeLinearSearch = view.findViewById(R.id.home_linear_search);
        mIvSalary = view.findViewById(R.id.iv_salary);
        mTvSalary = view.findViewById(R.id.tv_salary);
        mLlSalary = view.findViewById(R.id.ll_salary);
        mIvSpeed = view.findViewById(R.id.iv_speed);
        mTvSpeed = view.findViewById(R.id.tv_speed);
        mLlSpeed = view.findViewById(R.id.ll_speed);
        mIvGold = view.findViewById(R.id.iv_gold);
        mTvGold = view.findViewById(R.id.tv_gold);
        mLlGold = view.findViewById(R.id.ll_gold);
        mIvReliable = view.findViewById(R.id.iv_reliable);
        mTvReliable = view.findViewById(R.id.tv_reliable);
        mLlReliable = view.findViewById(R.id.ll_reliable);
        mIvRecommend = view.findViewById(R.id.iv_recommend);
        mTvRecommend = view.findViewById(R.id.tv_recommend);
        mClRecommend = view.findViewById(R.id.cl_recommend);
        mIvSimple = view.findViewById(R.id.iv_simple);
        mTvSimple = view.findViewById(R.id.tv_simple);
        mClSimple = view.findViewById(R.id.cl_simple);
        mListRecommend = view.findViewById(R.id.list_recommend);
        mListSimple = view.findViewById(R.id.list_simple);
        mSmartRefresh = view.findViewById(R.id.smartRefresh);
        mBanner.setDelayTime(5000);
    }

    @Override
    protected void afterCreate() {
        setToolbarVisible(false);
        mActivity.setImmerseLayout(mLinearSearch);

        recommendList = new ArrayList<>();
        simpleList = new ArrayList<>();
        categoryEntityList = new ArrayList<>();
        imageList = new ArrayList<>();
        bannerList = new ArrayList<>();

        mPresenter.getBanner();
        mPresenter.getCategory();
        //为你推荐
        request(type_recommend, position_recommend, recommendPage);
        //简单易做
        request(type_simple, position_simple, simplePage);

        recommendAdapter = new HomeAdapter(getActivity(), recommendList);
        mListRecommend.setAdapter(recommendAdapter);

        simpleAdapter = new HomeAdapter(getActivity(), simpleList);
        mListSimple.setAdapter(simpleAdapter);

        mSmartRefresh.setEnableNestedScroll(true);//是否启用嵌套滚动
        mSmartRefresh.setEnableOverScrollBounce(true);//是否启用越界回弹
        mSmartRefresh.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容

        setType(true);
        PreferenceUUID.getInstence().putCity(mTvCity.getText().toString());
    }

    private void request(String type, String position, int page) {
        mSmartRefresh.setEnableAutoLoadMore(false);
        mPresenter.jobList(type, position, page);
    }

    private void setType(boolean isType) {
        this.isRecommend = isType;
        mTvRecommend.setSelected(isType);
        mTvSimple.setSelected(!isType);
        if (isType) {
            mTvRecommend.setTextSize(16f);
            mTvSimple.setTextSize(14f);
            UiUtils.margin(mClRecommend, MARGIN.TOP, 0);
            UiUtils.margin(mClSimple, MARGIN.TOP, UiUtils.sp2px(getContext(), 3f));
            mListRecommend.setVisibility(View.VISIBLE);
            mIvRecommend.setVisibility(View.VISIBLE);
            mListSimple.setVisibility(View.GONE);
            mIvSimple.setVisibility(View.GONE);
        } else {
            mTvRecommend.setTextSize(14f);
            mTvSimple.setTextSize(16f);
            UiUtils.margin(mClRecommend, MARGIN.TOP, UiUtils.sp2px(getContext(), 3f));
            UiUtils.margin(mClSimple, MARGIN.TOP, 0);
            mListRecommend.setVisibility(View.GONE);
            mIvRecommend.setVisibility(View.GONE);
            mListSimple.setVisibility(View.VISIBLE);
            mIvSimple.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mLlSalary.setOnClickListener(this);
        mLlSpeed.setOnClickListener(this);
        mLlGold.setOnClickListener(this);
        mLlReliable.setOnClickListener(this);
        mHomeLinearSearch.setOnClickListener(this);
        mTvCity.setOnClickListener(this);
        mIvCityMore.setOnClickListener(this);
        mClRecommend.setOnClickListener(this);
        mClSimple.setOnClickListener(this);

        //适配器 接口回调
        //兼职推荐
        mListRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (recommendList.get(position).getUiType() == 1) {
                    return;
                }
                Intent intent = new Intent(getActivity(), VocationActivity.class);
                intent.putExtra("id", recommendList.get(position).getId());
                intent.putExtra("position", position_recommend);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
        //简单易做
        mListSimple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (simpleList.get(position).getUiType() == 1) {
                    return;
                }
                Intent intent = new Intent(getActivity(), VocationActivity.class);
                intent.putExtra("id", simpleList.get(position).getId());
                intent.putExtra("position", position_simple);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
        mSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                request(type_recommend, position_recommend, recommendPage);
                ++simplePage;
                request(type_simple, position_simple, simplePage);
            }
        });
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                recommendPage = Constants.PAGE_INDEX;
                request(type_recommend, position_recommend, recommendPage);
                simplePage = Constants.PAGE_INDEX;
                request(type_simple, position_simple, simplePage);
                mPresenter.getBanner();
                mPresenter.getCategory();
                Log.i(TAG, "首页刷新了数据，推荐页码：" + recommendPage + ";简单页码：" + simplePage);
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
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
                    if (type != null) {
                        if (type.equals("1")) {
                            Intent intent = new Intent(getActivity(), VocationActivity.class);
                            intent.putExtra("id", bannerList.get(position).getJob_id());
                            intent.putExtra("position", Constants.POSITION_BANNER);
                            intent.putExtra("sortId", "" + position);
                            startActivity(intent);
                        } else if (type.equals("2")) {
                            int url_redirect = bannerList.get(position).getUrl_redirect();
                            String urls = bannerList.get(position).getUrls();
                            if (!TextUtils.isEmpty(urls)) {
                                if (url_redirect==1){
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse(urls);
                                    intent.setData(content_url);
                                    startActivity(intent);
                                }else if (url_redirect==0){
                                    Intent intent = new Intent(mActivity, HtmlActivity.class);
                                    intent.putExtra(IntentConstant.HTML_URL, urls);
                                    startActivity(intent);
                                }
                            }
                        } else if (type.equals("3")) {
                            String imei;
                            if (Tools.getIMEI(getActivity()) != null) {
                                imei = Tools.getIMEI(getActivity());
                                mPresenter.getBannerUrl(imei);
                            } else if (PreferenceUUID.getInstence().getOaid() != null) {
                                imei = PreferenceUUID.getInstence().getOaid();
                                mPresenter.getBannerUrl(imei);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_salary) {
            MobclickAgent.onEvent(getActivity(), "home_hot");
            if (categoryEntityList == null || categoryEntityList.size() < 1) {
                return;
            }
            Intent intent = new Intent(getActivity(), HomeVocationListActivity.class);
            intent.putExtra("position", categoryEntityList.get(0).getPosition());
            intent.putExtra("type", categoryEntityList.get(0).getType());
            intent.putExtra("title", categoryEntityList.get(0).getName());
            intent.putExtra("category", categoryEntityList.get(0).getId());
            SharedPrefUtils.putString(getActivity(), "type_one", categoryEntityList.get(0).getType());
            startActivity(intent);
        } else if (v.getId() == R.id.ll_speed) {
            MobclickAgent.onEvent(getActivity(), "home_hot");
            if (categoryEntityList == null || categoryEntityList.size() < 2) {
                return;
            }
            Intent intent = new Intent(getActivity(), HomeVocationListActivity.class);
            intent.putExtra("position", categoryEntityList.get(1).getPosition());
            intent.putExtra("type", categoryEntityList.get(1).getType());
            intent.putExtra("title", categoryEntityList.get(1).getName());
            intent.putExtra("category", categoryEntityList.get(1).getId());
            SharedPrefUtils.putString(getActivity(), "type_two", categoryEntityList.get(1).getType());
            startActivity(intent);
        } else if (v.getId() == R.id.ll_gold) {
            MobclickAgent.onEvent(getActivity(), "home_gold");
            if (categoryEntityList == null || categoryEntityList.size() < 3) {
                return;
            }
            Intent intent = new Intent(getActivity(), HomeVocationListActivity.class);
            intent.putExtra("position", categoryEntityList.get(2).getPosition());
            intent.putExtra("type", categoryEntityList.get(2).getType());
            intent.putExtra("title", categoryEntityList.get(2).getName());
            intent.putExtra("category", categoryEntityList.get(2).getId());
            SharedPrefUtils.putString(getActivity(), "type_three", categoryEntityList.get(2).getType());
            startActivity(intent);
        } else if (v.getId() == R.id.ll_reliable) {
            MobclickAgent.onEvent(getActivity(), "home_reliable");
            if (categoryEntityList == null || categoryEntityList.size() < 4) {
                return;
            }
            Intent intent = new Intent(getActivity(), HomeVocationListActivity.class);
            intent.putExtra("position", categoryEntityList.get(3).getPosition());
            intent.putExtra("type", categoryEntityList.get(3).getType());
            intent.putExtra("title", categoryEntityList.get(3).getName());
            intent.putExtra("category", categoryEntityList.get(3).getId());
            SharedPrefUtils.putString(getActivity(), "type_four", categoryEntityList.get(3).getType());
            startActivity(intent);
        } else if (v.getId() == R.id.home_linear_search) {
            MobclickAgent.onEvent(getActivity(), "home_search");
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_city) {
            MobclickAgent.onEvent(getActivity(), "home_city");
            Intent intent = new Intent(getActivity(), CityActivity.class);
            startActivityForResult(intent, 1001);
        } else if (v.getId() == R.id.iv_city_more) {
            MobclickAgent.onEvent(getActivity(), "home_city");
            Intent intent = new Intent(getActivity(), CityActivity.class);
            startActivityForResult(intent, 1001);
        } else if (v.getId() == R.id.cl_recommend) {
            setType(true);
        } else if (v.getId() == R.id.cl_simple) {
            MobclickAgent.onEvent(getActivity(), "home_simpleness");
            setType(false);
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
        Log.i(TAG, "首页更新了数据" + dataBeanList.toString());
        if (dataBeanList.size() > 3) {
            dataBeanList.add(3, bean);
        } else if (dataBeanList.size() > 0) {
            dataBeanList.add(bean);
        }
        if (recommendList.size() > 0 && simpleList.size() > 0) {
            mSmartRefresh.setEnableAutoLoadMore(true);
        }
        if (TextUtils.equals("5", position)) {
            if (recommendPage == Constants.PAGE_INDEX) {
                this.recommendList.clear();
            }
            if (dataBeanList.size() > 0) {
                this.recommendList.addAll(dataBeanList);
                recommendAdapter.notifyDataSetChanged();
            }
        } else {
            if (simplePage == Constants.PAGE_INDEX) {
                this.simpleList.clear();
            }
            if (dataBeanList.size() > 0) {
                this.simpleList.addAll(dataBeanList);
                simpleAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updateAdvertising(String postion, JobListResponseEntity2.AdvertisingBean bean) {

    }

    @Override
    public void updateBanner(List<BannerEntity> bannerEntityList) {
        bannerList.clear();
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
            mTvSalary.setText("边玩边赚");
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && data != null) {
            String city = data.getStringExtra("city");
            mTvCity.setText(city);
            PreferenceUUID.getInstence().putCity(city);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mPresenter != null) {
                //为你推荐
                request(type_recommend, position_recommend, recommendPage);
                //简单易做
                request(type_simple, position_simple, simplePage);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("首页");
        MobclickAgent.onResume(getActivity());
        if (mPresenter != null) {
            //为你推荐
            request(type_recommend, position_recommend, recommendPage);
            //简单易做
            request(type_simple, position_simple, simplePage);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("首页");
        MobclickAgent.onPause(getActivity());
    }
}
