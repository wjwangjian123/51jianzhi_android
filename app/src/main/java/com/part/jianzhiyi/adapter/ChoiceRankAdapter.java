package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.ChoiceEntity;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class ChoiceRankAdapter extends CustomBaseAdapter<ChoiceEntity.WeeklyBean> {

    private Context context;

    public ChoiceRankAdapter(Context context, List<ChoiceEntity.WeeklyBean> list) {
        super(context, R.layout.item_choice_rank, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ChoiceEntity.WeeklyBean item, int position) {
        if (item != null) {
            if (position % 3 == 0) {
                ((RelativeLayout) viewHolder.getView(R.id.item_rl)).setBackgroundResource(R.drawable.icon_choice_bg_one);
            }
            if (position % 3 == 1) {
                ((RelativeLayout) viewHolder.getView(R.id.item_rl)).setBackgroundResource(R.drawable.icon_choice_bg_two);
            }
            if (position % 3 == 2) {
                ((RelativeLayout) viewHolder.getView(R.id.item_rl)).setBackgroundResource(R.drawable.icon_choice_bg_three);
            }
            int i = position + 1;
            ((TextView) viewHolder.getView(R.id.item_rank_position)).setText("0" + i);
            ((TextView) viewHolder.getView(R.id.item_rank_title)).setText(item.getTitle());
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
            ((TextView) viewHolder.getView(R.id.item_rank_price1)).setText(item.getPrice1());
            ((TextView) viewHolder.getView(R.id.item_rank_price2)).setText(item.getPrice2());
        }
    }
}
