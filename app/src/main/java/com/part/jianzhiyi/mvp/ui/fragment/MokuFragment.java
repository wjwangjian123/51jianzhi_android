package com.part.jianzhiyi.mvp.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fendasz.moku.planet.entity.ApiDataCallBack;
import com.fendasz.moku.planet.helper.ApiDataHelper;
import com.fendasz.moku.planet.source.bean.ClientSampleTaskData;
import com.fendasz.moku.planet.utils.LogUtils;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.customview.NoScrollViewPager;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuTaskListEntity;
import com.part.jianzhiyi.mvp.contract.moku.MokuContract;
import com.part.jianzhiyi.mvp.presenter.moku.MokuPresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.functions.Action;

/**
 * Created by jyx on 2020/8/24
 *
 * @author jyx
 * @describe
 */
public class MokuFragment extends BaseFragment<MokuPresenter> implements MokuContract.IMokuView {

    private TextView mMokuTvTocompleted;
    private ImageView mMokuIvTocompleted;
    private LinearLayout mMokuLinearTocompleted;
    private TextView mMokuTvSubmitted;
    private ImageView mMokuIvSubmitted;
    private LinearLayout mMokuLinearSubmitted;
    private SmartRefreshLayout mMokuSmartRefresh;
    private NoScrollViewPager mMokuViewpager;

    private ArrayList<Fragment> fragments;
    private ApiDataHelper mApiDataHelper;
    private List<ClientSampleTaskData> mTaskToBeCompletedDataList;
    private List<MokuTaskListEntity.DataBean> mTaskInReviewDataList;
    private String mUserId;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_moku;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMokuTvTocompleted = view.findViewById(R.id.moku_tv_tocompleted);
        mMokuIvTocompleted = view.findViewById(R.id.moku_iv_tocompleted);
        mMokuLinearTocompleted = view.findViewById(R.id.moku_linear_tocompleted);
        mMokuTvSubmitted = view.findViewById(R.id.moku_tv_submitted);
        mMokuIvSubmitted = view.findViewById(R.id.moku_iv_submitted);
        mMokuLinearSubmitted = view.findViewById(R.id.moku_linear_submitted);
        mMokuSmartRefresh = view.findViewById(R.id.moku_smartRefresh);
        mMokuViewpager = view.findViewById(R.id.moku_viewpager);
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarVisible(false);
        mUserId = PreferenceUUID.getInstence().getUserId();
        position = 0;
        fragments = new ArrayList<>();
        mTaskToBeCompletedDataList = new ArrayList<>();
        mTaskInReviewDataList = new ArrayList<>();
        mApiDataHelper = new ApiDataHelper.HelperBuilder(mUserId).create(getActivity());
        mMokuTvTocompleted.setSelected(true);
        mMokuIvTocompleted.setVisibility(View.VISIBLE);
        initViewPager();
    }

    private void initViewPager() {
        fragments.add(new ToCompletedFragment(mTaskToBeCompletedDataList, mUserId));
        fragments.add(new SubmitFragment(mTaskInReviewDataList));
        //设置viewpager的缓存
//        wallet_viewpager.setOffscreenPageLimit(3)
        //禁止viewpager左右滑动
        mMokuViewpager.setNoScroll(true);
        mMokuViewpager.setCurrentItem(0);
        mMokuViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
        mMokuLinearTocompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMokuTvTocompleted.setSelected(true);
                mMokuIvTocompleted.setVisibility(View.VISIBLE);
                mMokuTvSubmitted.setSelected(false);
                mMokuIvSubmitted.setVisibility(View.INVISIBLE);
                mMokuViewpager.setCurrentItem(0);
                getTaskList(null);
            }
        });
        mMokuLinearSubmitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMokuTvTocompleted.setSelected(false);
                mMokuIvTocompleted.setVisibility(View.INVISIBLE);
                mMokuTvSubmitted.setSelected(true);
                mMokuIvSubmitted.setVisibility(View.VISIBLE);
                mMokuViewpager.setCurrentItem(1);
                mPresenter.getTaskList();
            }
        });
        mMokuSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mMokuSmartRefresh.finishRefresh(2000);
                if (mMokuIvTocompleted.getVisibility() == View.VISIBLE) {
                    getTaskList(new Action() {
                        @Override
                        public void run() throws Exception {
                        }
                    });
                } else if (mMokuIvSubmitted.getVisibility() == View.VISIBLE) {
                    mPresenter.getTaskList();
                }
            }
        });
        if (mMokuViewpager != null) {
            mMokuViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int i) {
                    position = i;
                    if (i == 0) {
                        mMokuTvTocompleted.setSelected(true);
                        mMokuIvTocompleted.setVisibility(View.VISIBLE);
                        mMokuTvSubmitted.setSelected(false);
                        mMokuIvSubmitted.setVisibility(View.INVISIBLE);
                    } else if (i == 1) {
                        mMokuTvTocompleted.setSelected(false);
                        mMokuIvTocompleted.setVisibility(View.INVISIBLE);
                        mMokuTvSubmitted.setSelected(true);
                        mMokuIvSubmitted.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private void getTaskList(Action action) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mApiDataHelper.getTaskList(new ApiDataCallBack<List<ClientSampleTaskData>>() {
                    @Override
                    public void success(int code, List<ClientSampleTaskData> data) {
                        mTaskToBeCompletedDataList.clear();
                        mTaskToBeCompletedDataList.addAll(data);
                        notifyDataSetChanged();
                        if (action != null) {
                            try {
                                action.run();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void error(int code, String message) {
                        LogUtils.log(TAG, "code:" + code + " message:" + message);
                        Toast.makeText(getActivity(), "获取任务列表失败" + message, Toast.LENGTH_SHORT).show();
                        if (action != null) {
                            try {
                                action.run();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 刷新ListView
     */
    private void notifyDataSetChanged() {
        if (position == 0) {
            ToCompletedFragment currentFragment = (ToCompletedFragment) fragments.get(position);
            currentFragment.notifyDataSetChanged();
        }
        if (position == 1) {
            SubmitFragment submitFragment = (SubmitFragment) fragments.get(position);
            submitFragment.notifyDataSetChanged();
        }
    }

    @Override
    protected MokuPresenter createPresenter() {
        return new MokuPresenter(this);
    }

    @Override
    protected void afterCreate() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMokuIvTocompleted.getVisibility() == View.VISIBLE) {
            getTaskList(null);
        } else if (mMokuIvSubmitted.getVisibility() == View.VISIBLE) {
            if (mPresenter != null) {
                mPresenter.getTaskList();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mApiDataHelper != null) {
            mApiDataHelper.closeDisposable();
        }
    }

    @Override
    public void updategetTaskList(MokuTaskListEntity responseData) {
        mTaskInReviewDataList.clear();
        if (responseData.getData() != null) {
            if (responseData.getData().size() > 0) {
                mTaskInReviewDataList.addAll(responseData.getData());
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updategetAddTask(ResponseData responseData, int statusid) {

    }
}
