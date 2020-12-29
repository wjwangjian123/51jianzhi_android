package com.part.jianzhiyi.modulemerchants.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.ActionListener;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.BaseDialogFragment;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.TextPickerDialog;
import com.part.jianzhiyi.corecommon.selectdateview.view.TextModel;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.adapter.MerPublishedAdapter;
import com.part.jianzhiyi.modulemerchants.base.BaseFragment;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MBannerEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MHomeContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MHomePresenter;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerSelectPositionActivity;
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
public class MerPublishedFragment extends BaseFragment<MHomePresenter> implements MHomeContract.IMHomeView, View.OnClickListener {

    private TextView mTvStartTime;
    private TextView mTvEndTime;
    private ImageView mIvDate;
    private RecyclerView mRecycleview;
    private ImageView mIvEmpty;
    private List<MJobListEntity.DataBeanX.DataBean> mList;
    private MerPublishedAdapter mMerPublishedAdapter;
    private List<TextModel> mListStatus;
    private int mstatus;
    private String mid;

    @Override
    protected MHomePresenter createPresenter() {
        return new MHomePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端首页已发布页面");
        MobclickAgent.onResume(getActivity());
        if (mPresenter != null) {
            mPresenter.getMJobList("2", mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim());
        }
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if (mPresenter != null && mMerPublishedAdapter != null) {
//                mPresenter.getMJobList("2", mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim());
//            }
//        }
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mer_published;
    }

    private long clickTime = 0;
    private long clickTime1 = 0;

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
        mListStatus = new ArrayList<>();
        mListStatus.add(new TextModel("招聘中"));
        mListStatus.add(new TextModel("已下线"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecycleview.setLayoutManager(linearLayoutManager);
        mMerPublishedAdapter = new MerPublishedAdapter(getActivity());
        mRecycleview.setAdapter(mMerPublishedAdapter);
        mMerPublishedAdapter.setmOnRefreshClickListener(new MerPublishedAdapter.OnRefreshClickListener() {
            @Override
            public void onItemRefreshClick(int position, String id) {
                MobclickAgent.onEvent(getActivity(), "mer_published_refresh");
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //刷新
                    mPresenter.getJobRefresh(id);
                    mPresenter.getmdAdd("69");
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMerPublishedAdapter.setmOnItemClickListener(new MerPublishedAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                MobclickAgent.onEvent(getActivity(), "mer_published_edit");
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //编辑
                    mPresenter.getMJobInfo(id);
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMerPublishedAdapter.setOnSetStatusItemClickListener(new MerPublishedAdapter.OnSetStatusItemClickListener() {
            @Override
            public void onItemSetStatusClick(int position, String id, int status) {
                mstatus = status;
                mid = id;
                MobclickAgent.onEvent(getActivity(), "mer_published_status");
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(0));
                pickDialog.setList(mListStatus);
                pickDialog.show(getActivity().getFragmentManager(), "dialog");
            }
        });
    }

    private class MyAction implements ActionListener {

        private int position;

        public MyAction(int i) {
            this.position = i;
        }

        @Override
        public void onCancelClick(BaseDialogFragment dialog) {

        }

        @Override
        public void onDoneClick(BaseDialogFragment dialog) {
            String content = "";
            if (position == 0) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                if (content.equals("招聘中") && mstatus == 4) {
                    mPresenter.getJobSx(1, mid);
                }
                if (content.equals("已下线") && mstatus == 1) {
                    mPresenter.getJobSx(4, mid);
                }
            }
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mIvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击拉起时间选择器
                MobclickAgent.onEvent(getActivity(), "mer_published_date");
                initStartDate();
            }
        });
        MerPositionFragment merPositionFragment = new MerPositionFragment();
        merPositionFragment.setmOnPublishedClickListener(new MerPositionFragment.OnPublishedClickListener() {
            @Override
            public void OnPublishedClick() {
                mPresenter.getMJobList("2", "", "");
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
                mPresenter.getMJobList("2", mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim());
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

    public static String getDate(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(time);
        return format;
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
        if (responseData != null) {
            if (responseData.getCode().equals("200")) {
                mPresenter.getMJobList("2", mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim());
                initDialogAuthTip(responseData.getMsg());
            } else {
                showToast(responseData.getMsg());
            }
        }
    }

    @Override
    public void updategetJobSx(ResponseData responseData) {
        if (responseData != null) {
            showToast(responseData.getMsg());
            if (responseData.getCode().equals("200")) {
                mPresenter.getMJobList("2", mTvStartTime.getText().toString().trim(), mTvEndTime.getText().toString().trim());
            }
        }
    }

    @Override
    public void updategetMJobInfo(MJobInfoEntity mJobInfoEntity) {
        if (mJobInfoEntity != null) {
            Intent intent = new Intent(getActivity(), MerSelectPositionActivity.class);
            intent.putExtra("type", 1);
            intent.putExtra("mJobInfoEntity", mJobInfoEntity);
            intent.putExtra("mType", 0);
            startActivity(intent);
        }
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
                for (int i = 0; i < mJobListEntity.getData().getData().size(); i++) {
                    mJobListEntity.getData().getData().get(i).setIs_copy(mJobListEntity.getData().getBinfo().getIs_copy());
                    mJobListEntity.getData().getData().get(i).setIs_join(mJobListEntity.getData().getBinfo().getIs_join());
                    mJobListEntity.getData().getData().get(i).setIs_click(mJobListEntity.getData().getBinfo().getIs_click());
                }
                mList.addAll(mJobListEntity.getData().getData());
                mMerPublishedAdapter.setList(mList);
            } else {
                mRecycleview.setVisibility(View.GONE);
                mIvEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    private void initDialogAuthTip(String mtip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_tip_publish_auth, null, false);
        TextView text = inflate.findViewById(R.id.tv_text);
        TextView tip = inflate.findViewById(R.id.tv_tip);
        TextView auth = inflate.findViewById(R.id.tv_auth);
        ImageView cancel = inflate.findViewById(R.id.iv_cancel);
        alertDialog.setView(inflate);
        text.setText("刷新成功");
        auth.setText("好的");
        tip.setText(mtip);
        cancel.setVisibility(View.GONE);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端首页已发布页面");
        MobclickAgent.onPause(getActivity());
    }
}
