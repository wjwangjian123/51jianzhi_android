package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.ActionListAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.mvp.contract.MainContract;
import com.part.jianzhiyi.mvp.presenter.MainPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class ActionListActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView {

    private ImageView mActionIvReturn;
    private ListView mLvAction;
    private ImageView mActionImg;
    private ActionListAdapter mActionListAdapter;
    private List<ActJobListEntity.DataBeanX.DataBean> mList;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_action_list;
    }

    @Override
    protected void initView() {
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_action));
        mActionIvReturn = (ImageView) findViewById(R.id.action_iv_return);
        mActionImg = (ImageView) findViewById(R.id.action_img);
        mLvAction = (ListView) findViewById(R.id.lv_action);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String type = intent.getStringExtra("type");
        mPresenter.getActJobList(id, type);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mActionListAdapter = new ActionListAdapter(ActionListActivity.this, mList);
        mLvAction.setAdapter(mActionListAdapter);
        mLvAction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(ActionListActivity.this, "home_action_list");
                Intent intent = new Intent(ActionListActivity.this, VocationActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                intent.putExtra("position", Constants.POSITION_SERACH);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        mActionIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetAction(ActivityEntity activityEntity) {

    }

    @Override
    public void updategetActJobList(ActJobListEntity actJobListEntity) {
        if (mList != null) {
            mList.clear();
        }
        if (actJobListEntity.getData().getInfo() != null) {
            //磁盘不缓存 跳过内存缓存
            Glide.with(ActionListActivity.this).load(actJobListEntity.getData().getInfo().getBackimg()).error(R.drawable.shape_action_list_bg).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(mActionImg);
        } else {
            mActionImg.setImageResource(R.drawable.shape_action_list_bg);
        }
        if (actJobListEntity.getData().getData().size() > 0) {
            mList.addAll(actJobListEntity.getData().getData());
            mActionListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updategetConfig(ConfigEntity configEntity) {

    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("首页活动列表");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("首页活动列表");
        MobclickAgent.onPause(this);
    }
}

