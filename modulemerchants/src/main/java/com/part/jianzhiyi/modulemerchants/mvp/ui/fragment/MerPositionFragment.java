package com.part.jianzhiyi.modulemerchants.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.part.jianzhiyi.corecommon.constants.IntentConstant;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseFragment;
import com.part.jianzhiyi.modulemerchants.loader.GlideImageLoader;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MBannerEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MHomeContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MHomePresenter;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerMainActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by jyx on 2020/11/23
 *
 * @author jyx
 * @describe 首页职位页面
 */
public class MerPositionFragment extends BaseFragment<MHomePresenter> implements MHomeContract.IMHomeView, View.OnClickListener {

    private Banner mBanner;
    private TextView mTvToPublish;
    private ImageView mIvToPublish;
    private LinearLayout mLlToPublish;
    private TextView mTvPublished;
    private ImageView mIvPublished;
    private LinearLayout mLlPublished;
    private TextView mTvUpdate;
    private ImageView mIvUpdate;
    private LinearLayout mLlUpdate;
    private ViewPager mViewpager;
    private SmartRefreshLayout mSmartRefresh;
    private List<MBannerEntity.DataBean> bannerList;

    @Override
    protected MHomePresenter createPresenter() {
        return new MHomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mer_position;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getIndexBanner();
        MobclickAgent.onPageStart("商户端首页职位");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    protected void afterCreate() {
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mBanner = view.findViewById(R.id.banner);
        mTvToPublish = view.findViewById(R.id.tv_to_publish);
        mIvToPublish = view.findViewById(R.id.iv_to_publish);
        mLlToPublish = view.findViewById(R.id.ll_to_publish);
        mTvPublished = view.findViewById(R.id.tv_published);
        mIvPublished = view.findViewById(R.id.iv_published);
        mLlPublished = view.findViewById(R.id.ll_published);
        mTvUpdate = view.findViewById(R.id.tv_update);
        mIvUpdate = view.findViewById(R.id.iv_update);
        mLlUpdate = view.findViewById(R.id.ll_update);
        mViewpager = view.findViewById(R.id.viewpager);
        mSmartRefresh = view.findViewById(R.id.smartRefresh);
        setToolbarVisible(false);
        mActivity.setImmerseLayout(view.findViewById(R.id.ll_title));
        mBanner.setDelayTime(5000);
        bannerList = new ArrayList<>();
        mSmartRefresh.setEnableNestedScroll(true);//是否启用嵌套滚动
        mSmartRefresh.setEnableOverScrollBounce(true);//是否启用越界回弹
        mSmartRefresh.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        mSmartRefresh.setEnableRefresh(true);
        mSmartRefresh.setEnableLoadMore(true);
        initViewPager();
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MerToPublishFragment());
        fragments.add(new MerPublishedFragment());
        fragments.add(new MerUpdateFragment());
        mViewpager.setCurrentItem(0);
        mViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        mLlToPublish.setOnClickListener(this);
        mLlPublished.setOnClickListener(this);
        mLlUpdate.setOnClickListener(this);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (bannerList.size() == 0) {
                    return;
                }
                int url_redirect = bannerList.get(position).getUrl_redirect();
                String urls = bannerList.get(position).getUrls();
                if (!TextUtils.isEmpty(urls)) {
                    if (url_redirect == 1) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(urls);
                        intent.setData(content_url);
                        startActivity(intent);
                    } else if (url_redirect == 2) {
                        ARouter.getInstance().build("/app/activity/html").withString(IntentConstant.HTML_URL, urls).withString("title", "").navigation();
                    }
                }
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mIvToPublish.setVisibility(View.VISIBLE);
                    mIvPublished.setVisibility(View.INVISIBLE);
                    mIvUpdate.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    mIvToPublish.setVisibility(View.INVISIBLE);
                    mIvPublished.setVisibility(View.VISIBLE);
                    mIvUpdate.setVisibility(View.INVISIBLE);
                } else if (position == 2) {
                    mIvToPublish.setVisibility(View.INVISIBLE);
                    mIvPublished.setVisibility(View.INVISIBLE);
                    mIvUpdate.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        MerMainActivity.setmBtnClickListener(new MerMainActivity.BtnClickListener() {
            @Override
            public void onTabClick() {
                mViewpager.setCurrentItem(1);
            }
        });
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSmartRefresh.finishRefresh(2000);
                mPresenter.getIndexBanner();
                if (mIvToPublish.getVisibility() == View.VISIBLE) {
                    if (mOnToPublishClickListener != null) {
                        mOnToPublishClickListener.OnToPublishClick();
                    }
                } else if (mIvPublished.getVisibility() == View.VISIBLE) {
                    if (mOnPublishedClickListener != null) {
                        mOnPublishedClickListener.OnPublishedClick();
                    }
                } else if (mIvUpdate.getVisibility() == View.VISIBLE) {
                    if (mOnUpdateClickListener != null) {
                        mOnUpdateClickListener.OnUpdateClick();
                    }
                }
            }
        });
    }

    private static OnToPublishClickListener mOnToPublishClickListener;
    private static OnPublishedClickListener mOnPublishedClickListener;
    private static OnUpdateClickListener mOnUpdateClickListener;

    public void setmOnToPublishClickListener(OnToPublishClickListener mOnToPublishClickListener) {
        MerPositionFragment.mOnToPublishClickListener = mOnToPublishClickListener;
    }

    public void setmOnPublishedClickListener(OnPublishedClickListener mOnPublishedClickListener) {
        MerPositionFragment.mOnPublishedClickListener = mOnPublishedClickListener;
    }

    public void setmOnUpdateClickListener(OnUpdateClickListener mOnUpdateClickListener) {
        MerPositionFragment.mOnUpdateClickListener = mOnUpdateClickListener;
    }

    public interface OnToPublishClickListener {
        void OnToPublishClick();
    }

    public interface OnPublishedClickListener {
        void OnPublishedClick();
    }

    public interface OnUpdateClickListener {
        void OnUpdateClick();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_to_publish) {
            mIvToPublish.setVisibility(View.VISIBLE);
            mIvPublished.setVisibility(View.INVISIBLE);
            mIvUpdate.setVisibility(View.INVISIBLE);
            mViewpager.setCurrentItem(0);
        } else if (v.getId() == R.id.ll_published) {
            mIvToPublish.setVisibility(View.INVISIBLE);
            mIvPublished.setVisibility(View.VISIBLE);
            mIvUpdate.setVisibility(View.INVISIBLE);
            mViewpager.setCurrentItem(1);
        } else if (v.getId() == R.id.ll_update) {
            mIvToPublish.setVisibility(View.INVISIBLE);
            mIvPublished.setVisibility(View.INVISIBLE);
            mIvUpdate.setVisibility(View.VISIBLE);
            mViewpager.setCurrentItem(2);
        }
    }

    @Override
    public void updategetIndexBanner(MBannerEntity mBannerEntity) {
        bannerList.clear();
        if (mBannerEntity != null) {
            if (mBannerEntity.getData().size() == 0) {
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
                bannerList.addAll(mBannerEntity.getData());
                for (int i = 0; i < mBannerEntity.getData().size(); i++) {
                    String image = mBannerEntity.getData().get(i).getImage();
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
    }

    @Override
    public void updategetJobRefresh(ResponseData responseData) {

    }

    @Override
    public void updategetJobSx(ResponseData responseData) {

    }

    @Override
    public void updategetMJobInfo(MJobInfoEntity mJobInfoEntity) {

    }

    @Override
    public void updategetMJobList(MJobListEntity mJobListEntity) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端首页职位");
        MobclickAgent.onPause(getActivity());
    }
}
