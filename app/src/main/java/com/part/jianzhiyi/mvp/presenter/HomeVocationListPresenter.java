package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.mvp.contract.HomeVocationListContract;
import com.part.jianzhiyi.mvp.model.HomeVocationListModel;
import com.part.jianzhiyi.preference.PreferenceUUID;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class HomeVocationListPresenter extends BasePresenter<HomeVocationListContract.IVocationListModel,HomeVocationListContract.IVocationListView> {
    public HomeVocationListPresenter(HomeVocationListContract.IVocationListView mView) {
        super(mView, new HomeVocationListModel());
    }


    public void jobList1(String type,String position,int page,String category){
        mModel.jobList1(PreferenceUUID.getInstence().getUserId(),type,position,page,category)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<JobListResponseEntity2>>() {
                    @Override
                    public void onNext(ResponseData<JobListResponseEntity2> responseData) {
                        if(TextUtils.equals(responseData.getCode(),HttpAPI.REQUEST_SUCCESS)){
                            if(isAttach()){
                                JobListResponseEntity2 data = responseData.getData();
                                weakReferenceView.get().updateNewList(position,data.getData());
                            }
                        }
                    }
                }));
    }

    public void jobList(String type,String position,int page,String label){
        mModel.jobList(PreferenceUUID.getInstence().getUserId(),type,position,page,label)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<JobListResponseEntity2>>() {
                    @Override
                    public void onNext(ResponseData<JobListResponseEntity2> responseData) {
                        if(TextUtils.equals(responseData.getCode(),HttpAPI.REQUEST_SUCCESS)){
                            if(isAttach()){
                                JobListResponseEntity2 data = responseData.getData();
                                weakReferenceView.get().updateNewList(position,data.getData());
                            }
                        }
                    }
                }));
    }

}
