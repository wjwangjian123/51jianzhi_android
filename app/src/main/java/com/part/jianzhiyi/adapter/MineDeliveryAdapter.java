package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class MineDeliveryAdapter extends RecyclerView.Adapter<MineDeliveryAdapter.ViewHolder> {

    protected Context mContext;
    protected List<JobListResponseEntity> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerSetClickListener mOnSetClickListener;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public MineDeliveryAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<JobListResponseEntity> datas) {
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
            viewHolder.mItemTvPrice1.setText(mDatas.get(position).getPrice1());
            viewHolder.mItemTvPrice2.setText(mDatas.get(position).getPrice2());
            viewHolder.mItemTvCompany.setText(mDatas.get(position).getCompany());
            if (mDatas.get(position).getPlace() == null || mDatas.get(position).getPlace() == "") {
                if (PreferenceUUID.getInstence().getCity() == "" || PreferenceUUID.getInstence().getCity() == null) {
                    viewHolder.mItemTvPlace.setText("不限");
                } else {
                    viewHolder.mItemTvPlace.setText(PreferenceUUID.getInstence().getCity());
                }
            } else {
                viewHolder.mItemTvPlace.setText(mDatas.get(position).getPlace());
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
        private TextView mItemTvPrice1;
        private TextView mItemTvPrice2;
        private TextView mItemTvCompany;
        private TextView mItemTvPlace;
        private TextView mItemTvSettlement;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemTvJobname = (TextView) itemView.findViewById(R.id.item_tv_jobname);
            mItemTvPrice1 = (TextView) itemView.findViewById(R.id.item_tv_price1);
            mItemTvPrice2 = (TextView) itemView.findViewById(R.id.item_tv_price2);
            mItemTvCompany = (TextView) itemView.findViewById(R.id.item_tv_company);
            mItemTvPlace = (TextView) itemView.findViewById(R.id.item_tv_place);
            mItemTvSettlement = (TextView) itemView.findViewById(R.id.item_tv_settlement);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        MineDeliveryAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnSetClickListener(OnRecyclerSetClickListener mOnSetClickListener) {
        MineDeliveryAdapter.mOnSetClickListener = mOnSetClickListener;
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
