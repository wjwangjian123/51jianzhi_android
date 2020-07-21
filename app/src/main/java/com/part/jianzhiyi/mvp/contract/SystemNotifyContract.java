package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface SystemNotifyContract {
    interface ISystemNotifyModel extends IModel {
        Observable<ResponseData<List<MsgResponseEntity>>> msgList(String userid);

    }

    interface ISystemNotifyView extends IView {

        void updateList(List<MsgResponseEntity> list);
    }
}
