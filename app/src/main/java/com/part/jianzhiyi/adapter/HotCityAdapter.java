package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-24
 **/
public class HotCityAdapter extends CustomBaseAdapter<String> {

    public HotCityAdapter(Context context, List<String> list) {
        super(context, R.layout.item_hot_city, list);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        ((TextView) viewHolder.getView(R.id.tv_hot_city)).setText(item);
    }
}
