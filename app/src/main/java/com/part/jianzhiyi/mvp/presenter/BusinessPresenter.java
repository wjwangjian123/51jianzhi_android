package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.mvp.contract.BusinessContract;
import com.part.jianzhiyi.mvp.model.BusinessModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class BusinessPresenter extends BasePresenter<BusinessContract.IBusinessModel,BusinessContract.IBusinessView> {
    public BusinessPresenter(BusinessContract.IBusinessView mView) {
        super(mView, new BusinessModel());
    }

    public void getTitle(){
        mModel.getTitle(Constants.APPID)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> jobListResponseEntityResponseData) {
                        if(TextUtils.equals(jobListResponseEntityResponseData.getCode(),HttpAPI.REQUEST_SUCCESS)){
                            if(isAttach()){
                                weakReferenceView.get().updateContract(jobListResponseEntityResponseData.getData().getContact());
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
