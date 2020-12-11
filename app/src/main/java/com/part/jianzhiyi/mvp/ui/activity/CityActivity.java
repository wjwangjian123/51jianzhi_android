package com.part.jianzhiyi.mvp.ui.activity;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.CityAdapter;
import com.part.jianzhiyi.adapter.CityCharAdapter;
import com.part.jianzhiyi.adapter.HotCityAdapter;
import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.utils.FileUtils;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.mvp.contract.CityContract;
import com.part.jianzhiyi.mvp.presenter.CityPresenter;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route(path = "/app/activity/city")
public class CityActivity extends BaseActivity<CityPresenter> implements CityContract.ICityView {

    private ListView lvCityChar;
    private CityCharAdapter cityCharAdapter;
    private CityAdapter cityAdapter;
    private List<String> hotCityList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private List<String> cityCharList = new ArrayList<>();
    private ListView lvCity;
    private HotCityAdapter hotCityAdapter;
    private GridView gvHotCity;
    private boolean isFirst = true;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initToolbar("选择所在地区");
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_city;
    }

    @Override
    protected void init() {
        super.init();
        isFirst = PreferenceUUID.getInstence().getFirst();
        showPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, "定位");
        String cityChar = FileUtils.getFromAssets(this, "city.json");
        JSONObject jsonObject = JSON.parseObject(cityChar);
        String hot = jsonObject.getString("hot");
        hotCityList = JSON.parseArray(hot, String.class);
    }

    @Override
    protected void initView() {
        lvCity = findViewById(R.id.lv_city);
        addHeaderView();
        lvCityChar = findViewById(R.id.lv_char);
    }

    @Override
    protected void initData() {
        cityCharAdapter = new CityCharAdapter(this, cityCharList);
        lvCityChar.setAdapter(cityCharAdapter);

        cityAdapter = new CityAdapter(this, cityList);
        lvCity.setAdapter(cityAdapter);

        mPresenter.getCity();

    }

    @Override
    protected void permissionResult() {
        super.permissionResult();
        if (isFirst) {
            PreferenceUUID.getInstence().setNotFirst();
        }
    }

    @Override
    protected void permissionSuccess(String permissionName) {
        super.permissionSuccess(permissionName);
    }

    @Override
    protected void permissonFailed() {
        super.permissonFailed();
    }

    private void addHeaderView() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_city_heder, null, false);
        gvHotCity = view.findViewById(R.id.gv_hot_city);
        hotCityAdapter = new HotCityAdapter(this, hotCityList);
        gvHotCity.setAdapter(hotCityAdapter);
        lvCity.addHeaderView(view);

    }


    @Override
    protected void setListener() {
        super.setListener();

        lvCityChar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setListSelect(position);
            }
        });


        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = cityList.get(position - 1);
                if (RegularUtils.isChar(s)) {
                    return;
                }
                setCity(s);
            }
        });

        gvHotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCity(hotCityList.get(position));
            }
        });
    }

    private void setListSelect(int position) {
        if (position < 0 || position > cityCharList.size()) {
            return;
        }
        String s = cityCharList.get(position);
        int i = cityList.indexOf(s);
        lvCity.setSelection(i + 1);
    }

    private void setCity(String city) {
        ODApplication.city = city;
        Intent intent = new Intent();
        intent.putExtra("city", city);
        setResult(1000, intent);
//        mPresenter.androidInfo(CityActivity.this);
        finish();
    }

    @Override
    protected CityPresenter createPresenter() {
        return new CityPresenter(this);
    }

    @Override
    public void startIntent() {

    }


    @Override
    public void updateCity(String text) {
        parseJson(text);
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    private void parseJson(String json) {

        JSONObject object = JSON.parseObject(json);
        Set<String> city = object.keySet();
        Observable.fromIterable(city).toSortedList().subscribe(new SingleObserver<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<String> citys) {
                cityCharList.addAll(citys);
                cityCharList.add(0, "热门");
                cityCharAdapter.notifyDataSetChanged();


                for (String key : citys) {
                    cityList.add(key);
                    for (int i = 0; i < object.getJSONArray(key).size(); i++) {
                        String cityConent = object.getJSONArray(key).getJSONObject(i).getString("area_name");
                        cityList.add(cityConent);
                    }
                }
                cityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("选择城市页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("选择城市页面");
        MobclickAgent.onPause(this);
    }
}
