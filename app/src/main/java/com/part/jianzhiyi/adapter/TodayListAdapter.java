package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;

import java.util.List;

/**
 * 搜索
 * 列表页面
 *
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class TodayListAdapter extends CustomBaseAdapter<SearchEntity.DataBean> {

    private Context context;

    public TodayListAdapter(Context context, List<SearchEntity.DataBean> list) {
        super(context, R.layout.item_today_layout, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, SearchEntity.DataBean item, int position) {
        if (item != null) {
            if (position == 0) {
                ((ImageView) viewHolder.getView(R.id.iv_top)).setImageResource(R.drawable.icon_home_one);
            }
            if (position == 1) {
                ((ImageView) viewHolder.getView(R.id.iv_top)).setImageResource(R.drawable.icon_home_two);
            }
            if (position == 2) {
                ((ImageView) viewHolder.getView(R.id.iv_top)).setImageResource(R.drawable.icon_home_three);
            }
            if (position == 3) {
                ((ImageView) viewHolder.getView(R.id.iv_top)).setImageResource(R.drawable.icon_home_four);
            }
            ((TextView) viewHolder.getView(R.id.tv_title)).setText(item.getTitle());
            ((TextView) viewHolder.getView(R.id.tv_price1)).setText(item.getPrice1());
            ((TextView) viewHolder.getView(R.id.tv_price2)).setText(item.getPrice2());
        }
    }
}
