package com.part.jianzhiyi.mvp.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.HomeAdapter;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.integral.SignEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.HomeContract;
import com.part.jianzhiyi.mvp.presenter.HomePresenter;
import com.part.jianzhiyi.mvp.ui.activity.VocationActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * Created by jyx on 2021/1/5
 *
 * @author jyx
 * @describe
 */
public class HomeListFragment extends BaseFragment<HomePresenter> implements HomeContract.IHomeView {

    private ListViewInScrollView mListview;
    private List<JobListResponseEntity2.DataBean> mList;
    private HomeAdapter mHomeAdapter;
    private String mlable;
    private String mposition_recommend;

    public HomeListFragment(List<JobListResponseEntity2.DataBean> mDataBean, String lable, String position_recommend) {
        this.mList = mDataBean;
        this.mlable = lable;
        this.mposition_recommend = position_recommend;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mListview = view.findViewById(R.id.list_view);
        setToolbarVisible(false);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home_fragment;
    }

    @Override
    protected void afterCreate() {
        mHomeAdapter = new HomeAdapter(getActivity(), mList);
        mListview.setAdapter(mHomeAdapter);
        mHomeAdapter.notifyDataSetChanged();
        //兼职推荐
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).getUiType() == 1) {
                    return;
                }
                if (mlable.equals("0")) {
                    if (position == 0) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_one");
                        mPresenter.getaddMd("30");
                    }
                    if (position == 1) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_two");
                        mPresenter.getaddMd("31");
                    }
                    if (position == 2) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_three");
                        mPresenter.getaddMd("32");
                    }
                    if (position == 4) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_four");
                        mPresenter.getaddMd("33");
                    }
                    if (position == 5) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_five");
                        mPresenter.getaddMd("34");
                    }
                    if (position == 6) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_six");
                        mPresenter.getaddMd("35");
                    }
                    if (position == 7) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_seven");
                        mPresenter.getaddMd("36");
                    }
                    if (position == 8) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_eight");
                        mPresenter.getaddMd("37");
                    }
                    if (position == 9) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_nine");
                        mPresenter.getaddMd("38");
                    }
                    if (position == 10) {
                        MobclickAgent.onEvent(getActivity(), "home_recommend_ten");
                        mPresenter.getaddMd("39");
                    }
                }
                Intent intent = new Intent(getActivity(), VocationActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                intent.putExtra("position", mposition_recommend);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
    }

    public void notifyDataSetChanged() {
        if (mHomeAdapter != null) {
            mHomeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateNewList(String position, List<JobListResponseEntity2.DataBean> dataBeanList) {

    }

    @Override
    public void updateAdvertising(String postion, JobListResponseEntity2.AdvertisingBean bean) {

    }

    @Override
    public void updateBanner(List<BannerEntity> bannerEntityList) {

    }

    @Override
    public void updategetBannerUrl(ResponseData responseData) {

    }

    @Override
    public void updateCategory(List<CategoryEntity> categoryEntityList) {

    }

    @Override
    public void updatesearch(SearchEntity searchEntity) {

    }

    @Override
    public void updategetHomeLabel(LableEntity searchEntity) {

    }

    @Override
    public void updategetSignInfos(SignEntity signEntity) {

    }

    @Override
    public void updategetAddInteg(SignInfoEntity responseData) {

    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }
}
