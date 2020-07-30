package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.utils.UiUtils;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-30
 **/
public class ChoiceSpecialMoreAdapter extends BaseAdapter {
    private final static String TAG = ChoiceSpecialMoreAdapter.class.getSimpleName();
    private Context context;
    private List<JobListResponseEntity2.DataBean> list;


    public ChoiceSpecialMoreAdapter(Context context, List<JobListResponseEntity2.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
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
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_choice, parent, false);
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
                    String name = "icon_choice" + (position % 4);
                    int imageResId = UiUtils.getImageResId(context, name);
                    homeViewHolder.mChoiceRl.setBackgroundResource(imageResId);
                    homeViewHolder.mChoiceTvTitle.setText(item.getTitle());
                    homeViewHolder.mChoiceTvPrice1.setText(item.getPrice1());
                    homeViewHolder.mChoiceTvPrice2.setText(item.getPrice2());
                    if (item.getSex() == null || item.getSex() == "") {
                        homeViewHolder.mChoiceTvSex.setText("男女不限");
                    } else {
                        homeViewHolder.mChoiceTvSex.setText(item.getSex());
                    }
                    if (item.getTime() == null || item.getTime() == "") {
                        homeViewHolder.mChoiceTvTime.setText("不限");
                    } else {
                        homeViewHolder.mChoiceTvTime.setText(item.getTime());
                    }
                    homeViewHolder.mChoiceTvMethod.setText(item.getMethod());
                }
                break;
            case 1:
                break;
            default:
                break;
        }

        return convertView;
    }


    class AdViewHolder {
        private RelativeLayout rlAd;
        private ImageView ivHolder;

        private AdViewHolder(View view) {
            rlAd = view.findViewById(R.id.rl_ad);
            ivHolder = view.findViewById(R.id.iv_holder);
        }
    }

    class HomeViewHolder {

        public TextView mChoiceTvTitle;
        public TextView mChoiceTvTime;
        public TextView mChoiceTvMethod;
        public TextView mChoiceTvSex;
        public LinearLayout mChoiceLl;
        public TextView mChoiceTvPrice1;
        public TextView mChoiceTvPrice2;
        public RelativeLayout mChoiceRl;

        private HomeViewHolder(View view) {
            mChoiceTvTitle = view.findViewById(R.id.choice_tv_title);
            mChoiceTvTime = view.findViewById(R.id.choice_tv_time);
            mChoiceTvMethod = view.findViewById(R.id.choice_tv_method);
            mChoiceTvSex = view.findViewById(R.id.choice_tv_sex);
            mChoiceLl = view.findViewById(R.id.choice_ll);
            mChoiceTvPrice1 = view.findViewById(R.id.choice_tv_price1);
            mChoiceTvPrice2 = view.findViewById(R.id.choice_tv_price2);
            mChoiceRl = view.findViewById(R.id.choice_rl);
        }
    }
}
