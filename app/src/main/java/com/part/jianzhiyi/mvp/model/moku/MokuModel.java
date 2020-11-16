package com.part.jianzhiyi.mvp.model.moku;

import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuTaskListEntity;
import com.part.jianzhiyi.mvp.contract.moku.MokuContract;
import com.part.jianzhiyi.preference.PreferenceUUID;

import io.reactivex.Observable;


/**
 * @author:shixinxin
 * @content：内容
 **/
public class MokuModel implements MokuContract.IMokuModel {

    @Override
    public Observable<MokuTaskListEntity> getTaskList() {
        return HttpAPI.getInstence().getServiceApi().getTaskList(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    public Observable<ResponseData> getAddTask(String task_data_id, String user_id, String show_name, String icon, String show_money, String desc, String status) {
        return HttpAPI.getInstence().getServiceApi().getAddTask(task_data_id, PreferenceUUID.getInstence().getUserId(), show_name, icon, show_money, desc, status);
    }
}
