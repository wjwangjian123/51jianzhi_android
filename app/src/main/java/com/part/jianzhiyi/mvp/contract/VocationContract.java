package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.JobDetailEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.JoinJobEntity;
import com.part.jianzhiyi.model.entity.VocationEntity;
import com.part.jianzhiyi.mvp.model.user.IUserModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public interface VocationContract {
    interface IVocationModel extends IModel, IUserModel {
        Observable<ResponseData<AddFavouriteResponseEntity>> addFavourite(String userid, String job_id, String sortId);

        Observable<ResponseData<AddFavouriteResponseEntity>> joinJob(String userid, String jobid, String sortId, String contact);

        Observable<JoinJobEntity> joinJobV2(String userid, String jobid, String sortId, String contact);

        Observable<ResponseData<VocationEntity>> jobDetail(String jobId, String position, String sortId);

        Observable<ResponseData<AddFavouriteResponseEntity>> cancelFavourite(String userid, String job_id, String sortId);

        Observable<ResponseData<List<JobListResponseEntity>>> recommendList(String jobid);

        Observable<JobDetailEntity> jobDetailv(String jobId, String position, String sort_id);

        Observable<ResponseData> getSussOrErrLog(String type, String status);

        Observable<ResponseData<AddFavouriteResponseEntity>> joincopyContact(String appid, String user_id, String job_id, String sortId, String contact, int type);

        Observable<ResponseData> getAddIntegBrowse(String user_id, int type, String job_id);
    }

    interface IVocationView extends IView {
        void updateEntity(JobDetailEntity.DataBean.InfoBean entity);

        void updateJobList(List<JobDetailEntity.DataBean.JobListBean> job_list);

        void updateRecommend(List<JobDetailEntity.DataBean.LoveBean> list);

        void updateErrorTip(String msg);

        //        void advertising( VocationEntity.AdvertisingBean advertisingBean);
        void favouriteSuccess();

        void cancelFavoriteSuccess();

        void joinSuccess(JoinJobEntity joinJobEntity);

        void updategetAddIntegBrowse(ResponseData responseData);
    }
}
