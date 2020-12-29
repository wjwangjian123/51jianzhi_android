package com.part.jianzhiyi.modulemerchants.adapter;

import android.content.Context;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;

import java.util.List;

/**
 * 搜索
 * 列表页面
 *
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class MerRechargeAdapter extends CustomBaseAdapter<MMyWalletEntity.DataBean.SubListBean> {

    private Context context;

    public MerRechargeAdapter(Context context, List<MMyWalletEntity.DataBean.SubListBean> list) {
        super(context, R.layout.item_mer_recharge, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MMyWalletEntity.DataBean.SubListBean item, int position) {
        if (item != null) {
            ((TextView) viewHolder.getView(R.id.tv_time)).setText(item.getDate());
            ((TextView) viewHolder.getView(R.id.tv_method)).setText("支付方式:" + item.getType());
            ((TextView) viewHolder.getView(R.id.tv_id)).setText(item.getId());
            ((TextView) viewHolder.getView(R.id.tv_seller)).setText(item.getSeller_id());
            ((TextView) viewHolder.getView(R.id.tv_pay_money)).setText(item.getMoney());
            ((TextView) viewHolder.getView(R.id.tv_money)).setText("余额：" + item.getY_money());
        }
    }
}
