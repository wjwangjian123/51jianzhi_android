package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.SearchListAdapter;
import com.part.jianzhiyi.adapter.SearchTagAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.SearchContract;
import com.part.jianzhiyi.mvp.presenter.SearchPresenter;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

@Route(path = "/app/activity/search")
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.ISearchView {

    private InputFilteEditText mIfetSearch;
    private LinearLayout mLlSearch;
    private TextView mTvCancel;
    private ConstraintLayout mLlDefault;
    private TagFlowLayout mTflSearch;
    private TextView mTvSearchCom;
    private ListViewInScrollView mLvSearch;
    private ImageView mIvNodata;
    private TextView mTvTitleOne;
    private TextView mTvPrice1One;
    private TextView mTvPrice2One;
    private TextView mTvSettlementOne;
    private TextView mTvTimeOne;
    private RelativeLayout mRlOne;
    private TextView mTvTitleTwo;
    private TextView mTvPrice1Two;
    private TextView mTvPrice2Two;
    private TextView mTvSettlementTwo;
    private TextView mTvTimeTwo;
    private RelativeLayout mRlTwo;
    private LinearLayout mLlHot;

    private SearchListAdapter adapter;
    private SearchTagAdapter mSearchTagAdapter;
    private List<LableEntity.DataBean> list;
    private List<SearchEntity.DataBean> jobList;
    private List<SearchEntity.DataBean> jobList1;
    private List<SearchEntity.DataBean> jobList2;
    private int type = 1;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getLable();
        mPresenter.search("", "");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mIfetSearch = (InputFilteEditText) findViewById(R.id.ifet_search);
        mLlSearch = (LinearLayout) findViewById(R.id.ll_search);
        mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mLlDefault = (ConstraintLayout) findViewById(R.id.ll_default);
        mTflSearch = (TagFlowLayout) findViewById(R.id.tfl_search);
        mTvSearchCom = (TextView) findViewById(R.id.tv_search_com);
        mLvSearch = (ListViewInScrollView) findViewById(R.id.lv_search);
        mIvNodata = (ImageView) findViewById(R.id.iv_nodata);
        mTvTitleOne = (TextView) findViewById(R.id.tv_title_one);
        mTvPrice1One = (TextView) findViewById(R.id.tv_price1_one);
        mTvPrice2One = (TextView) findViewById(R.id.tv_price2_one);
        mTvSettlementOne = (TextView) findViewById(R.id.tv_settlement_one);
        mTvTimeOne = (TextView) findViewById(R.id.tv_time_one);
        mRlOne = (RelativeLayout) findViewById(R.id.rl_one);
        mTvTitleTwo = (TextView) findViewById(R.id.tv_title_two);
        mTvPrice1Two = (TextView) findViewById(R.id.tv_price1_two);
        mTvPrice2Two = (TextView) findViewById(R.id.tv_price2_two);
        mTvSettlementTwo = (TextView) findViewById(R.id.tv_settlement_two);
        mTvTimeTwo = (TextView) findViewById(R.id.tv_time_two);
        mRlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        mLlHot = (LinearLayout) findViewById(R.id.ll_hot);
        setToolBarVisible(false);
        setImmerseLayout(mLlSearch);
        mIfetSearch.setEnableDefaultHeight(false);
        list = new ArrayList<>();
        jobList = new ArrayList<>();
        jobList1 = new ArrayList<>();
        jobList2 = new ArrayList<>();
    }

    @Override
    protected void initData() {
        mSearchTagAdapter = new SearchTagAdapter(list, SearchActivity.this);
        mTflSearch.setAdapter(mSearchTagAdapter);
        adapter = new SearchListAdapter(SearchActivity.this, jobList1);
        mLvSearch.setAdapter(adapter);
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mTflSearch.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                MobclickAgent.onEvent(SearchActivity.this, "search_lable");
                type = 2;
                mIfetSearch.setText(list.get(position).getTitle());
                mLlDefault.setVisibility(View.GONE);
                mTflSearch.setVisibility(View.GONE);
                mIvNodata.setVisibility(View.GONE);
                mPresenter.getaddMd("16");
                mPresenter.search("", list.get(position).getId());
                return true;
            }
        });
        mIfetSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = mIfetSearch.getText().toString().trim();
                if (trim.length() < 1) {
                    type = 1;
                    mLlDefault.setVisibility(View.VISIBLE);
                    mTflSearch.setVisibility(View.VISIBLE);
                    mTvSearchCom.setVisibility(View.VISIBLE);
                    mLvSearch.setVisibility(View.VISIBLE);
                    mLlHot.setVisibility(View.GONE);
                    mIvNodata.setVisibility(View.GONE);
                    adapter = new SearchListAdapter(SearchActivity.this, jobList1);
                    mLvSearch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        mIfetSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    type = 2;
                    mLlDefault.setVisibility(View.GONE);
                    mTflSearch.setVisibility(View.GONE);
                    mIvNodata.setVisibility(View.GONE);
                    MobclickAgent.onEvent(SearchActivity.this, "search_keyword");
                    mPresenter.getaddMd("15");
                    mPresenter.search(mIfetSearch.getText().toString(), "");
                }
                return false;
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.getaddMd("17");
                MobclickAgent.onEvent(SearchActivity.this, "search_position");
                if (type == 1 && jobList1.size() > 0) {
                    Intent intent = new Intent(SearchActivity.this, VocationActivity.class);
                    intent.putExtra("id", jobList1.get(position).getId());
                    intent.putExtra("position", Constants.POSITION_SERACH);
                    intent.putExtra("sortId", "" + position);
                    startActivity(intent);
                }
                if (type == 2 && jobList.size() > 0) {
                    Intent intent = new Intent(SearchActivity.this, VocationActivity.class);
                    intent.putExtra("id", jobList.get(position).getId());
                    intent.putExtra("position", Constants.POSITION_SERACH);
                    intent.putExtra("sortId", "" + position);
                    startActivity(intent);
                }
            }
        });
        mRlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 2 && jobList2.size() > 0) {
                    Intent intent = new Intent(SearchActivity.this, VocationActivity.class);
                    intent.putExtra("id", jobList2.get(0).getId());
                    intent.putExtra("position", Constants.POSITION_SERACH);
                    intent.putExtra("sortId", "" + 0);
                    startActivity(intent);
                }
            }
        });
        mRlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 2 && jobList2.size() > 0) {
                    Intent intent = new Intent(SearchActivity.this, VocationActivity.class);
                    intent.putExtra("id", jobList2.get(1).getId());
                    intent.putExtra("position", Constants.POSITION_SERACH);
                    intent.putExtra("sortId", "" + 1);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void updateList(List<JobListResponseEntity> list) {

    }

    @Override
    public void updategetLable(LableEntity lableEntity) {
        if (list != null) {
            this.list.clear();
        }
        if (lableEntity != null && lableEntity.getData().size() > 0) {
            list.addAll(lableEntity.getData());
            mSearchTagAdapter.notifyDataChanged();
        }
    }

    @Override
    public void updatesearch(SearchEntity searchEntity) {
        if (type == 1) {
            if (jobList1 != null) {
                this.jobList1.clear();
            }
            if (searchEntity != null && searchEntity.getData().size() > 0) {
                mLvSearch.setVisibility(View.VISIBLE);
                jobList1.addAll(searchEntity.getData());
            }
        } else if (type == 2) {
            if (jobList != null) {
                this.jobList.clear();
            }
            if (jobList2 != null) {
                this.jobList2.clear();
            }
            if (searchEntity.getData().size() > 0) {
                mLlHot.setVisibility(View.VISIBLE);
                mLvSearch.setVisibility(View.VISIBLE);
                mTvSearchCom.setVisibility(View.GONE);
                mIvNodata.setVisibility(View.GONE);

                mRlOne.setVisibility(View.VISIBLE);
                mTvTitleOne.setText(searchEntity.getData().get(0).getTitle());
                mTvPrice1One.setText(searchEntity.getData().get(0).getPrice1());
                mTvPrice2One.setText(searchEntity.getData().get(0).getPrice2());
                if (searchEntity.getData().get(0).getMethod() == null || searchEntity.getData().get(0).getMethod() == "") {
                    mTvSettlementOne.setText("不限");
                } else {
                    mTvSettlementOne.setText(searchEntity.getData().get(0).getMethod());
                }
                if (searchEntity.getData().get(0).getTime() == null || searchEntity.getData().get(0).getTime() == "") {
                    mTvTimeOne.setText("不限");
                } else {
                    mTvTimeOne.setText(searchEntity.getData().get(0).getTime());
                }
                jobList2.add(searchEntity.getData().get(0));
                if (searchEntity.getData().size() > 1) {
                    mLlHot.setBackgroundResource(R.drawable.icon_search_hot_bg);
                    mRlTwo.setVisibility(View.VISIBLE);
                    mTvTitleTwo.setText(searchEntity.getData().get(1).getTitle());
                    mTvPrice1Two.setText(searchEntity.getData().get(1).getPrice1());
                    mTvPrice2Two.setText(searchEntity.getData().get(1).getPrice2());
                    if (searchEntity.getData().get(1).getMethod() == null || searchEntity.getData().get(1).getMethod() == "") {
                        mTvSettlementTwo.setText("不限");
                    } else {
                        mTvSettlementTwo.setText(searchEntity.getData().get(1).getMethod());
                    }
                    if (searchEntity.getData().get(1).getTime() == null || searchEntity.getData().get(1).getTime() == "") {
                        mTvTimeTwo.setText("不限");
                    } else {
                        mTvTimeTwo.setText(searchEntity.getData().get(1).getTime());
                    }
                    jobList2.add(searchEntity.getData().get(1));
                } else {
                    mLlHot.setBackgroundResource(R.drawable.icon_search_hot_bg1);
                    mRlTwo.setVisibility(View.GONE);
                }
                for (int i = 0; i < searchEntity.getData().size(); i++) {
                    if (i > 1) {
                        jobList.add(searchEntity.getData().get(i));
                    }
                }
                adapter = new SearchListAdapter(SearchActivity.this, jobList);
                mLvSearch.setAdapter(adapter);
            } else {
                mLlHot.setVisibility(View.GONE);
                mLvSearch.setVisibility(View.VISIBLE);
                mTvSearchCom.setVisibility(View.VISIBLE);
                mIvNodata.setVisibility(View.VISIBLE);
                adapter = new SearchListAdapter(SearchActivity.this, jobList1);
                mLvSearch.setAdapter(adapter);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void requestError() {
        mLvSearch.setVisibility(View.GONE);
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("搜索页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("搜索页面");
        MobclickAgent.onPause(this);
    }
}
