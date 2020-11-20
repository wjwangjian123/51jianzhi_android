package com.part.jianzhiyi.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fendasz.moku.planet.source.bean.ClientSampleTaskData;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.mogu.adapter.TaskListToAdapter;
import com.part.jianzhiyi.mvp.ui.activity.TaskDetailActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
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
public class ToCompletedFragment extends Fragment {

    private RecyclerView mTocompleteRecycle;
    private ImageView mTocompleteIvEmpty;
    private List<ClientSampleTaskData> mTaskDataList;
    private String mUserId;
    private TaskListToAdapter mTaskAdapter;
    private Context mContext;

    public ToCompletedFragment(List<ClientSampleTaskData> taskDataList, String userId) {
        mTaskDataList = taskDataList;
        mUserId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tocompleted, container, false);
        mTocompleteRecycle = view.findViewById(R.id.tocomplete_recycle);
        mTocompleteIvEmpty = view.findViewById(R.id.tocomplete_iv_empty);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mContext = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mTocompleteRecycle.setLayoutManager(linearLayoutManager);
        mTaskAdapter = new TaskListToAdapter(getActivity());
        mTocompleteRecycle.setAdapter(mTaskAdapter);
        mTaskAdapter.setmOnItemClickListener(new TaskListToAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, Integer id) {
                MobclickAgent.onEvent(getActivity(), "moku_tasklist");
                gotoDetail(id);
            }
        });
        if (mTaskDataList != null) {
            mTocompleteRecycle.setVisibility(View.VISIBLE);
            mTocompleteIvEmpty.setVisibility(View.GONE);
            mTaskAdapter.setList(mTaskDataList);
        } else {
            mTocompleteRecycle.setVisibility(View.GONE);
            mTocompleteIvEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 查看详情
     *
     * @param
     */
    private void gotoDetail(int taskDataId) {
        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
        intent.putExtra("taskDataId", taskDataId);
        intent.putExtra("userId", mUserId);
        this.startActivity(intent);
    }

    void notifyDataSetChanged() {
        if (mTaskAdapter != null) {
            if (mTaskDataList.size() == 0) {
                mTocompleteRecycle.setVisibility(View.GONE);
                mTocompleteIvEmpty.setVisibility(View.VISIBLE);
            } else {
                mTocompleteRecycle.setVisibility(View.VISIBLE);
                mTocompleteIvEmpty.setVisibility(View.GONE);
                mTaskAdapter.setList(mTaskDataList);
            }
        }
    }
}
