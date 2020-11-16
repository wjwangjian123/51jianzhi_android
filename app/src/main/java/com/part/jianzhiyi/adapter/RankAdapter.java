package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

/**
 * @author:shixinxin
 * @content：首页推荐，简单易做
 * @vision:V1.0.1
 **/
public class RankAdapter extends CustomBaseAdapter<JobListResponseEntity2.DataBean> {

    private Context context;

    public RankAdapter(Context context, List<JobListResponseEntity2.DataBean> list) {
        super(context, R.layout.item_rank, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, JobListResponseEntity2.DataBean item, int position) {
        if (item != null) {
            if (position == 0) {
                ((TextView) viewHolder.getView(R.id.item_rank_position)).setBackgroundResource(R.drawable.icon_rank_one);
                ((TextView) viewHolder.getView(R.id.item_rank_position)).setText("");
            }
            if (position == 1) {
                ((TextView) viewHolder.getView(R.id.item_rank_position)).setBackgroundResource(R.drawable.icon_rank_two);
                ((TextView) viewHolder.getView(R.id.item_rank_position)).setText("");
            }
            if (position == 2) {
                ((TextView) viewHolder.getView(R.id.item_rank_position)).setBackgroundResource(R.drawable.icon_rank_three);
                ((TextView) viewHolder.getView(R.id.item_rank_position)).setText("");
            }
            if (position > 2) {
                int i = position + 1;
                ((TextView) viewHolder.getView(R.id.item_rank_position)).setText(String.valueOf(i));
            }
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
            if (item.getMethod().equals(null) || item.getMethod().equals("")) {
                ((View) viewHolder.getView(R.id.view_method)).setVisibility(View.GONE);
            } else {
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
