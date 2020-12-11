package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.ViewedEntity;

import java.util.List;

/**
 * 搜索
 * 列表页面
 *
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class SearchViewListAdapter extends CustomBaseAdapter<ViewedEntity.DataBean> {

    private Context context;

    public SearchViewListAdapter(Context context, List<ViewedEntity.DataBean> list) {
        super(context, R.layout.item_home_type, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ViewedEntity.DataBean item, int position) {
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
