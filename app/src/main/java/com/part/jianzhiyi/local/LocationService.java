package com.part.jianzhiyi.local;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.corecommon.preference.PreferenceCity;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.subjects.PublishSubject;


/**
 * @author shixinxin
 * @fuction 地图定位
 */

public class LocationService {

    private final static String TAG = LocationService.class.getSimpleName();
    private AMapLocationClient mLocationClient;
    private PublishSubject<String> subject;
    private Context context;
    private final GeocodeSearch.OnGeocodeSearchListener listener = new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            if (i != 1000) {
                Log.e(TAG, "错误码为：" + i);
                return;
            }

            RegeocodeAddress poiItem = regeocodeResult.getRegeocodeAddress();
            String city = poiItem.getCity();
            ODApplication.city = city;
            PreferenceCity.getInstence().setCity(city);
            Log.i(TAG, "city:" + city);
            stop();
            try {
                subject.onNext(city);
                subject.onComplete();
            } catch (Exception e) {

            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };

    /**
     * @param context
     * @param once    是否只定位一次
     */
    public LocationService(Context context, boolean once) {
        this.context = context.getApplicationContext();
        mLocationClient = new AMapLocationClient(this.context);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置定位模式为高精度模式
        option.setNeedAddress(false);//设置是否返回地址信息（默认返回地址信息）
        if (once) {
            option.setOnceLocationLatest(true);//返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true
        } else {
            option.setInterval(10 * 1000);
        }
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(mListener);
        subject = PublishSubject.create();
        subject.doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                mLocationClient.unRegisterLocationListener(mListener);
                mLocationClient.onDestroy();
            }
        });

    }

    public Observable<String> getSubject() {
        return subject;
    }

    /**
     * 发起定位
     */
    public void start() {
        mLocationClient.startLocation();
    }

    public void stop() {
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        }
    }

    private AMapLocationListener mListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                regeocodeQuery(LocationService.this.context, new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), listener);
            } else {
                int errorCode = aMapLocation.getErrorCode();
                Log.i(TAG, "errorCode:" + errorCode);
                subject.onError(new Throwable("位置获取失败"));

            }
        }
    };


    public void regeocodeQuery(Context context, LatLonPoint latLng, GeocodeSearch.OnGeocodeSearchListener listener) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(context);
        geocodeSearch.setOnGeocodeSearchListener(listener);
        RegeocodeQuery query = new RegeocodeQuery(latLng, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }

}
