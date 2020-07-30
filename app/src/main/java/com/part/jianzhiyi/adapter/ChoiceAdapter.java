package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.ChoiceEntity;

import java.util.List;
/**
 * @author:shixinxin
 * @Date :2020-03-30
 **/
public class ChoiceAdapter extends CustomBaseAdapter<ChoiceEntity.ChoiceBean> {

    private Context context;

    public ChoiceAdapter(Context context, List<ChoiceEntity.ChoiceBean> list) {
        super(context, R.layout.item_choice, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ChoiceEntity.ChoiceBean item, int position) {
        if (item!=null){
            if (position%3==0){
                ((RelativeLayout) viewHolder.getView(R.id.choice_rl)).setBackgroundResource(R.drawable.icon_choice0);
            }
            if (position%3==1){
                ((RelativeLayout) viewHolder.getView(R.id.choice_rl)).setBackgroundResource(R.drawable.icon_choice1);
            }
            if (position%3==2){
                ((RelativeLayout) viewHolder.getView(R.id.choice_rl)).setBackgroundResource(R.drawable.icon_choice2);
            }
            if (item.getTime() == null || item.getTime() == "") {
                ((TextView) viewHolder.getView(R.id.choice_tv_time)).setText("不限");
            } else {
                ((TextView) viewHolder.getView(R.id.choice_tv_time)).setText(item.getTime());
            }
            ((TextView) viewHolder.getView(R.id.choice_tv_title)).setText(item.getTitle());
            ((TextView) viewHolder.getView(R.id.choice_tv_method)).setText(item.getMethod());
            ((TextView) viewHolder.getView(R.id.choice_tv_sex)).setText(item.getSex() == null ? "男女不限" : item.getSex());
            ((TextView) viewHolder.getView(R.id.choice_tv_price1)).setText(item.getPrice1());
            ((TextView) viewHolder.getView(R.id.choice_tv_price2)).setText(item.getPrice2());
        }
    }
}
