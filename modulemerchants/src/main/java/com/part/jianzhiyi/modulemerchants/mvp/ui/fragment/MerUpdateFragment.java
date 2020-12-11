package com.part.jianzhiyi.modulemerchants.mvp.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.adapter.MerUpdateAdapter;
import com.part.jianzhiyi.modulemerchants.base.BaseFragment;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MBannerEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MHomeContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MHomePresenter;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/12/1
 *
 * @author jyx
 * @describe
 */
public class MerUpdateFragment extends BaseFragment<MHomePresenter> implements MHomeContract.IMHomeView, View.OnClickListener {

    private TextView mTvStartTime;
    private TextView mTvEndTime;
    private ImageView mIvDate;
    private RecyclerView mRecycleview;
    private ImageView mIvEmpty;
    private List<MJobListEntity.DataBeanX.DataBean> mList;
    private MerUpdateAdapter mMerUpdateAdapter;

    @Override
    protected MHomePresenter createPresenter() {
        return new MHomePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端首页修改中页面");
        MobclickAgent.onResume(getActivity());
        mPresenter.getMJobList("3", mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim());
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if (mPresenter != null && mMerUpdateAdapter != null) {
//                mPresenter.getMJobList("3", mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim());
//            }
//        }
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mer_update;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTvStartTime = view.findViewById(R.id.tv_start_time);
        mTvEndTime = view.findViewById(R.id.tv_end_time);
        mIvDate = view.findViewById(R.id.iv_date);
        mRecycleview = view.findViewById(R.id.recycleview);
        mIvEmpty = view.findViewById(R.id.iv_empty);
        setToolbarVisible(false);
        mList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecycleview.setLayoutManager(linearLayoutManager);
        mMerUpdateAdapter = new MerUpdateAdapter(getActivity());
        mRecycleview.setAdapter(mMerUpdateAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mIvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击拉起时间选择器
                MobclickAgent.onEvent(getActivity(), "mer_update_date");
                initStartDate();
            }
        });
        MerPositionFragment merPositionFragment = new MerPositionFragment();
        merPositionFragment.setmOnUpdateClickListener(new MerPositionFragment.OnUpdateClickListener() {
            @Override
            public void OnUpdateClick() {
                mPresenter.getMJobList("3", "", "");
            }
        });
    }

    private void initStartDate() {
        //时间选择器
        //获取系统的日期
        Calendar selectedDate = Calendar.getInstance();
        //年
        int year = selectedDate.get(Calendar.YEAR);
        //月
        int month = selectedDate.get(Calendar.MONTH);
        //日
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        Calendar startDate = Calendar.getInstance();
        startDate.set(year - 1, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, day);
        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTvStartTime.setText(getDate(date));
                initEndDate();
            }
        }) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(true)
                .setDividerColor(getResources().getColor(R.color.color_EEEEEE))
                .setTitleText("选择开始时间")
                .setTitleColor(getResources().getColor(R.color.color_666666))
                .setTitleSize(14)
                .setCancelColor(getResources().getColor(R.color.color_666666))
                .setSubmitColor(getResources().getColor(R.color.color_666666))
                .setContentTextSize(15)
                .setSubCalSize(15)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
        pvTime.show();
    }

    private void initEndDate() {
        //时间选择器
        //获取系统的日期
        Calendar selectedDate = Calendar.getInstance();
        //年
        int year = selectedDate.get(Calendar.YEAR);
        //月
        int month = selectedDate.get(Calendar.MONTH);
        //日
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        Calendar startDate = Calendar.getInstance();
        startDate.set(year - 1, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, day);
        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String start = mTvStartTime.getText().toString().trim();
                String end = getDate(date);
                long s = dateToTimeStamp(start);
                long e = dateToTimeStamp(end);
                if (e < s) {
                    showToast("开始时间必须小于结束时间");
                    return;
                }
                mTvEndTime.setText(getDate(date));
                mPresenter.getMJobList("3", mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim());
            }
        }) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(true)
                .setDividerColor(getResources().getColor(R.color.color_EEEEEE))
                .setTitleText("选择结束时间")
                .setTitleColor(getResources().getColor(R.color.color_666666))
                .setTitleSize(14)
                .setCancelColor(getResources().getColor(R.color.color_666666))
                .setSubmitColor(getResources().getColor(R.color.color_666666))
                .setContentTextSize(15)
                .setSubCalSize(15)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
        pvTime.show();
    }

    /**
     * @param date 日期转时间戳
     * @return
     */
    public static long dateToTimeStamp(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long l = sdf.parse(date).getTime() / 1000;
            return l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDate(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(time);
        return format;
    }

    @Override
    protected void afterCreate() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void updategetIndexBanner(MBannerEntity mBannerEntity) {

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
        mList.clear();
        if (mJobListEntity != null) {
            mTvStartTime.setText(mJobListEntity.getData().getBinfo().getStart_time());
            mTvEndTime.setText(mJobListEntity.getData().getBinfo().getEnd_time());
            if (mJobListEntity.getData().getData().size() > 0) {
                mRecycleview.setVisibility(View.VISIBLE);
                mIvEmpty.setVisibility(View.GONE);
                mList.addAll(mJobListEntity.getData().getData());
                mMerUpdateAdapter.setList(mList);
            } else {
                mRecycleview.setVisibility(View.GONE);
                mIvEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端首页修改中页面");
        MobclickAgent.onPause(getActivity());
    }
}
