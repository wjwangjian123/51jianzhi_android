package com.part.jianzhiyi.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
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


}
