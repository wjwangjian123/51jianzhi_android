package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class FavouriteAdapter extends CustomBaseAdapter<JobListResponseEntity> {

    private Context context;
    private OnCancelClickListener onCancelClickListener;

    public FavouriteAdapter(Context context, List<JobListResponseEntity> list) {
        super(context, R.layout.item_favourite, list);
        this.context = context;
    }

    public void setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, JobListResponseEntity item, int position) {
        if (item != null) {
            ((TextView) viewHolder.getView(R.id.fa_tv_jobname)).setText(item.getTitle());
            ((TextView) viewHolder.getView(R.id.fa_tv_price)).setText(item.getPrice());
            ((TextView) viewHolder.getView(R.id.fa_tv_company)).setText(item.getCompany());
            ((TextView) viewHolder.getView(R.id.fa_tv_settlement)).setText(item.getMethod());
            if (item.getPlace() == null || item.getPlace() == "") {
                if (PreferenceUUID.getInstence().getCity() == "" || PreferenceUUID.getInstence().getCity() == null) {
                    ((TextView) viewHolder.getView(R.id.fa_tv_address)).setText("不限");
                } else {
                    ((TextView) viewHolder.getView(R.id.fa_tv_address)).setText(PreferenceUUID.getInstence().getCity());
                }
            } else {
                ((TextView) viewHolder.getView(R.id.fa_tv_address)).setText(item.getPlace());
            }
            if (item.getTime() == null || item.getTime() == "") {
                ((TextView) viewHolder.getView(R.id.fa_tv_time)).setText("不限");
            } else {
                ((TextView) viewHolder.getView(R.id.fa_tv_time)).setText(item.getTime());
            }
            viewHolder.getView(R.id.fa_tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCancelClickListener == null) {
                        return;
                    }
                    onCancelClickListener.onItemCancel(position);
                }
            });
        }
    }
}


