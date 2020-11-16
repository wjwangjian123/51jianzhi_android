package com.part.jianzhiyi.mogu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.moku.KuaibaoEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class WithdrawalAdapter extends RecyclerView.Adapter<WithdrawalAdapter.ViewHolder> {

    protected Context mContext;
    protected List<KuaibaoEntity.DataBean> mDatas;
    protected LayoutInflater mInflater;

    public WithdrawalAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<KuaibaoEntity.DataBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_kb, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            KuaibaoEntity.DataBean dataBean = mDatas.get(position % mDatas.size());//获取重复数据
            viewHolder.mItemTvPhone.setText(dataBean.getPhone());
            viewHolder.mItemTvMoney.setText(dataBean.getMoney() + "元");
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            if (mDatas.size() > 5) {
//            展示无限数据
                return Integer.MAX_VALUE;
            } else {
                return mDatas.size();
            }
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemTvPhone;
        private TextView mItemTvMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemTvPhone = (TextView) itemView.findViewById(R.id.item_tv_phone);
            mItemTvMoney = (TextView) itemView.findViewById(R.id.item_tv_money);
        }
    }
}
