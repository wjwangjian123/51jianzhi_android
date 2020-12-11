package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.SearchListAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.SearchContract;
import com.part.jianzhiyi.mvp.presenter.SearchPresenter;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

@Route(path = "/app/activity/search")
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.ISearchView {

    private ImageView mIvBack;
    private InputFilteEditText mIfetSearch;
    private LinearLayout mLlSearch;
    private ImageView mIvSearch;
    private TextView mTvSearch;
    private ConstraintLayout mLlDefault;
    private TagFlowLayout mTflSearch;
    private TextView mTvSearchCom;
    private ListView mLvSearch;
    private ImageView mIvNodata;
    private List<LableEntity.DataBean> list;
    private SearchListAdapter adapter;
    private List<SearchEntity.DataBean> jobList;
    private List<SearchEntity.DataBean> jobList1;
    private int type = 1;


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getLable();
        mPresenter.search("","");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIfetSearch = (InputFilteEditText) findViewById(R.id.ifet_search);
        mLlSearch = (LinearLayout) findViewById(R.id.ll_search);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mLlDefault = (ConstraintLayout) findViewById(R.id.ll_default);
        mTflSearch = (TagFlowLayout) findViewById(R.id.tfl_search);
        mTvSearchCom = (TextView) findViewById(R.id.tv_search_com);
        mLvSearch = (ListView) findViewById(R.id.lv_search);
        mIvNodata = (ImageView) findViewById(R.id.iv_nodata);
        setToolBarVisible(false);
        setImmerseLayout(mLlSearch);
        mIfetSearch.setEnableDefaultHeight(false);
        list=new ArrayList<>();
        jobList=new ArrayList<>();
        jobList1=new ArrayList<>();
    }

    @Override
    protected void initData() {

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
                mIfetSearch.setText(list.get(position).getTitle());
                mLlDefault.setVisibility(View.GONE);
                mTflSearch.setVisibility(View.GONE);
                mTvSearchCom.setVisibility(View.GONE);
                mIvNodata.setVisibility(View.GONE);
                type=2;
                mPresenter.getaddMd("16");
                mPresenter.search("",list.get(position).getId());
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
                if (trim.length()<1){
                    mLlDefault.setVisibility(View.VISIBLE);
                    mTflSearch.setVisibility(View.VISIBLE);
                    mTvSearchCom.setVisibility(View.VISIBLE);
                    mIvNodata.setVisibility(View.GONE);
                    mLvSearch.setVisibility(View.VISIBLE);
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
                    mLlDefault.setVisibility(View.GONE);
                    mTflSearch.setVisibility(View.GONE);
                    mTvSearchCom.setVisibility(View.GONE);
                    mIvNodata.setVisibility(View.GONE);
                    type=2;
                    MobclickAgent.onEvent(SearchActivity.this, "search_keyword");
                    mPresenter.getaddMd("15");
                    mPresenter.search(mIfetSearch.getText().toString(),"");
                }
                return false;
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(SearchActivity.this, VocationActivity.class);
                intent.putExtra("id", jobList.get(position).getId());
                intent.putExtra("position", Constants.POSITION_SERACH);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void updateList(List<JobListResponseEntity> list) {

    }

    @Override
    public void updategetLable(LableEntity lableEntity) {
        if (list!=null){
            this.list.clear();
        }
        if (lableEntity != null) {
            list.addAll(lableEntity.getData());
            mTflSearch.setAdapter(new TagAdapter<LableEntity.DataBean>(list) {
                @Override
                public View getView(FlowLayout parent, int position, LableEntity.DataBean databean) {
                    TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_flow, ((TagFlowLayout)mTflSearch), false);
                    if (lableEntity.getData().get(position).getStatus().equals("1")){
                        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_search_hot,0,0,0);
                    }else{
                        tv.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    }
                    tv.setText(databean.getTitle());
                    return tv;
                }
            });
        }
    }

    @Override
    public void updatesearch(SearchEntity searchEntity) {
        if (type==1){
            if (jobList1!=null){
                this.jobList1.clear();
            }
            if (searchEntity != null) {
                jobList1.addAll(searchEntity.getData());
            }
        }
        //2.0搜索接口
        if (jobList!=null){
            this.jobList.clear();
        }
        if (searchEntity.getData().size() >0) {
            mLvSearch.setVisibility(View.VISIBLE);
            mIvNodata.setVisibility(View.GONE);
            jobList.addAll(searchEntity.getData());
        }else{
            mLvSearch.setVisibility(View.GONE);
            mIvNodata.setVisibility(View.VISIBLE);
        }
        adapter = new SearchListAdapter(SearchActivity.this, jobList);
        mLvSearch.setAdapter(adapter);
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
