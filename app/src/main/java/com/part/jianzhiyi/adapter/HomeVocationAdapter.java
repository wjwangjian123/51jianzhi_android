package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;

import java.util.List;

public class HomeVocationAdapter extends CustomBaseAdapter<JobListResponseEntity2.DataBean> {


    private Context context;

    public HomeVocationAdapter(Context context, List<JobListResponseEntity2.DataBean> list) {
        super(context, R.layout.item_home_vocation, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, JobListResponseEntity2.DataBean item, int position) {
        if (item != null) {
            ((TextView) viewHolder.getView(R.id.tv_home_vaction_title)).setText(item.getTitle());
            ((TextView) viewHolder.getView(R.id.tv_home_vaction_price1)).setText(item.getPrice1());
            ((TextView) viewHolder.getView(R.id.tv_home_vaction_price2)).setText(item.getPrice2());
            if (item.getMethod() == null || item.getMethod() == "") {
                ((TextView) viewHolder.getView(R.id.item_tv_settlement)).setText("不限");
            } else {
                ((TextView) viewHolder.getView(R.id.item_tv_settlement)).setText(item.getMethod());
            }
            if (item.getTime() == null || item.getTime() == "") {
                ((TextView) viewHolder.getView(R.id.item_tv_time)).setText("不限");
            } else {
                ((TextView) viewHolder.getView(R.id.item_tv_time)).setText(item.getTime());
            }
        }
    }
}
