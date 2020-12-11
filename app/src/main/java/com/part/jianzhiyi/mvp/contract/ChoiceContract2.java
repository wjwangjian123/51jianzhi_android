package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ChoiceEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface ChoiceContract2 {
    interface IChoiceModel extends IModel {
        Observable<ResponseData<ChoiceEntity>> getChoice();

        Observable<ResponseData> getaddMd(String type);
    }

    interface IChoiceView extends IView {
        void updateRecommendList(List<ChoiceEntity.XiaobianBean> list);

        void updateRankList(List<ChoiceEntity.WeeklyBean> weeklyList);

        void updateChoiceList(List<ChoiceEntity.ChoiceBean> choiceList);

        void updateAdvertising(ChoiceEntity.AdvertisingBean advertisingBean);

        void updategetaddMd(ResponseData responseData);

    }

}
