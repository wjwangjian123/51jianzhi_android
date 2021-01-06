package com.part.jianzhiyi.modulemerchants.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by jyx on 2020/11/30
 *
 * @author jyx
 * @describe
 */
public class MerTagAdapter extends TagAdapter<MLableEntity.DataBean.ListsBean> {

    private Context mContext;
    private int mposition;
    private static OnItemClickListener onItemClickListener;

    public MerTagAdapter(List<MLableEntity.DataBean.ListsBean> datas, Context context) {
        super(datas);
        mContext = context;
        this.mposition = datas.size();
    }

    @Override
    public View getView(FlowLayout parent, int position, MLableEntity.DataBean.ListsBean listsBean) {
        View inflate;
        if (position == mposition - 1) {
            //如果是最后一个标签，显示不同的布局
            inflate = LayoutInflater.from(mContext).inflate(R.layout.item_text_flow_last, null, false);
            RelativeLayout rlBg = inflate.findViewById(R.id.rl_bg);
            TextView tvTitle = inflate.findViewById(R.id.tv_title);
            EditText etTitle = inflate.findViewById(R.id.et_title);
            tvTitle.setText(listsBean.getTitle());
            rlBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, etTitle.getText().toString().trim());
                    }
                }
            });
        } else {
            inflate = LayoutInflater.from(mContext).inflate(R.layout.item_text_flow, null, false);
            TextView tv = (TextView) inflate;
            tv.setText(listsBean.getTitle());
        }
        return inflate;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        MerTagAdapter.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String etTitle);
    }
}
