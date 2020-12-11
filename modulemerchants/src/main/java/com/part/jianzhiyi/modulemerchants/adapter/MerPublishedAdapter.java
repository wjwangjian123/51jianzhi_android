package com.part.jianzhiyi.modulemerchants.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class MerPublishedAdapter extends RecyclerView.Adapter<MerPublishedAdapter.ViewHolder> {

    protected Context mContext;
    protected List<MJobListEntity.DataBeanX.DataBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerItemClickListener mOnItemClickListener;
    private static OnRefreshClickListener mOnRefreshClickListener;
    private static OnSetStatusItemClickListener onSetStatusItemClickListener;

    public MerPublishedAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MJobListEntity.DataBeanX.DataBean> mList) {
        this.mDatas = mList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_published_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            viewHolder.mTvTitle.setText(mDatas.get(position).getTitle());
            viewHolder.mTvId.setText("ID：" + mDatas.get(position).getId());
            viewHolder.mTvVisitorNum.setText(mDatas.get(position).getExposure_num() + "");
            viewHolder.mTvSignupNum.setText(mDatas.get(position).getJoin() + "");
            viewHolder.mTvCopyNum.setText(mDatas.get(position).getCopy() + "");
            if (mDatas.get(position).getIs_pay() == 0) {
                viewHolder.mTvRefresh.setVisibility(View.VISIBLE);
                viewHolder.mTvStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSetStatusItemClickListener != null) {
                            onSetStatusItemClickListener.onItemSetStatusClick(position, mDatas.get(position).getId(), mDatas.get(position).getStatus());
                        }
                    }
                });
                if (mDatas.get(position).getStatus() == 1) {
                    if (mDatas.get(position).getSx() == 1) {
                        viewHolder.mTvRefresh.setText("刷新");
                        viewHolder.mTvRefresh.setTextColor(Color.parseColor("#ffffff"));
                        viewHolder.mTvRefresh.setBackgroundResource(R.drawable.shape_bg_orange4);
                    } else if (mDatas.get(position).getSx() == 0) {
                        viewHolder.mTvRefresh.setText(mDatas.get(position).getZd());
                        viewHolder.mTvRefresh.setTextColor(Color.parseColor("#A1A7B7"));
                        viewHolder.mTvRefresh.setBackgroundResource(R.drawable.shape_refresh_grey4);
                    }
                    viewHolder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_mer_up, 0);
                    viewHolder.mTvRefresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnRefreshClickListener != null) {
                                mOnRefreshClickListener.onItemRefreshClick(position, mDatas.get(position).getId());
                            }
                        }
                    });
                } else if (mDatas.get(position).getStatus() == 4) {
                    viewHolder.mTvRefresh.setText("刷新");
                    viewHolder.mTvRefresh.setTextColor(Color.parseColor("#A1A7B7"));
                    viewHolder.mTvRefresh.setBackgroundResource(R.drawable.shape_refresh_grey4);
                    viewHolder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_mer_position_down, 0);
                    viewHolder.mTvRefresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            } else if (mDatas.get(position).getIs_pay() == 1) {
                viewHolder.mTvRefresh.setVisibility(View.GONE);
                viewHolder.mTvStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                viewHolder.mTvRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                viewHolder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
            if (mDatas.get(position).getIs_copy() == 1) {
                viewHolder.mLlCopy.setVisibility(View.VISIBLE);
            } else if (mDatas.get(position).getIs_copy() == 2) {
                viewHolder.mLlCopy.setVisibility(View.GONE);
            }
            if (mDatas.get(position).getIs_join() == 1) {
                viewHolder.mLlSignup.setVisibility(View.VISIBLE);
            } else if (mDatas.get(position).getIs_join() == 2) {
                viewHolder.mLlSignup.setVisibility(View.GONE);
            }
            if (mDatas.get(position).getIs_click() == 1) {
                viewHolder.mLlVisitor.setVisibility(View.VISIBLE);
            } else if (mDatas.get(position).getIs_click() == 2) {
                viewHolder.mLlVisitor.setVisibility(View.GONE);
            }
            if (mDatas.get(position).getIs_click() == 1 || mDatas.get(position).getIs_join() == 1 || mDatas.get(position).getIs_copy() == 1) {
                viewHolder.mView1.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mView1.setVisibility(View.GONE);
            }
            viewHolder.mTvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, mDatas.get(position).getId());
                    }
                }
            });
            if (mDatas.get(position).getStatus() == 1) {
                viewHolder.mTvStatus.setText("招聘中");
                viewHolder.mTvTitle.setTextColor(Color.parseColor("#33363C"));
                viewHolder.mTvId.setTextColor(Color.parseColor("#FE954A"));
                viewHolder.mTvVisitorNum.setTextColor(Color.parseColor("#33363C"));
                viewHolder.mTvVisitor.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvSignupNum.setTextColor(Color.parseColor("#33363C"));
                viewHolder.mTvSignup.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvCopyNum.setTextColor(Color.parseColor("#33363C"));
                viewHolder.mTvCopy.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvStatus.setTextColor(Color.parseColor("#FE954A"));
                viewHolder.mTvStatus.setBackgroundResource(R.drawable.shape_bg_auth);
            } else if (mDatas.get(position).getStatus() == 4) {
                viewHolder.mTvStatus.setText("已下线");
                viewHolder.mTvTitle.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvId.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvVisitorNum.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvVisitor.setTextColor(Color.parseColor("#CDD3E1"));
                viewHolder.mTvSignupNum.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvSignup.setTextColor(Color.parseColor("#CDD3E1"));
                viewHolder.mTvCopyNum.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvCopy.setTextColor(Color.parseColor("#CDD3E1"));
                viewHolder.mTvStatus.setTextColor(Color.parseColor("#A1A7B7"));
                viewHolder.mTvStatus.setBackgroundResource(R.drawable.shape_bg_auth_grey);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvId;
        private View mView;
        private TextView mTvVisitorNum;
        private TextView mTvVisitor;
        private LinearLayout mLlVisitor;
        private TextView mTvSignupNum;
        private TextView mTvSignup;
        private LinearLayout mLlSignup;
        private TextView mTvCopyNum;
        private TextView mTvCopy;
        private LinearLayout mLlCopy;
        private LinearLayout mLlNum;
        private View mView1;
        private TextView mTvEdit;
        private TextView mTvRefresh;
        private TextView mTvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvId = (TextView) itemView.findViewById(R.id.tv_id);
            mView = (View) itemView.findViewById(R.id.view);
            mTvVisitorNum = (TextView) itemView.findViewById(R.id.tv_visitor_num);
            mTvVisitor = (TextView) itemView.findViewById(R.id.tv_visitor);
            mLlVisitor = (LinearLayout) itemView.findViewById(R.id.ll_visitor);
            mTvSignupNum = (TextView) itemView.findViewById(R.id.tv_signup_num);
            mTvSignup = (TextView) itemView.findViewById(R.id.tv_signup);
            mLlSignup = (LinearLayout) itemView.findViewById(R.id.ll_signup);
            mTvCopyNum = (TextView) itemView.findViewById(R.id.tv_copy_num);
            mTvCopy = (TextView) itemView.findViewById(R.id.tv_copy);
            mLlCopy = (LinearLayout) itemView.findViewById(R.id.ll_copy);
            mLlNum = (LinearLayout) itemView.findViewById(R.id.ll_num);
            mView1 = (View) itemView.findViewById(R.id.view1);
            mTvEdit = (TextView) itemView.findViewById(R.id.tv_edit);
            mTvRefresh = (TextView) itemView.findViewById(R.id.tv_refresh);
            mTvStatus = (TextView) itemView.findViewById(R.id.tv_status);
        }
    }

    public void setmOnRefreshClickListener(OnRefreshClickListener mOnRefreshClickListener) {
        MerPublishedAdapter.mOnRefreshClickListener = mOnRefreshClickListener;
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        MerPublishedAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnSetStatusItemClickListener(OnSetStatusItemClickListener onSetStatusItemClickListener) {
        MerPublishedAdapter.onSetStatusItemClickListener = onSetStatusItemClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRefreshClickListener {
        void onItemRefreshClick(int position, String id);
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position, String id);
    }

    public interface OnSetStatusItemClickListener {
        void onItemSetStatusClick(int position, String id, int status);
    }
}
