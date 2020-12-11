package com.part.jianzhiyi.modulemerchants.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class MerPositionAdapter extends RecyclerView.Adapter<MerPositionAdapter.ViewHolder> {

    protected Context mContext;
    protected List<MLableEntity.DataBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public MerPositionAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MLableEntity.DataBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_text, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            viewHolder.mTvFlowTitle.setText(mDatas.get(position).getTitle());
            if (mDatas.get(position).isSelect()) {
                viewHolder.mTvFlowTitle.setSelected(true);
            } else {
                viewHolder.mTvFlowTitle.setSelected(false);
            }
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

        private TextView mTvFlowTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvFlowTitle = (TextView) itemView.findViewById(R.id.tv_flow_title);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        MerPositionAdapter.mOnItemClickListener = mOnItemClickListener;
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
