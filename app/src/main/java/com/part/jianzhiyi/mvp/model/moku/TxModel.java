package com.part.jianzhiyi.mvp.model.moku;

import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.entity.moku.KuaibaoEntity;
import com.part.jianzhiyi.model.entity.moku.TxBindingEntity;
import com.part.jianzhiyi.model.entity.moku.TxInfoEntity;
import com.part.jianzhiyi.model.entity.moku.TxTypeEntity;
import com.part.jianzhiyi.model.entity.pay.AuthInfoEntity;
import com.part.jianzhiyi.mvp.contract.moku.TxContract;
import com.part.jianzhiyi.preference.PreferenceUUID;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class TxModel implements TxContract.ITxModel {

    @Override
    public Observable<TxTypeEntity> getTxtype() {
        return HttpAPI.getInstence().getServiceApi().getTxtype(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    public Observable<TxBindingEntity> getTxbinding(String code) {
        return HttpAPI.getInstence().getServiceApi().getTxbinding(PreferenceUUID.getInstence().getUserId(), code);
    }

    @Override
    public Observable<TxInfoEntity> getTxInfo() {
        return HttpAPI.getInstence().getServiceApi().getTxInfo(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    public Observable<TxBindingEntity> getTxcode(String user_id, String times, String token) {
        return HttpAPI.getInstence().getServiceApi().getTxcode(user_id, times, token);
    }

    @Override
    public Observable<TxBindingEntity> getTxapp(String code, String money) {
        return HttpAPI.getInstence().getServiceApi().getTxapp(PreferenceUUID.getInstence().getUserId(), code, money);
    }

    @Override
    public Observable<AuthInfoEntity> getAuthInfo() {
        return HttpAPI.getInstence().getServiceApi().getAuthInfo();
    }

    @Override
    public Observable<KuaibaoEntity> getTxkb() {
        return HttpAPI.getInstence().getServiceApi().getTxkb();
    }

}
