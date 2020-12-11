package com.part.jianzhiyi.mvp.contract.mine;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;

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
public class MineFavouriteContract {
    public interface IMineFavouriteModel extends IModel {
        Observable<ResponseData<AddFavouriteResponseEntity>> addFavourite(String userid, String job_id, String sortId);

        Observable<ResponseData<AddFavouriteResponseEntity>> cancelFavourite(String userid, String job_id, String sortId);

        Observable<ResponseData<List<JobListResponseEntity>>> favourite(String userid);

        Observable<ResponseData> getaddMd(String type);
    }

    public interface IMineFavouriteView extends IView {
        void updateSuccess();

        void updateList(List<JobListResponseEntity> list);

        void updategetaddMd(ResponseData responseData);
    }
}
