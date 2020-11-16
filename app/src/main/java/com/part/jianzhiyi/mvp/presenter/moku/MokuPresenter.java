package com.part.jianzhiyi.mvp.presenter.moku;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuTaskListEntity;
import com.part.jianzhiyi.mvp.contract.moku.MokuContract;
import com.part.jianzhiyi.mvp.model.moku.MokuModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MokuPresenter extends BasePresenter<MokuContract.IMokuModel, MokuContract.IMokuView> {
    public MokuPresenter(MokuContract.IMokuView mView) {
        super(mView, new MokuModel());
    }

    public void getTaskList() {
        mModel.getTaskList()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MokuTaskListEntity>() {
                    @Override
                    public void onNext(MokuTaskListEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTaskList(responseData);
                            }
                        }
                    }
                }));
    }

    public void getAddTask(String task_data_id, String user_id, String show_name, String icon, String show_money, String desc, String status, int statusid) {
        mModel.getAddTask(task_data_id, user_id, show_name, icon, show_money, desc, status)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAddTask(responseData, statusid);
                            }
                        }
                    }
                }));
    }
}
