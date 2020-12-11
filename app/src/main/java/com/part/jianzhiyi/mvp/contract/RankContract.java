package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;

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
public interface RankContract {
    interface IRankModel extends IModel {
        Observable<ResponseData<JobListResponseEntity2>> jobList(String type, String position, int page, String label);

        Observable<ResponseData> getaddMd(String type);
    }

    interface IRankView extends IView {

        void updateRank(List<JobListResponseEntity2.DataBean> list);

        void updategetaddMd(ResponseData responseData);

    }
}
