package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-24
 **/
public class CityAdapter extends CustomBaseAdapter<String> {

    private TextView tvCity;
    private Context context;

    public CityAdapter(Context context, List<String> list) {
        super(context, R.layout.item_city, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        boolean aChar = RegularUtils.isChar(item);
        tvCity = ((TextView) viewHolder.getView(R.id.tv_city));
        tvCity.setText(item);
        tvCity.setTextSize(aChar ? 18 : 16);
        int resColor = context.getResources().getColor(aChar ? R.color.color_text_theme : R.color.color_333333);
        tvCity.setTextColor(resColor);
    }
}
