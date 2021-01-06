package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCompanyInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MCompanyContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.MCompanyModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MCompanyPresenter extends BasePresenter<MCompanyContract.IMCompanyModel, MCompanyContract.IMCompanyView> {
    public MCompanyPresenter(MCompanyContract.IMCompanyView mView) {
        super(mView, new MCompanyModel());
    }

    public void getCompanyInfo(String uid, String job_id) {
        mModel.getCompanyInfo(uid, job_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MCompanyInfoEntity>() {
                    @Override
                    public void onNext(MCompanyInfoEntity mCompanyInfoEntity) {
                        if (TextUtils.equals(mCompanyInfoEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCompanyInfo(mCompanyInfoEntity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCompanyInfo(mCompanyInfoEntity);
                            }
                        }
                    }
                }));
    }

    public void getIntroduced(String company_desc) {
        mModel.getIntroduced(company_desc)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetIntroduced(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetIntroduced(responseData);
                            }
                        }
                    }
                }));
    }
}
