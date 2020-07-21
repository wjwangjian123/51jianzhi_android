package com.part.jianzhiyi.http;

import android.content.Context;
import android.util.Log;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wangshuang on 17/11/17.
 * 网络请求的API
 * <p>
 * 应用拦截器（addInterceptor）：  主要用于查看请求信息及返回信息，如链接地址、头信息、参数信息等；
 * 网络拦截器（addNetworkInterceptor）：可以添加、删除或替换请求头信息，还可以改变的请求携带的实体。
 */

public class HttpAPI {

    private String TAG = "HttpAPI--TAG:";
    private static final int WRITE_TIME = 10 * 1000;
    private static final int READ_TIME = 10 * 1000;
    private static final int TIME_OUT = 90 * 1000;


    public static final int LOADING_DEFAULT_TIME = 2;//2s转圈
    public static final int LOADING_CUSTOM_TIME = 0;//直接转圈
    public static final int LOADING_NONE_TIME = -1;//不转

    public static final int MODE_DEFAULT = 0;
    public static final int MODE_CUSTOM = 1;

    private static HttpAPI mHttpAPI;
    private static HttpAPI mScaleHttpAPI;
    private Context mContext;
    private static Context activity_context;
    private ServiceAPI mServiceApi;
    private ServiceAPI mScaleServiceApi;
    public static String ip = "";
    //请求 成功码
    public static final int MAX_AGE = 1 * 60;
    public static final int CACHE_STALE_SEC = 10 * 60;
    public final static String REQUEST_SUCCESS = "200";
    public final static String REQUEST_OVERTIME = "65536";
    public Map<String, String> defaultHeadMap = new HashMap<>();

    public void initMap() {
//        defaultHeadMap.put("pv", Tools.getPhoneOSVersion());//手机系统
//        defaultHeadMap.put("pe", Tools.getManufacturer());//手机厂商
//        defaultHeadMap.put("pt",  Tools.getPhoneType());//手机型号
//        defaultHeadMap.put("deviceid", Tools.getDeviceID(ODApplication.context()));//deviceId
//        defaultHeadMap.put("imei",Tools.getIMEI(ODApplication.context()));//imei
//        defaultHeadMap.put("appid","8");//imei
//        defaultHeadMap.put("ip",ip);//imei
    }

    public void refreshIp(String ip) {
        this.ip = ip;
//        defaultHeadMap.put("ip",ip);
    }

    private Interceptor interceptor;

    {
        interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().build();
                return chain.proceed(request);
            }
        }

        ;
    }

    //拦截器添加公共参数
    private class CommonInterceptorNew implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
//            Iterator<Map.Entry<String, String>> iterator = defaultHeadMap.entrySet().iterator();

            Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> next = iterator.next();
//                requestBuilder.addHeader(next.getKey(), next.getValue());
//            }
            Request request = requestBuilder.build();

            Response proceed = chain.proceed(request);
            return proceed;
        }

    }


    private HttpAPI(Context context) {
        initMap();
        mServiceApi = createService(true);
        this.mContext = context;
    }

    private HttpAPI(Context context, boolean isGson) {
        initMap();
        mScaleServiceApi = createService(isGson);
        this.mContext = context;
    }


    public synchronized static HttpAPI getInstence(Context context) {
        activity_context = context;
        if (mHttpAPI == null) {
            mHttpAPI = new HttpAPI(context);
        }
        return mHttpAPI;
    }

    public synchronized static HttpAPI getInstence() {
        activity_context = ODApplication.context();
        if (mHttpAPI == null) {
            mHttpAPI = new HttpAPI(activity_context);
        }
        return mHttpAPI;
    }

    public synchronized static HttpAPI getInstence(boolean isGson) {
        activity_context = ODApplication.context();
        if (isGson) {
            if (mHttpAPI == null) {
                mHttpAPI = new HttpAPI(activity_context);
            }
            return mHttpAPI;
        } else {
            if (mScaleHttpAPI == null) {
                mScaleHttpAPI = new HttpAPI(activity_context, false);
            }
            return mScaleHttpAPI;
        }
    }

    public ServiceAPI getServiceApi() {
        return mServiceApi;
    }

    public ServiceAPI getScaleServiceApi() {
        return mScaleServiceApi;
    }


    private ServiceAPI createService(boolean isGson) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Cache cache = new Cache(new File(Tools.getApplicationByReflection().getApplicationContext().getCacheDir(), "httpCache"), 1024 * 1024 * 100);
        int maxCacheSeconds = 10 * 60;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new CommonInterceptorNew())
                .addInterceptor(loggingInterceptor)
                .writeTimeout(WRITE_TIME, TimeUnit.SECONDS)
                .readTimeout(READ_TIME, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//链接失败后要重新链接吗
                .addNetworkInterceptor(interceptor)
                .cache(cache);

        if (isGson) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build();

            return retrofit.create(ServiceAPI.class);
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URI)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build();

            return retrofit.create(ServiceAPI.class);
        }
    }


}
