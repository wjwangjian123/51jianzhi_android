package com.part.jianzhiyi.mvp.contract.moku;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuTaskListEntity;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface MokuContract {
    interface IMokuModel extends IModel {
        Observable<MokuTaskListEntity> getTaskList();

        Observable<ResponseData> getAddTask(String task_data_id, String user_id, String show_name, String icon, String show_money, String desc, String status);
    }

    interface IMokuView extends IView {
        void updategetTaskList(MokuTaskListEntity responseData);

        void updategetAddTask(ResponseData responseData, int statusid);

    }
}
