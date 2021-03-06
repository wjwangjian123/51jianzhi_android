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
import com.facebook.drawee.view.SimpleDraweeView;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.HomeAdapter;
import com.part.jianzhiyi.adapter.HomeLableAdapter;
import com.part.jianzhiyi.adapter.TodayListAdapter;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.corecommon.utils.SharedPrefUtils;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.loader.GlideImageLoader;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.model.entity.integral.SignEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.mvp.contract.HomeContract;
import com.part.jianzhiyi.mvp.presenter.HomePresenter;
import com.part.jianzhiyi.mvp.ui.activity.CityActivity;
import com.part.jianzhiyi.mvp.ui.activity.HomeVocationListActivity;
import com.part.jianzhiyi.mvp.ui.activity.HtmlActivity;
import com.part.jianzhiyi.mvp.ui.activity.IntegralActivity;
import com.part.jianzhiyi.mvp.ui.activity.LoginActivity;
import com.part.jianzhiyi.mvp.ui.activity.SearchActivity;
import com.part.jianzhiyi.mvp.ui.activity.VocationActivity;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.part.jianzhiyi.utils.IntentUtils;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ListViewInScrollView mListRecommend;
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
    private List<JobListResponseEntity2.DataBean> recommendList;

    private TodayListAdapter mTodayListAdapter;
    private HomeLableAdapter mHomeLableAdapter;
    private HomeAdapter recommendAdapter;
    private int recommendPage = Constants.PAGE_INDEX;
    private String lable = "0";

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
        mListRecommend = view.findViewById(R.id.list_recommend);
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

        setToolbarVisible(false);
        mActivity.setImmerseLayout(view.findViewById(R.id.home_rl_title));

        mBanner.setDelayTime(5000);
    }

    private static BtnClickListener mBtnClickListener;

    public static void setmBtnClickListener(BtnClickListener mBtnClickListener) {
        HomeFragment.mBtnClickListener = mBtnClickListener;
    }

    public interface BtnClickListener {
        void onTabClick();
    }

    @Override
    protected void afterCreate() {
        bannerList = new ArrayList<>();
        categoryEntityList = new ArrayList<>();
        searchList = new ArrayList<>();
        lableList = new ArrayList<>();
        recommendList = new ArrayList<>();

        mPresenter.getBanner();
        mPresenter.getCategory();
        mPresenter.search("", "");
        mPresenter.getHomeLabel();
        //????????????
        mSmartRefresh.setEnableAutoLoadMore(false);
        position_recommend = Constants.POSITION_HOME_RECOMMEND;
        mPresenter.jobList(type_recommend, position_recommend, recommendPage, lable);

        mTodayListAdapter = new TodayListAdapter(getActivity(), searchList);
        mListToday.setAdapter(mTodayListAdapter);
        recommendAdapter = new HomeAdapter(getActivity(), recommendList);
        mListRecommend.setAdapter(recommendAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleLabel.setLayoutManager(linearLayoutManager);
        mHomeLableAdapter = new HomeLableAdapter(getActivity());
        mRecycleLabel.setAdapter(mHomeLableAdapter);

        mSmartRefresh.setEnableNestedScroll(true);//????????????????????????
        mSmartRefresh.setEnableOverScrollBounce(true);//????????????????????????
        PreferenceUUID.getInstence().putCity(mTvCity.getText().toString());
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
                Intent intent = new Intent(getActivity(), VocationActivity.class);
                intent.putExtra("id", searchList.get(position).getId());
                intent.putExtra("position", Constants.POSITION_HOME_TODAY);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
        //????????? ????????????
        //????????????
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
        mHomeLableAdapter.setmOnItemClickListener(new HomeLableAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                lable = id;
                int count = lableList.size();
                for (int i = 0; i < count; i++) {
                    if (position == i) {
                        lableList.get(i).setSelect(1);
                    } else {
                        lableList.get(i).setSelect(0);
                    }
                }
                mHomeLableAdapter.setList(lableList);
                position_recommend = Constants.POSITION_HOME_LABLE;
                recommendPage = Constants.PAGE_INDEX;
                mPresenter.jobList("8", position_recommend, recommendPage, lable);
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
                lable = "0";
                position_recommend = Constants.POSITION_HOME_RECOMMEND;
                mPresenter.jobList(type_recommend, position_recommend, recommendPage, lable);
                mPresenter.getBanner();
                mPresenter.getCategory();
                mPresenter.search("", "");
                mPresenter.getHomeLabel();
                Log.i(TAG, "???????????????????????????????????????" + recommendPage);
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
            MobclickAgent.onEvent(getActivity(), "home_hot");
            if (categoryEntityList == null || categoryEntityList.size() < 1) {
                return;
            }
            if (categoryEntityList.get(0).getRdtype().equals("0")) {
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick();
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
            MobclickAgent.onEvent(getActivity(), "home_hot");
            if (categoryEntityList == null || categoryEntityList.size() < 2) {
                return;
            }
            if (categoryEntityList.get(1).getRdtype().equals("0")) {
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick();
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
            MobclickAgent.onEvent(getActivity(), "home_gold");
            if (categoryEntityList == null || categoryEntityList.size() < 3) {
                return;
            }
            if (categoryEntityList.get(2).getRdtype().equals("0")) {
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick();
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
            MobclickAgent.onEvent(getActivity(), "home_reliable");
            if (categoryEntityList == null || categoryEntityList.size() < 4) {
                return;
            }
            if (categoryEntityList.get(3).getRdtype().equals("0")) {
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick();
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
        if (recommendList.size() > 0) {
            mSmartRefresh.setEnableAutoLoadMore(true);
        }
        if (recommendPage == Constants.PAGE_INDEX) {
            this.recommendList.clear();
        }
        if (dataBeanList.size() > 0) {
            this.recommendList.addAll(dataBeanList);
        }
        recommendAdapter.notifyDataSetChanged();
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
            //?????????????????????
            mBanner.setImageLoader(new GlideImageLoader());
            //??????????????????
            mBanner.setImages(imageList);
            //banner?????????????????????????????????????????????
            mBanner.start();
        } else {
            List<String> imageList = new ArrayList<>();
            bannerList.addAll(bannerEntityList);
            for (int i = 0; i < bannerEntityList.size(); i++) {
                String image = bannerEntityList.get(i).getImage();
                imageList.add(image);
                Log.i("tag", "image url:" + image);
            }
            //?????????????????????
            mBanner.setImageLoader(new GlideImageLoader());
            //??????????????????
            mBanner.setImages(imageList);
            //banner?????????????????????????????????????????????
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
            mTvSalary.setText("????????????");
            mIvSpeed.setImageResource(R.drawable.icon_speed);
            mTvSpeed.setText("????????????");
            mIvGold.setImageResource(R.drawable.icon_gold);
            mTvGold.setText("????????????");
            mIvReliable.setImageResource(R.drawable.icon_reliable);
            mTvReliable.setText("????????????");
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
        LableEntity.DataBean dataBean = new LableEntity.DataBean();
        dataBean.setId("1");
        dataBean.setSelect(1);
        dataBean.setTitle("??????");
        lableList.add(dataBean);
        if (searchEntity.getData() != null) {
            lableList.addAll(searchEntity.getData());
        }
        mHomeLableAdapter.setList(lableList);
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
            Bundle bundle = new Bundle();
            bundle.putString("code", responseData.getCode());
            bundle.putSerializable("SignInfoEntity", responseData.getData());
            IntentUtils.getInstence().intent(getActivity(), IntegralActivity.class, bundle);
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("??????");
        MobclickAgent.onResume(getActivity());
        if (mPresenter != null) {
            if (lableList.equals("0")) {
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
        MobclickAgent.onPageEnd("??????");
        MobclickAgent.onPause(getActivity());
    }
}
