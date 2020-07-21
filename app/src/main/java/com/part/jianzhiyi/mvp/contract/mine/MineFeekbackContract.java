package com.part.jianzhiyi.mvp.contract.mine;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.mvp.model.user.IUserModel;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public interface MineFeekbackContract {
    interface IMineFeekbackModel extends IModel, IUserModel {
        Observable<ResponseData<AddFavouriteResponseEntity>> jobfeedback(String userid, String content, String contact);
    }

    interface IMineFeekbackView extends IView {
        void updateSuccess();
    }


}
