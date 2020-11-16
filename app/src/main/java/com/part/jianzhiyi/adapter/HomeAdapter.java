package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.ad.PositionId;
import com.part.jianzhiyi.customview.CSJAdView;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-30
 **/
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<JobListResponseEntity2.DataBean> list;
    private final static String TAG = HomeAdapter.class.getSimpleName();
    private int position;

    public HomeAdapter(Context context, List<JobListResponseEntity2.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        this.position = position;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getUiType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdViewHolder adViewHolder = null;
        HomeViewHolder homeViewHolder = null;
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case 0:
                    Log.i(TAG, position + "创建内容View");
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_home_type, parent, false);
                    homeViewHolder = new HomeViewHolder(convertView);
                    convertView.setTag(homeViewHolder);
                    break;
                case 1:
                    Log.i(TAG, position + "创建广告View");
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_ad, parent, false);
                    adViewHolder = new AdViewHolder(convertView);
                    convertView.setTag(adViewHolder);
                    break;
                default:
                    break;
            }
        } else {
            switch (getItemViewType(position)) {
                case 0:
                    homeViewHolder = (HomeViewHolder) convertView.getTag();
                    break;
                case 1:
                    adViewHolder = (AdViewHolder) convertView.getTag();
                    break;
            }
        }
        switch (getItemViewType(position)) {
            case 0:
                if (list != null && list.size() > 0) {
                    JobListResponseEntity2.DataBean item = list.get(position);
                    homeViewHolder.mTvTypeContent.setText(item.getTitle());
                    homeViewHolder.mTvPrice1.setText(item.getPrice1());
                    homeViewHolder.mTvPrice2.setText(item.getPrice2());
                    if (item.getMethod() == null || item.getMethod() == "") {
                        homeViewHolder.mItemTvSettlement.setText("不限");
                    } else {
                        homeViewHolder.mItemTvSettlement.setText(item.getMethod());
                    }
                    if (item.getTime() == null || item.getTime() == "") {
                        homeViewHolder.mItemTvTime.setText("不限");
                    } else {
                        homeViewHolder.mItemTvTime.setText(item.getTime());
                    }
                }
                break;
            case 1:
                adViewHolder.mBannerContainer.setPosId(PositionId.HOME_POS_ID);
                adViewHolder.mBannerContainer.loadAd();
                AdViewHolder finalAdViewHolder = adViewHolder;
                adViewHolder.mBannerContainer.setOnAdListener(new CSJAdView.OnAdListener() {
                    @Override
                    public void onAdShowListener() {
                        finalAdViewHolder.ivHolder.setVisibility(View.GONE);
                        finalAdViewHolder.rlAd.setVisibility(View.VISIBLE);
                        finalAdViewHolder.mBannerContainer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdCloseListener() {
                        finalAdViewHolder.mBannerContainer.setVisibility(View.GONE);
                        finalAdViewHolder.rlAd.setVisibility(View.GONE);
                        if (position < list.size()) {
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
                break;
            default:
                break;
        }
        return convertView;
    }

    class AdViewHolder {
        private RelativeLayout rlAd;
        private ImageView ivHolder;
        private CSJAdView mBannerContainer;

        private AdViewHolder(View view) {
            rlAd = view.findViewById(R.id.rl_ad);
            ivHolder = view.findViewById(R.id.iv_holder);
            mBannerContainer = view.findViewById(R.id.banner_container);
        }
    }

    class HomeViewHolder {
        private TextView mTvTypeContent;
        private TextView mTvPrice1;
        private TextView mTvPrice2;
        private TextView mItemTvTime;
        private TextView mItemTvSettlement;

        private HomeViewHolder(View view) {
            mTvTypeContent = view.findViewById(R.id.tv_type_content);
            mTvPrice1 = view.findViewById(R.id.tv_price1);
            mTvPrice2 = view.findViewById(R.id.tv_price2);
            mItemTvTime = view.findViewById(R.id.item_tv_time);
            mItemTvSettlement = view.findViewById(R.id.item_tv_settlement);
        }
    }
}
