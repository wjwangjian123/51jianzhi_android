package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.JoinSuccessContract;
import com.part.jianzhiyi.mvp.model.JoinSuccessModel;
import com.part.jianzhiyi.preference.PreferenceUUID;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class JoinSuccessPresenter extends BasePresenter<JoinSuccessContract.IJoinSuccessModel, JoinSuccessContract.IJoinSuccessView> {

    public JoinSuccessPresenter(JoinSuccessContract.IJoinSuccessView mView) {
        super(mView, new JoinSuccessModel());
    }

    public void search(String title,String lable){
        mModel.search(title, lable)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<SearchEntity>() {
                    @Override
                    public void onNext(SearchEntity searchEntity) {
                        if(TextUtils.equals(searchEntity.getCode(), HttpAPI.REQUEST_SUCCESS)){
                            if(isAttach()){
                                weakReferenceView.get().updatesearch(searchEntity);
                            }
                        }
                    }
                }));
    }

    public void joincopyContact(String jobId, String sortId, String contact,int type) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.joincopyContact(Constants.APPID, userId, jobId, sortId, contact,type)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
//                            weakReferenceView.get().joinSuccess();
                        } else {
                        }
                    }
                }));
    }
}
