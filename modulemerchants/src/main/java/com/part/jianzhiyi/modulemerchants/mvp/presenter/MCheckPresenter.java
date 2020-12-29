package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MCheckContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.MCheckModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MCheckPresenter extends BasePresenter<MCheckContract.IMCheckModel, MCheckContract.IMCheckView> {
    public MCheckPresenter(MCheckContract.IMCheckView mView) {
        super(mView, new MCheckModel());
    }

    public void getCheck() {
        mModel.getCheck()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MCheckVersionEntity>() {
                    @Override
                    public void onNext(MCheckVersionEntity mCheckVersionEntity) {
                        if (TextUtils.equals(mCheckVersionEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCheck(mCheckVersionEntity);
                            }
                        }
                    }
                }));
    }
}
