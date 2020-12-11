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
 * @vision:V1.0.1
 **/
public interface HomeVocationListContract {
    interface IVocationListModel extends IModel {
        Observable<ResponseData<JobListResponseEntity2>> jobList1(String userid, String type, String position, int page, String category);

        Observable<ResponseData<JobListResponseEntity2>> jobList(String userid, String type, String position, int page, String label);

        Observable<ResponseData> getaddMd(String type);
    }

    interface IVocationListView extends IView {
        void updateNewList(String position, List<JobListResponseEntity2.DataBean> dataBeanList);

        void updategetaddMd(ResponseData responseData);
    }
}
