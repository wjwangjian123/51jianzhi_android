package com.part.jianzhiyi.mogu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.model.entity.moku.TxLogEntity;

import java.util.List;

/**
 * 钱包
 *
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class TxLogListAdapter extends CustomBaseAdapter<TxLogEntity.DataBean> {

    private Context context;

    public TxLogListAdapter(Context context, List<TxLogEntity.DataBean> list) {
        super(context, R.layout.layout_wallet_tixian, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, TxLogEntity.DataBean item, int position) {
        if (item != null) {
            ((TextView) viewHolder.getView(R.id.item_wallet_tv_name)).setText(item.getTitle());
            ((TextView) viewHolder.getView(R.id.item_wallet_tv_time)).setText(item.getTime());
            ((TextView) viewHolder.getView(R.id.item_wallet_tv_money)).setText(item.getMoney());
            if (item.getStatus().equals("0")) {
                //提现申请中
                FrescoUtil.setResPic(R.drawable.icon_tixian_ing, ((SimpleDraweeView) viewHolder.getView(R.id.item_wallet_iv_icon)));
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_money)).setTextColor(Color.parseColor("#FFBD3E"));
            } else if (item.getStatus().equals("1")) {
                //提现成功
                FrescoUtil.setResPic(R.drawable.icon_tixian, ((SimpleDraweeView) viewHolder.getView(R.id.item_wallet_iv_icon)));
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_money)).setTextColor(Color.parseColor("#00A980"));
            } else if (item.getStatus().equals("2")) {
                //提现失败
                FrescoUtil.setResPic(R.drawable.icon_tixian_fail, ((SimpleDraweeView) viewHolder.getView(R.id.item_wallet_iv_icon)));
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_money)).setTextColor(Color.parseColor("#F7536A"));
            }
        }
    }
}
