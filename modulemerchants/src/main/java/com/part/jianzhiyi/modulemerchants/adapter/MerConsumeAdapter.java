package com.part.jianzhiyi.modulemerchants.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
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
public class MerConsumeAdapter extends CustomBaseAdapter<MMyWalletEntity.DataBean.SubListBean> {

    private Context context;

    public MerConsumeAdapter(Context context, List<MMyWalletEntity.DataBean.SubListBean> list) {
        super(context, R.layout.item_mer_consume, list);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MMyWalletEntity.DataBean.SubListBean item, int position) {
        if (item != null) {
            if (item.getIs_copy() == 1) {
                ((LinearLayout) viewHolder.getView(R.id.ll_copy)).setVisibility(View.VISIBLE);
            } else if (item.getIs_copy() == 2) {
                ((LinearLayout) viewHolder.getView(R.id.ll_copy)).setVisibility(View.GONE);
            }
            if (item.getIs_join() == 1) {
                ((LinearLayout) viewHolder.getView(R.id.ll_join)).setVisibility(View.VISIBLE);
            } else if (item.getIs_join() == 2) {
                ((LinearLayout) viewHolder.getView(R.id.ll_join)).setVisibility(View.GONE);
            }
            if (item.getIs_click() == 1) {
                ((LinearLayout) viewHolder.getView(R.id.ll_hits)).setVisibility(View.VISIBLE);
            } else if (item.getIs_click() == 2) {
                ((LinearLayout) viewHolder.getView(R.id.ll_hits)).setVisibility(View.GONE);
            }
            ((TextView) viewHolder.getView(R.id.tv_time)).setText(item.getCreate_time());
            ((TextView) viewHolder.getView(R.id.tv_type)).setText(item.getType_msg() + item.getType());
            ((TextView) viewHolder.getView(R.id.tv_join_num)).setText(item.getJoin());
            ((TextView) viewHolder.getView(R.id.tv_copy_num)).setText(item.getCopy());
            ((TextView) viewHolder.getView(R.id.tv_hits)).setText(item.getExposure_num());
            ((TextView) viewHolder.getView(R.id.tv_consume)).setText(item.getMoney());
            ((TextView) viewHolder.getView(R.id.tv_money)).setText("余额：" + item.getYy_money());
        }
    }
}
