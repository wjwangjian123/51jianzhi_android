package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.model.entity.MessageResponseEntity;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class MessageListAdapter extends CustomBaseAdapter<MessageResponseEntity> {

    private Context context;

    public MessageListAdapter(Context context, List<MessageResponseEntity> list) {
        super(context, R.layout.item_see_mine_layout, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MessageResponseEntity item, int position) {
        if (item != null) {
            if (item.getHeadimg() == null || item.getHeadimg() == "") {
                FrescoUtil.setResPic(R.drawable.icon_pic1, ((SimpleDraweeView) viewHolder.getView(R.id.view_iv_headpic)));
            } else {
                FrescoUtil.setHttpPic(item.getHeadimg(), ((SimpleDraweeView) viewHolder.getView(R.id.view_iv_headpic)));
            }
            ((TextView) viewHolder.getView(R.id.view_tv_name)).setText(item.getCompany());
            ((TextView) viewHolder.getView(R.id.view_tv_word)).setText(item.getMsg3());
            ((TextView) viewHolder.getView(R.id.view_tv_time)).setText(item.getTime());
            if (item.getIsRed() == 1) {
                ((ImageView) viewHolder.getView(R.id.view_iv_red)).setVisibility(View.GONE);
            } else if (item.getIsRed() == 2) {
                ((ImageView) viewHolder.getView(R.id.view_iv_red)).setVisibility(View.VISIBLE);
            }
        }
    }
}
