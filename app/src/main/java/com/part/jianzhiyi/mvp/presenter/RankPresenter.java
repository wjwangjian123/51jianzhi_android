package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.mvp.contract.RankContract;
import com.part.jianzhiyi.mvp.model.RankModel;

import java.util.List;


/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class RankPresenter extends BasePresenter<RankContract.IRankModel, RankContract.IRankView> {

    public RankPresenter(RankContract.IRankView mView) {
        super(mView, new RankModel());
    }


    public void getRankList(String type, String position,String label) {
        mModel.jobList(type, position, 0,label)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<JobListResponseEntity2>>() {
                    @Override
                    public void onNext(ResponseData<JobListResponseEntity2> responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                List<JobListResponseEntity2.DataBean> dataBeans = responseData.getData().getData();
                                weakReferenceView.get().updateRank(dataBeans);
                            }
                        }
                    }
                }));
    }

}
