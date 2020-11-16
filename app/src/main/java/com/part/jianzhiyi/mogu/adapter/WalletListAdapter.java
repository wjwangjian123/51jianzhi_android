package com.part.jianzhiyi.mogu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.model.entity.moku.MokuBillListEntity;

import java.util.List;

/**
 * 钱包
 *
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class WalletListAdapter extends CustomBaseAdapter<MokuBillListEntity.DataBean> {

    private Context context;

    public WalletListAdapter(Context context, List<MokuBillListEntity.DataBean> list) {
        super(context, R.layout.layout_wallet_tixian, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MokuBillListEntity.DataBean item, int position) {
        if (item != null) {
            ((TextView) viewHolder.getView(R.id.item_wallet_tv_time)).setText(item.getCreate_time());
            if (item.getType() == 1) {
                //增
                if (item.getIcon() != null) {
                    FrescoUtil.setHttpPic(item.getIcon(), ((SimpleDraweeView) viewHolder.getView(R.id.item_wallet_iv_icon)));
                }
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_name)).setText(item.getShow_name());
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_money)).setTextColor(Color.parseColor("#344051"));
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_money)).setText("+" + item.getMoney());
            } else if (item.getType() == 0) {
                //减
                FrescoUtil.setResPic(R.drawable.icon_tixian, ((SimpleDraweeView) viewHolder.getView(R.id.item_wallet_iv_icon)));
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_name)).setText("提现-提现成功");
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_money)).setTextColor(Color.parseColor("#00A980"));
                ((TextView) viewHolder.getView(R.id.item_wallet_tv_money)).setText("-" + item.getMoney());
            }
        }
    }
}
