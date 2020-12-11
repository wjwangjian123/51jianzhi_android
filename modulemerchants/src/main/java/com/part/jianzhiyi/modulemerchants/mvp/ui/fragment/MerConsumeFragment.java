package com.part.jianzhiyi.modulemerchants.mvp.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.adapter.MerConsumeAdapter;
import com.part.jianzhiyi.modulemerchants.base.BaseFragment;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MMinMoneyEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MZfbPayEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MerWalletContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MerWalletPresenter;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerMyWalletActivity;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jyx on 2020/11/23
 *
 * @author jyx
 * @describe 消费明细页面
 */
public class MerConsumeFragment extends BaseFragment<MerWalletPresenter> implements MerWalletContract.IMerWalletView {

    private TextView mTvStartTime;
    private TextView mTvEndTime;
    private ImageView mIvDate;
    private TextView mTvTime;
    private TextView mTvConsume;
    private TextView mTvEstimate;
    private ListViewInScrollView mListConsume;
    private List<MMyWalletEntity.DataBean.SubListBean> mlist;
    private MerConsumeAdapter mMerConsumeAdapter;
    private int page = 1;

    @Override
    protected MerWalletPresenter createPresenter() {
        return new MerWalletPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        MobclickAgent.onPageStart("商户端消费明细页面");
        MobclickAgent.onResume(getActivity());
        if (mPresenter != null) {
            mPresenter.getMyMoney(mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim(), "1", page);
        }
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if (mPresenter != null && mMerConsumeAdapter != null) {
//                mPresenter.getMyMoney(mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim(), "1", 1);
//            }
//        }
//    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTvStartTime = view.findViewById(R.id.tv_start_time);
        mTvEndTime = view.findViewById(R.id.tv_end_time);
        mIvDate = view.findViewById(R.id.iv_date);
        mTvTime = view.findViewById(R.id.tv_time);
        mTvConsume = view.findViewById(R.id.tv_consume);
        mTvEstimate = view.findViewById(R.id.tv_estimate);
        mListConsume = view.findViewById(R.id.list_consume);
        setToolbarVisible(false);
        mlist = new ArrayList<>();
        mMerConsumeAdapter = new MerConsumeAdapter(getActivity(), mlist);
        mListConsume.setAdapter(mMerConsumeAdapter);
    }

    public static String getDate(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(time);
        return format;
    }

    @Override
    protected void setListener() {
        super.setListener();
        mIvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击拉起时间选择器
                initStartDate();
            }
        });
        MerMyWalletActivity merMyWalletActivity = new MerMyWalletActivity();
        merMyWalletActivity.setmOnConsumeClickListener(new MerMyWalletActivity.OnConsumeClickListener() {
            @Override
            public void OnConsumeClick(int type) {
                if (type == 1) {
                    //下拉刷新
                    page = 1;
                    mPresenter.getMyMoney(mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim(), "1", page);
                } else if (type == 2) {
                    //上拉加载更多
                    ++page;
                    mPresenter.getMyMoney(mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim(), "1", page);
                }
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
                page = 1;
                mPresenter.getMyMoney(mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim(), "1", page);
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mer_consume;
    }

    @Override
    protected void afterCreate() {

    }

    @Override
    public void updategetMinMoney(MMinMoneyEntity mMinMoneyEntity) {

    }

    @Override
    public void updategetMyMoney(MMyWalletEntity mMyWalletEntity) {
        if (page == 1) {
            mlist.clear();
        }
        if (mMyWalletEntity != null && mMyWalletEntity.getData() != null) {
            if (mMyWalletEntity.getData().getBusInfo() != null) {
                mTvStartTime.setText(mMyWalletEntity.getData().getBusInfo().getStart_time());
                mTvEndTime.setText(mMyWalletEntity.getData().getBusInfo().getEnd_time());
                mTvTime.setText(mMyWalletEntity.getData().getBusInfo().getDate());
                mTvEstimate.setText(mMyWalletEntity.getData().getBusInfo().getMsg());
                mTvConsume.setText(mMyWalletEntity.getData().getBusInfo().getEstimate_money());
            }
            if (mMyWalletEntity.getData().getSubList().size() > 0) {
                for (int i = 0; i < mMyWalletEntity.getData().getSubList().size(); i++) {
                    mMyWalletEntity.getData().getSubList().get(i).setIs_copy(mMyWalletEntity.getData().getBusInfo().getIs_copy());
                    mMyWalletEntity.getData().getSubList().get(i).setIs_join(mMyWalletEntity.getData().getBusInfo().getIs_join());
                    mMyWalletEntity.getData().getSubList().get(i).setIs_click(mMyWalletEntity.getData().getBusInfo().getIs_click());
                }
                mlist.addAll(mMyWalletEntity.getData().getSubList());
            }
            mMerConsumeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updategetOrder(MZfbPayEntity mZfbPayEntity) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端消费明细页面");
        MobclickAgent.onPause(getActivity());
    }
}
