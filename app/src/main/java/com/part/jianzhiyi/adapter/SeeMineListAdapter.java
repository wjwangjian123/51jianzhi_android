package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.model.entity.ViewedEntity;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class SeeMineListAdapter extends CustomBaseAdapter<ViewedEntity.DataBean> {

    private Context context;

    public SeeMineListAdapter(Context context, List<ViewedEntity.DataBean> list) {
        super(context, R.layout.item_see_mine_layout, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ViewedEntity.DataBean item, int position) {
        if (item != null) {
            if (item.getImg() == null || item.getImg() == "") {
                FrescoUtil.setResPic(R.drawable.icon_pic1, ((SimpleDraweeView) viewHolder.getView(R.id.view_iv_headpic)));
            } else {
                FrescoUtil.setHttpPic(item.getImg(), ((SimpleDraweeView) viewHolder.getView(R.id.view_iv_headpic)));
            }
            ((TextView) viewHolder.getView(R.id.view_tv_name)).setText(item.getName());
            ((TextView) viewHolder.getView(R.id.view_tv_word)).setText(item.getMsg1());
            ((TextView) viewHolder.getView(R.id.view_tv_time)).setText(item.getData());
        }
    }
}
