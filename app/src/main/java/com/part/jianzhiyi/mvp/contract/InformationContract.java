package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.ChatJobInfoEntity;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface InformationContract {
    interface IInformationModel extends IModel {
        Observable<ResponseData<List<MsgResponseEntity>>> msgList(String userid);
        Observable<ChatJobInfoEntity> getChatJobinfo(String id);
        Observable<ViewedEntity> viewedJob(String userid);
        Observable<ResponseData<AddFavouriteResponseEntity>> joincopyContact(String appid, String user_id, String job_id, String sortId, String contact, int type);
    }

    interface IInformationView extends IView {
        void updateList(List<MsgResponseEntity> list);
        void updategetChatJobinfo(ChatJobInfoEntity chatJobInfoEntity);
        void updateviewedJob(ViewedEntity list);
    }
}
