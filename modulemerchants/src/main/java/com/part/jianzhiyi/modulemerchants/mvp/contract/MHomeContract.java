package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MBannerEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;

import io.reactivex.Observable;

public interface MHomeContract {

    interface IMHomeModel extends IModel {
        Observable<MBannerEntity> getIndexBanner();

        Observable<ResponseData> getJobRefresh(String job_id);

        Observable<ResponseData> getJobSx(int status, String job_id);

        Observable<MJobInfoEntity> getMJobInfo(String id);

        Observable<MJobListEntity> getMJobList(String type, String start_time, String end_time);

        Observable<ResponseData> getmdAdd(String type);

    }

    interface IMHomeView extends IView {
        void updategetIndexBanner(MBannerEntity mBannerEntity);

        void updategetJobRefresh(ResponseData responseData);

        void updategetJobSx(ResponseData responseData);

        void updategetMJobInfo(MJobInfoEntity mJobInfoEntity);

        void updategetMJobList(MJobListEntity mJobListEntity);

        void updategetmdAdd(ResponseData responseData);
    }
}
