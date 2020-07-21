package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.ViewedEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface ChoiceContract {
    interface IChoiceModel extends IModel {
        Observable<ResponseData<JobListResponseEntity2>> jobList(String userid, String type, String position, int page);

        Observable<ViewedEntity> viewedJob(String userid);

        Observable<ResponseData<List<JobListResponseEntity>>> inviteJob(String userid);

        Observable<ResponseData<List<JobListResponseEntity>>> joinedJob(String userid);

        Observable<ResponseData<List<JobListResponseEntity>>> arrivedJob(String userid);

        Observable<ResponseData<List<JobListResponseEntity>>> approvedJob(String userid);

        Observable<ResponseData<List<JobListResponseEntity>>> donedJob(String userid);
    }

    interface IChoiceView extends IView {
        void updateList(List<JobListResponseEntity> list);

        void updateviewedJob(ViewedEntity list);

        default void updateNewList(String position, List<JobListResponseEntity2.DataBean> dataBeanList) {
        }


        default void updateAdvertising(String postion, JobListResponseEntity2.AdvertisingBean bean) {
        }

    }

}
