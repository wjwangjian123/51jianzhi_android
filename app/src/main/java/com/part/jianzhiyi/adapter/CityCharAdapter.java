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
public class CityCharAdapter extends CustomBaseAdapter<String> {

    public CityCharAdapter(Context context, List<String> list) {
        super(context, R.layout.item_city_char, list);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        ((TextView) viewHolder.getView(R.id.tv_char)).setText(item);
    }
}
