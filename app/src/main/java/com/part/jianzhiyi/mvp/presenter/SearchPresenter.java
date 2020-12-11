package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.SearchContract;
import com.part.jianzhiyi.mvp.model.SearchModel;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;

import java.util.List;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class SearchPresenter extends BasePresenter<SearchContract.ISearchModel, SearchContract.ISearchView> {

    public SearchPresenter(SearchContract.ISearchView mView) {
        super(mView, new SearchModel());
    }

    public void jobSearch(String key) {
        mModel.jobSearch(PreferenceUUID.getInstence().getUserId(), key)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<JobListResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<JobListResponseEntity>> jobListResponseEntityResponseData) {
                        if (TextUtils.equals(jobListResponseEntityResponseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateList(jobListResponseEntityResponseData.getData());
                            }
                        }
                    }

                }));
    }

    public void getLable() {
        mModel.getLable()
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<LableEntity>() {
                    @Override
                    public void onNext(LableEntity lableEntity) {
                        if (TextUtils.equals(lableEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetLable(lableEntity);
                            }
                        }
                    }

                }));
    }

    public void search(String title, String lable) {
        mModel.search(title, lable)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<SearchEntity>() {
                    @Override
                    public void onNext(SearchEntity searchEntity) {
                        if (TextUtils.equals(searchEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updatesearch(searchEntity);
                            }
                        }
                    }
                }));
    }

    public void getaddMd(String type) {
        mModel.getaddMd(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetaddMd(responseData);
                            }
                        }
                    }
                }));
    }
}
