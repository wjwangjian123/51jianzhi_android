package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.corecommon.utils.UiUtils;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

public class HomeVocationAdapter extends CustomBaseAdapter<JobListResponseEntity2.DataBean> {


    private Context context;

    public HomeVocationAdapter(Context context, List<JobListResponseEntity2.DataBean> list) {
        super(context, R.layout.item_home_vocation, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, JobListResponseEntity2.DataBean item, int position) {
        String name = "ic_salary" + (position + 5) % 5;
        int imageResId = UiUtils.getImageResId(context, name);
        ((ImageView) viewHolder.getView(R.id.iv_home_vaction_avatar)).setImageResource(imageResId);
        ((TextView) viewHolder.getView(R.id.tv_home_vaction_title)).setText(item.getTitle());
        ((TextView) viewHolder.getView(R.id.item_tv_settlement)).setText(item.getMethod());
        ((TextView) viewHolder.getView(R.id.tv_home_vaction_price)).setText(item.getPrice());
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
