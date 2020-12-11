package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.JobDetailEntity;

import java.util.List;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class HomeLoveAdapter extends CustomBaseAdapter<JobDetailEntity.DataBean.LoveBean> {

    private Context context;

    public HomeLoveAdapter(Context context, List<JobDetailEntity.DataBean.LoveBean> list) {
        super(context, R.layout.item_home_type, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, JobDetailEntity.DataBean.LoveBean item, int position) {
        if (item != null) {
            ((TextView) viewHolder.getView(R.id.tv_type_content)).setText(item.getTitle());
            ((TextView) viewHolder.getView(R.id.tv_price1)).setText(item.getPrice1());
            ((TextView) viewHolder.getView(R.id.tv_price2)).setText(item.getPrice2());
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
