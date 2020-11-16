package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class ChoiceRecommendMoreAdapter extends CustomBaseAdapter<JobListResponseEntity2.DataBean> {

    private Context context;

    public ChoiceRecommendMoreAdapter(Context context, List<JobListResponseEntity2.DataBean> list) {
        super(context, R.layout.item_choice_more_recommend, list);
        this.context = context;
    }


    @Override
    protected void convert(ViewHolder viewHolder, JobListResponseEntity2.DataBean item, int position) {
        if (item!=null){
            if (position%3==0){
                ((LinearLayout) viewHolder.getView(R.id.choice_rl)).setBackgroundResource(R.drawable.icon_edition_bg1);
            }
            if (position%3==1){
                ((LinearLayout) viewHolder.getView(R.id.choice_rl)).setBackgroundResource(R.drawable.icon_edition_bg2);
            }
            if (position%3==2){
                ((LinearLayout) viewHolder.getView(R.id.choice_rl)).setBackgroundResource(R.drawable.icon_edition_bg3);
            }
            ((TextView) viewHolder.getView(R.id.choice_tv_title)).setText(item.getTitle());
            if (item.getPlace().equals(null) || item.getPlace().equals("")) {
                if (PreferenceUUID.getInstence().getCity() == "" || PreferenceUUID.getInstence().getCity() == null) {
                    ((TextView) viewHolder.getView(R.id.item_tv_place)).setText("不限");
                } else {
                    ((TextView) viewHolder.getView(R.id.item_tv_place)).setText(PreferenceUUID.getInstence().getCity());
                }
            } else {
                ((TextView) viewHolder.getView(R.id.item_tv_place)).setText(item.getPlace());
            }
            if (item.getMethod().equals(null) || item.getMethod().equals("")){
                ((View) viewHolder.getView(R.id.view_method)).setVisibility(View.GONE);
            }else {
                ((View) viewHolder.getView(R.id.view_method)).setVisibility(View.VISIBLE);
                ((TextView) viewHolder.getView(R.id.item_tv_method)).setText(item.getMethod());
            }
            if (item.getTime() == null || item.getTime() == "") {
                ((TextView) viewHolder.getView(R.id.item_tv_time)).setText("不限");
            } else {
                ((TextView) viewHolder.getView(R.id.item_tv_time)).setText(item.getTime());
            }
            ((TextView) viewHolder.getView(R.id.choice_tv_price1)).setText(item.getPrice1());
            ((TextView) viewHolder.getView(R.id.choice_tv_price2)).setText(item.getPrice2());
        }
    }
}
