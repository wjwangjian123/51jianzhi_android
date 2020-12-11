package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.model.entity.ChoiceEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class ChoiceRecommendAdapter extends RecyclerView.Adapter<ChoiceRecommendAdapter.ViewHolder> {

    protected Context mContext;
    protected List<ChoiceEntity.XiaobianBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public ChoiceRecommendAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<ChoiceEntity.XiaobianBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_choice_recommend, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > 0) {
            if (position % 3 == 0) {
                holder.mItemRl.setBackgroundResource(R.drawable.icon_edition_bg_one);
            }
            if (position % 3 == 1) {
                holder.mItemRl.setBackgroundResource(R.drawable.icon_edition_bg_two);
            }
            if (position % 3 == 2) {
                holder.mItemRl.setBackgroundResource(R.drawable.icon_edition_bg_three);
            }
            holder.mItemRankTitle.setText(mDatas.get(position).getTitle());
            if (mDatas.get(position).getPlace().equals(null) || mDatas.get(position).getPlace().equals("")) {
                if (PreferenceUUID.getInstence().getCity() == "" || PreferenceUUID.getInstence().getCity() == null) {
                    holder.mItemTvPlace.setText("不限");
                } else {
                    holder.mItemTvPlace.setText(PreferenceUUID.getInstence().getCity());
                }
            } else {
                holder.mItemTvPlace.setText(mDatas.get(position).getPlace());
            }
            if (mDatas.get(position).getMethod().equals(null) || mDatas.get(position).getMethod().equals("")) {
                holder.mViewMethod.setVisibility(View.GONE);
            } else {
                holder.mViewMethod.setVisibility(View.VISIBLE);
                holder.mItemTvMethod.setText(mDatas.get(position).getMethod());
            }
            if (mDatas.get(position).getTime() == null || mDatas.get(position).getTime() == "") {
                holder.mItemTvTime.setText("不限");
            } else {
                holder.mItemTvTime.setText(mDatas.get(position).getTime());
            }
            holder.mItemRankPrice1.setText(mDatas.get(position).getPrice1());
            holder.mItemRankPrice2.setText(mDatas.get(position).getPrice2());
            holder.mItemRl.setOnClickListener(new View.OnClickListener() {
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

        private TextView mItemRankTitle;
        private TextView mItemTvPlace;
        private View mViewMethod;
        private TextView mItemTvMethod;
        private TextView mItemTvTime;
        private TextView mItemRankPrice1;
        private TextView mItemRankPrice2;
        private RelativeLayout mItemRl;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemRankTitle = (TextView) itemView.findViewById(R.id.item_rank_title);
            mItemTvPlace = (TextView) itemView.findViewById(R.id.item_tv_place);
            mViewMethod = (View) itemView.findViewById(R.id.view_method);
            mItemTvMethod = (TextView) itemView.findViewById(R.id.item_tv_method);
            mItemTvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
            mItemRankPrice1 = (TextView) itemView.findViewById(R.id.item_rank_price1);
            mItemRankPrice2 = (TextView) itemView.findViewById(R.id.item_rank_price2);
            mItemRl = (RelativeLayout) itemView.findViewById(R.id.item_rl);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        ChoiceRecommendAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRecyclerItemClickListener {
        void onItemClick(int position, String id);
    }
}
