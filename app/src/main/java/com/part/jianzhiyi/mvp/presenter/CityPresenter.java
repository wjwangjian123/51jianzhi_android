package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.mvp.contract.CityContract;
import com.part.jianzhiyi.mvp.model.CityModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class CityPresenter extends BasePresenter<CityContract.ICityModel, CityContract.ICityView> {
    public CityPresenter(CityContract.ICityView mView) {
        super(mView, new CityModel());
    }

    public void getCity() {
        mModel.getCity()
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<String>() {
                    @Override
                    public void onNext(String json) {
                        if (isAttach()) {
                            String data = JSON.parseObject(json).getString("data");
                            weakReferenceView.get().updateCity(data);
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
