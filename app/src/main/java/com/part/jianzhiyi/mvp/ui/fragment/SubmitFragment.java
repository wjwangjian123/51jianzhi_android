package com.part.jianzhiyi.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.moku.MokuTaskListEntity;
import com.part.jianzhiyi.mogu.adapter.TaskListSubAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/11/10
 *
 * @author jyx
 * @describe
 */
public class SubmitFragment extends Fragment {

    private RecyclerView mSubmitRecycle;
    private ImageView mSubmitIvEmpty;
    private List<MokuTaskListEntity.DataBean> mTaskInReviewDataList;
    private TaskListSubAdapter mTaskListSubAdapter;
    private Context mContext;

    public SubmitFragment(List<MokuTaskListEntity.DataBean> taskInReviewDataList) {
        mTaskInReviewDataList = taskInReviewDataList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit, container, false);
        mSubmitRecycle = view.findViewById(R.id.submit_recycle);
        mSubmitIvEmpty = view.findViewById(R.id.submit_iv_empty);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mContext = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mSubmitRecycle.setLayoutManager(linearLayoutManager);
        mTaskListSubAdapter = new TaskListSubAdapter(getActivity());
        mSubmitRecycle.setAdapter(mTaskListSubAdapter);
        mTaskListSubAdapter.setmOnItemClickListener(new TaskListSubAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String reason) {
                Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
            }
        });
        mTaskListSubAdapter.setOnMonitorItemClickListener(new TaskListSubAdapter.OnMonitorItemClickListener() {
            @Override
            public void onMonitorItemClick(int position) {
                MobclickAgent.onEvent(getActivity(), "moku_completed_list");
            }
        });
        if (mTaskInReviewDataList.size() > 0) {
            mSubmitRecycle.setVisibility(View.VISIBLE);
            mSubmitIvEmpty.setVisibility(View.GONE);
            mTaskListSubAdapter.setList(mTaskInReviewDataList);
        } else {
            mSubmitRecycle.setVisibility(View.GONE);
            mSubmitIvEmpty.setVisibility(View.VISIBLE);
        }
    }

    void notifyDataSetChanged() {
        if (mTaskListSubAdapter != null) {
            if (mTaskInReviewDataList.size() == 0) {
                mSubmitRecycle.setVisibility(View.GONE);
                mSubmitIvEmpty.setVisibility(View.VISIBLE);
            } else {
                mSubmitRecycle.setVisibility(View.VISIBLE);
                mSubmitIvEmpty.setVisibility(View.GONE);
                mTaskListSubAdapter.setList(mTaskInReviewDataList);
            }
        }
    }
}
