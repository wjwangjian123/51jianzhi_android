package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.FavouriteAdapter;
import com.part.jianzhiyi.adapter.OnCancelClickListener;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.customview.LoadDataTipView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineFavouriteContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineFavouritePresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class MineFavouriteActivity extends BaseActivity<MineFavouritePresenter> implements MineFavouriteContract.IMineFavouriteView {

    private List<JobListResponseEntity> list;
    private FavouriteAdapter adapter;
    private ListView mListView;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_favourite;
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.list_view);
        initToolbar("我的收藏");
        list=new ArrayList<>();
        adapter = new FavouriteAdapter(this, list);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mPresenter.favourite();
    }

    @Override
    protected MineFavouritePresenter createPresenter() {
        return new MineFavouritePresenter(this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnCancelClickListener(new OnCancelClickListener() {
            @Override
            public void onItemCancel(int position) {
                mPresenter.cancelFavourite(list.get(position).getId(), "" + position);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MineFavouriteActivity.this, VocationActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("position", "8");
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void updateSuccess() {
        mPresenter.favourite();
    }

    @Override
    public void updateList(List<JobListResponseEntity> list) {
        if (list == null || list.isEmpty()) {
            setLoadMode(LoadDataTipView.MODE.NODATA);
        } else {
            setLoadHidden();
            this.list.clear();
            this.list.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的收藏");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的收藏");
        MobclickAgent.onPause(this);
    }
}
