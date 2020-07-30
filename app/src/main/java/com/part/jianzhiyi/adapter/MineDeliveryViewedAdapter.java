package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class MineDeliveryViewedAdapter extends RecyclerView.Adapter<MineDeliveryViewedAdapter.ViewHolder> {

    protected Context mContext;
    protected List<ViewedEntity.DataBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerSetClickListener mOnSetClickListener;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public MineDeliveryViewedAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<ViewedEntity.DataBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_recycle_toudi, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            viewHolder.mItemTvJobname.setText(mDatas.get(position).getTitle());
            viewHolder.mItemTvPrice.setText(mDatas.get(position).getPrice());
            viewHolder.mItemTvCompany.setText(mDatas.get(position).getName());
            if (mDatas.get(position).getPlace() == null || mDatas.get(position).getPlace() == "") {
                if (PreferenceUUID.getInstence().getCity() == "" || PreferenceUUID.getInstence().getCity() == null) {
                    viewHolder.mItemTvAddress.setText("不限");
                } else {
                    viewHolder.mItemTvAddress.setText(PreferenceUUID.getInstence().getCity());
                }
            } else {
                viewHolder.mItemTvAddress.setText(mDatas.get(position).getPlace());
            }
            if (mDatas.get(position).getTime() == null || mDatas.get(position).getTime() == "") {
                viewHolder.mItemTvTime.setText("不限");
            } else {
                viewHolder.mItemTvTime.setText(mDatas.get(position).getTime());
            }
            viewHolder.mItemTvSettlement.setText(mDatas.get(position).getMethod());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, mDatas.get(position).getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemTvJobname;
        private TextView mItemTvPrice;
        private TextView mItemTvCompany;
        private TextView mItemTvAddress;
        private TextView mItemTvTime;
        private TextView mItemTvSettlement;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemTvJobname = (TextView) itemView.findViewById(R.id.item_tv_jobname);
            mItemTvPrice = (TextView) itemView.findViewById(R.id.item_tv_price);
            mItemTvCompany = (TextView) itemView.findViewById(R.id.item_tv_company);
            mItemTvAddress = (TextView) itemView.findViewById(R.id.item_tv_address);
            mItemTvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
            mItemTvSettlement = (TextView) itemView.findViewById(R.id.item_tv_settlement);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        MineDeliveryViewedAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnSetClickListener(OnRecyclerSetClickListener mOnSetClickListener) {
        MineDeliveryViewedAdapter.mOnSetClickListener = mOnSetClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRecyclerSetClickListener {
        void onSetClick(int position, String nickName, int id);
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position, String id);
    }
}
