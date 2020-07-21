package com.part.jianzhiyi.mvp.model.user;

import com.part.jianzhiyi.model.entity.LoginResponseEntity;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public interface IUserModel {
    boolean isUserLogin();

    LoginResponseEntity loadUserInfo();

    void insertOrUpdateDb(LoginResponseEntity entity);
}
