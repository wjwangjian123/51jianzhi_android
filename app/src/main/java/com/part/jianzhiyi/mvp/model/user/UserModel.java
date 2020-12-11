package com.part.jianzhiyi.mvp.model.user;

import com.greendao.gen.LoginResponseEntityDao;
import com.part.jianzhiyi.dbmodel.GreenDaoManager;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;


/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class UserModel implements IUserModel{
    @Override
    public boolean isUserLogin() {
        return PreferenceUUID.getInstence().isUserLogin();
    }

    @Override
    public LoginResponseEntity loadUserInfo() {
        LoginResponseEntityDao loginResponseEntityDao = GreenDaoManager.getInstance().getDaoSession().getLoginResponseEntityDao();
        if (loginResponseEntityDao == null) {
            return null;
        }
        return loginResponseEntityDao.queryBuilder().where(LoginResponseEntityDao.Properties.Id.eq(PreferenceUUID.getInstence().getUserId())).unique();
    }

    @Override
    public void insertOrUpdateDb(LoginResponseEntity entity) {
        GreenDaoManager.getInstance().getDaoSession().getLoginResponseEntityDao().insertOrReplace(entity);
    }


}
