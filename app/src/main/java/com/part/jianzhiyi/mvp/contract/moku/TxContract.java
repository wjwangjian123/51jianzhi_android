package com.part.jianzhiyi.mvp.contract.moku;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.KuaibaoEntity;
import com.part.jianzhiyi.model.entity.moku.TxBindingEntity;
import com.part.jianzhiyi.model.entity.moku.TxInfoEntity;
import com.part.jianzhiyi.model.entity.moku.TxTypeEntity;
import com.part.jianzhiyi.model.entity.pay.AuthInfoEntity;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/12
 * @modified by:shixinxin
 **/
public interface TxContract {
    interface ITxModel extends IModel {
        Observable<TxTypeEntity> getTxtype();

        Observable<TxBindingEntity> getTxbinding(String code);

        Observable<TxInfoEntity> getTxInfo();

        Observable<TxBindingEntity> getTxcode(String user_id, String times, String token);

        Observable<TxBindingEntity> getTxapp(String code, String money);

        Observable<AuthInfoEntity> getAuthInfo();

        Observable<KuaibaoEntity> getTxkb();

        Observable<ResponseData> getaddMd(String type);
    }

    interface ITxView extends IView {
        void updategetTxtype(TxTypeEntity txTypeEntity);

        void updategetTxbinding(TxBindingEntity txBindingEntity);

        void updategetTxInfo(TxInfoEntity txInfoEntity);

        void updategetTxcode(TxBindingEntity txBindingEntity);

        void updategetTxapp(TxBindingEntity txBindingEntity);

        void updategetAuthInfo(AuthInfoEntity authInfoEntity);

        void updategetTxkb(KuaibaoEntity responseData);

        void updategetaddMd(ResponseData responseData);
    }
}
