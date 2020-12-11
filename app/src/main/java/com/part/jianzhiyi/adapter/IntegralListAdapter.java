package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.integral.MyIntegralEntity;

import java.util.List;

public class IntegralListAdapter extends CustomBaseAdapter<MyIntegralEntity.DataBeanX.DataBean> {


    private Context context;

    public IntegralListAdapter(Context context, List<MyIntegralEntity.DataBeanX.DataBean> list) {
        super(context, R.layout.item_integral_layout, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MyIntegralEntity.DataBeanX.DataBean item, int position) {
        if (item != null) {
            ((TextView) viewHolder.getView(R.id.tv_task_name)).setText(item.getTypename());
            ((TextView) viewHolder.getView(R.id.tv_task_time)).setText(item.getYmd());
            ((TextView) viewHolder.getView(R.id.tv_task_score)).setText(item.getJfname());
        }
    }
}
