package com.part.jianzhiyi.mvp.contract.integral;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.integral.ExcitationInfoEntity;
import com.part.jianzhiyi.model.entity.integral.MyIntegralEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface IntegralContract {
    interface IntegralModel extends IModel {

        Observable<MyIntegralEntity> getMyIntegInfo(String user_id, int page);

        Observable<ExcitationInfoEntity> getExcitationInfo(String user_id);

    }

    interface IntegralView extends IView {

        void updategetMyIntegInfo(MyIntegralEntity entity);

        void updategetExcitationInfo(ExcitationInfoEntity entity);

    }
}
