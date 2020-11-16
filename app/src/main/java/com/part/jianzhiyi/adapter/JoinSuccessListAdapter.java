package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

/**
 * 搜索
 * 列表页面
 *
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class JoinSuccessListAdapter extends CustomBaseAdapter<SearchEntity.DataBean> {

    private Context context;

    public JoinSuccessListAdapter(Context context, List<SearchEntity.DataBean> list) {
        super(context, R.layout.item_join_successs_layout, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, SearchEntity.DataBean item, int position) {
        if (item != null) {
            ((TextView) viewHolder.getView(R.id.tv_type_content)).setText(item.getTitle());
            ((TextView) viewHolder.getView(R.id.tv_price1)).setText(item.getPrice1());
            ((TextView) viewHolder.getView(R.id.tv_price2)).setText(item.getPrice2());
            ((TextView) viewHolder.getView(R.id.item_tv_settlement)).setText(item.getMethod());
            if (item.getPlace() == null || item.getPlace() == "") {
                if (PreferenceUUID.getInstence().getCity() == "" || PreferenceUUID.getInstence().getCity() == null) {
                    ((TextView) viewHolder.getView(R.id.item_tv_place)).setText("不限");
                } else {
                    ((TextView) viewHolder.getView(R.id.item_tv_place)).setText(PreferenceUUID.getInstence().getCity());
                }
            } else {
                ((TextView) viewHolder.getView(R.id.item_tv_place)).setText(item.getPlace());
            }
            if (item.getTime() == null || item.getTime() == "") {
                ((TextView) viewHolder.getView(R.id.item_tv_time)).setText("不限");
            } else {
                ((TextView) viewHolder.getView(R.id.item_tv_time)).setText(item.getTime());
            }
        }
    }
}
