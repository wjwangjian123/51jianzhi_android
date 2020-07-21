package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/12
 * @modified by:shixinxin
 **/
public interface JoinSuccessContract {
    interface IJoinSuccessModel extends IModel {
        Observable<SearchEntity> search(String title, String lable);

        Observable<ResponseData<AddFavouriteResponseEntity>> joincopyContact(String appid, String user_id, String job_id, String sortId, String contact, int type);
    }

    interface IJoinSuccessView extends IView {
        void updatesearch(SearchEntity searchEntity);
    }
}
