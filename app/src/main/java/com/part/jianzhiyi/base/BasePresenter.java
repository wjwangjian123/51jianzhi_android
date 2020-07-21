package com.part.jianzhiyi.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.corecommon.utils.toast.CustomToast;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.lang.ref.WeakReference;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author wavewave
 * @CreateDate: 2019/3/13 3:44 PM
 * @Description: base 控制器
 * @Version: 1.0
 */
public abstract class BasePresenter<M extends IModel, V extends IView> {
    protected M mModel;
    protected final String SUCCESS_CODE = "0";
    protected WeakReference<V> weakReferenceView;
    protected CompositeDisposable mCompositeDisposable;
    protected final String TAG = getClass().getCanonicalName();

    public BasePresenter(V mView, M mModel) {
        if (mView == null) {
            throw new NullPointerException("view 不可以为 null");
        }
        if (mModel == null) {
            throw new NullPointerException("model 不可以为 null");
        }
        attachView(mView);
        this.mModel = mModel;
    }

    public void attachView(V mView) {
        weakReferenceView = new WeakReference<V>(mView);
    }

    public void detachView() {
        if (isAttach()) {
            unDispose();
            weakReferenceView.clear();
            weakReferenceView = null;
        }
    }


    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {@link //Activity #onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     * 目前框架已使用 避免内存泄漏
     *
     * @param disposable
     */
    public Disposable addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入容器集中处理
        Log.d(TAG, "添加新的:" + mCompositeDisposable.size());
        return disposable;
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            Log.d(TAG, "unDispose:清空了");
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
            Log.d(TAG, "unDispose:" + mCompositeDisposable.size());
        }
    }

    public boolean isAttach() {
        return weakReferenceView != null && weakReferenceView.get() != null;
    }


    /**
     * 用于 切换线程
     * 显示loading view操作
     *
     * @param second 单位 秒 多久之后显示 loading
     * @return
     */
    protected ObservableTransformer schedulersTransformer(long second) {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable -> {
                            showLoading(second);
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> {
                            if (second != HttpAPI.LOADING_NONE_TIME) {
                                hideLoading();
                            }
                        });
            }
        };
    }

    /**
     * 为了便于 添加 observe 的订阅管理
     * 抽出一个公共方法
     *
     * @param resultObserver 回调自己使用
     * @param <M>            最后需要的数据类型
     * @return
     */
    protected <M> ResultObserver getResult(ResultObserver<M> resultObserver) {
        ResultObserver<M> success = new ResultObserver<M>() {

            @Override
            public void onNext(M m) {
                resultObserver.onNext(m);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                String s = e.toString();
                String error = "服务器异常,请重试";
                if (!TextUtils.isEmpty(error)) {
                    CustomToast.normalToast(error + "");
                }

                resultObserver.onError(e);
                if (isAttach()) {
                    weakReferenceView.get().requestError();
                }
                Log.e("RetrofitLog", "base" + e.toString());
            }

            @Override
            public void onComplete() {
//                if (!NetworkUtils.checkNetworkConnect(ODApplication.context())) {//没有网络的 时候
//                    Toast.makeText(ODApplication.context(), "当前网络不可用，请检查网络状态", Toast.LENGTH_SHORT).show();
//                }
            }
        };
        addDispose(success);
        return success;
    }


    private ResultObserver<Long> observer;

    /**
     * @param second 几秒后显示loading -1不显示
     */
    private void showLoading(long second) {
        if (-1 == second) {
            return;
        }
        observer = new ResultObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "aLong:" + aLong);
                if (isAttach()) {
                    weakReferenceView.get().showLoading();//显示下拉刷新的进度条
                }
            }
        };
        addDispose(observer);
        Observable.interval(second, TimeUnit.SECONDS).take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void hideLoading() {
        if (observer != null) {
            observer.dispose();//如果网络请求完成后 要取消弹框的订阅
        }
        if (isAttach())//关闭弹框
        {
            weakReferenceView.get().hideLoading();
        }
    }

    /**
     * 网络请求处理，响应码处理
     *
     * @param e
     * @return 错误信息
     */
    private String error(Throwable e) {
        if (null == e) {
            return "";
        }
        String error_message = "";
        if (e instanceof UnknownHostException) {
            error_message = "当前网络不可用，请检查网络状态";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            switch (code) {
                case 500:
                    error_message = "服务器错误";
                    break;
                case 400:

                    break;
                default:
                    break;
            }
        } else if (e instanceof JSONException) {
            error_message = "解析错误";
        }
        return error_message;
    }


    protected void showToast(String message) {
        if (!isAttach()) {
            return;
        }
        weakReferenceView.get().showToast(message);
    }

    public void androidInfo(Context context) {
        HttpAPI.getInstence().getServiceApi().androidInfo(ODApplication.city, Constants.APPID, Tools.getIMEI(context), PreferenceUUID.getInstence().getUserId(), PreferenceUUID.getInstence().getUserPhone(), Tools.getPhoneOSVersion(), Tools.getManufacturer(), Tools.getPhoneType(), Tools.getDeviceID(ODApplication.context()), HttpAPI.ip, Tools.getUa(), Tools.getUa2(ODApplication.context()), PreferenceUUID.getInstence().getOaid()).compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData jobListResponseEntityResponseData) {
                    }
                }));
    }
}
