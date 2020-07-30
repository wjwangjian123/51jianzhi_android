package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class ChoiceRecommendMoreAdapter extends CustomBaseAdapter<JobListResponseEntity2.DataBean> {

    private Context context;

    public ChoiceRecommendMoreAdapter(Context context, List<JobListResponseEntity2.DataBean> list) {
        super(context, R.layout.item_choice_more_recommend, list);
        this.context = context;
    }


    @Override
    protected void convert(ViewHolder viewHolder, JobListResponseEntity2.DataBean item, int position) {
        ((TextView) viewHolder.getView(R.id.tv_content)).setText(item.getTitle());
        ((TextView) viewHolder.getView(R.id.tv_method)).setText(item.getMethod());
        ((TextView) viewHolder.getView(R.id.tv_company)).setText(item.getCompany());
        ((TextView) viewHolder.getView(R.id.tv_price1)).setText(item.getPrice1());
        ((TextView) viewHolder.getView(R.id.tv_price2)).setText(item.getPrice2());
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
