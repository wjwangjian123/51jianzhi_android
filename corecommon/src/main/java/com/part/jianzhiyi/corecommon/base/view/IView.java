package com.part.jianzhiyi.corecommon.base.view;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/26
 * @modified by:shixinxin
 **/
public interface IView {

    default void showMessage(String msg) {
    }

    ;

    void showToast(String msg);

    /**
     * 显示加载
     */
    default void showLoading() {

    }

    /**
     * 隐藏加载
     */
    default void hideLoading() {

    }

    /**
     * 暂无数据
     */
    default void noData() {

    }

    ;

    void startIntent();

    /**
     * 重新登陆
     */
    default void reStartLogin() {
    }

    ;


    default void requestError() {
    }

    ;
}
