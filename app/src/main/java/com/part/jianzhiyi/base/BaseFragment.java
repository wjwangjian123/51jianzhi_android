package com.part.jianzhiyi.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.corecommon.ui.CustomToolbarView;
import com.part.jianzhiyi.corecommon.utils.toast.CustomToast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/22
 * @modified by:shixinxin
 **/
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView {

    protected P mPresenter;


    protected BaseActivity mActivity;
    CustomToolbarView fragmentContainer;
    protected View fragmentView;


    /*
     * 获取当前的Presenter
     * @return Presenter对象
     */
    protected abstract P createPresenter();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_main_base, container, false);
        fragmentContainer = inflate.findViewById(R.id.custom_toolbar);
        mActivity.setImmerseLayout(fragmentContainer);
        fragmentView = LayoutInflater.from(mActivity).inflate(getLayoutId(), null);
        ((FrameLayout) inflate.findViewById(R.id.fl_activity_child_container)).addView(fragmentView);
        mPresenter = createPresenter();
        initView(inflate);
        return inflate;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initView();
        afterCreate();
        setListener();
    }

    /*
       设置layoutId
    */
    protected abstract int getLayoutId();


    protected void init() {

    }

    /**
     * 当前的onViewCreated
     */
    protected abstract void afterCreate();

    protected void setListener() {

    }

    protected void initView() {

    }

    protected void initView(View view) {

    }

    protected String TAG = this.getClass().getCanonicalName();


    @Override
    public void showLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }


    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showToast(String msg) {
        CustomToast.normalToast(msg);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }


    protected void setToolbarVisible(boolean isVisible) {
        fragmentContainer.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    protected void initTitle(String title) {
        fragmentContainer.setTitle(title);
    }

    protected void initToolbarWithRight(String title, int resId, CustomToolbarView.OnRightToolbarListener onRightToolbarListener) {
        fragmentContainer.setTitle(title);
        fragmentContainer.setRightImage(resId);
        fragmentContainer.setOnRightToolbarListener(onRightToolbarListener);
    }
}
