package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.JobDetailEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class DialogJoinUpAdapter extends RecyclerView.Adapter<DialogJoinUpAdapter.ViewHolder> {

    protected Context mContext;
    protected List<JobDetailEntity.DataBean.JobListBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerSetClickListener mOnSetClickListener;
//    private static OnRecyclerItemClickListener mOnItemClickListener;

    public DialogJoinUpAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<JobDetailEntity.DataBean.JobListBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_join_up, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            viewHolder.mItemTvTitle.setText(mDatas.get(position).getTitle());
            viewHolder.mItemTvNum.setText(mDatas.get(position).getBrowse_num() + "人正在关注");
            viewHolder.mItemTvPrice1.setText(mDatas.get(position).getPrice1());
            viewHolder.mItemTvPrice2.setText(mDatas.get(position).getPrice2());
            if (mDatas.get(position).getIvType() == 0) {
                viewHolder.mItemIvSelect.setSelected(false);
            } else if (mDatas.get(position).getIvType() == 1) {
                viewHolder.mItemIvSelect.setSelected(true);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnSetClickListener != null) {
                        mOnSetClickListener.onSetClick(position, mDatas.get(position).getId());
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

        private TextView mItemTvTitle;
        private TextView mItemTvNum;
        private TextView mItemTvPrice1;
        private TextView mItemTvPrice2;
        private ImageView mItemIvSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemTvTitle = (TextView) itemView.findViewById(R.id.item_tv_title);
            mItemTvNum = (TextView) itemView.findViewById(R.id.item_tv_num);
            mItemTvPrice1 = (TextView) itemView.findViewById(R.id.item_tv_price1);
            mItemTvPrice2 = (TextView) itemView.findViewById(R.id.item_tv_price2);
            mItemIvSelect = (ImageView) itemView.findViewById(R.id.item_iv_select);
        }
    }

//    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
//        DialogJoinUpAdapter.mOnItemClickListener = mOnItemClickListener;
//    }

    public void setmOnSetClickListener(OnRecyclerSetClickListener mOnSetClickListener) {
        DialogJoinUpAdapter.mOnSetClickListener = mOnSetClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRecyclerSetClickListener {
        void onSetClick(int position, String id);
    }

//    public interface OnRecyclerItemClickListener {
//        void onItemSelectClick(int position, String id);
//    }
}
