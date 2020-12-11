package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.base.adapter.CustomBaseAdapter;
import com.part.jianzhiyi.corecommon.base.adapter.ViewHolder;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.model.entity.ChatEntity;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class ChatAdapter extends CustomBaseAdapter<ChatEntity> {

    private Context context;
    private List<String> mStringList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnItemCopyClickListener mOnItemCopyClickListener;

    public ChatAdapter(Context context, List<ChatEntity> list) {
        super(context, R.layout.item_chat_list, list);
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemCopyClickListener(OnItemCopyClickListener onItemCopyClickListener) {
        mOnItemCopyClickListener = onItemCopyClickListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ChatEntity item, int position) {
        if (mStringList.size() > 0) {
            mStringList.clear();
        }
        String sex;
        String place;
        String method;
        if (item != null) {
            if (item.getDataBean() == null) {
                ((RelativeLayout) viewHolder.getView(R.id.chat_tv_relative)).setVisibility(View.GONE);
                if (item.getDataList() == null) {
                    if (item.getImg1() == null || item.getImg1() == "") {
                        FrescoUtil.setResPic(R.drawable.icon_pic1, ((SimpleDraweeView) viewHolder.getView(R.id.chat_iv_img)));
                    } else {
                        FrescoUtil.setHttpPic(item.getImg1(), ((SimpleDraweeView) viewHolder.getView(R.id.chat_iv_img)));
                    }
                    ((LinearLayout) viewHolder.getView(R.id.chat_tv_linear)).setVisibility(View.VISIBLE);
                    ((TextView) viewHolder.getView(R.id.chat_tv_content)).setVisibility(View.GONE);
                    ((TextView) viewHolder.getView(R.id.chat_tv_content1)).setText(item.getMsg1());
                    ((TextView) viewHolder.getView(R.id.chat_tv_copy)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemCopyClickListener == null) {
                                return;
                            }
                            mOnItemCopyClickListener.onItemCopy(position);
                        }
                    });
                } else {
                    if (item.getDataList().getImg1() == null || item.getDataList().getImg1() == "") {
                        FrescoUtil.setResPic(R.drawable.icon_pic1, ((SimpleDraweeView) viewHolder.getView(R.id.chat_iv_img)));
                    } else {
                        FrescoUtil.setHttpPic(item.getDataList().getImg1(), ((SimpleDraweeView) viewHolder.getView(R.id.chat_iv_img)));
                    }
                    ((TextView) viewHolder.getView(R.id.chat_tv_content)).setVisibility(View.VISIBLE);
                    ((LinearLayout) viewHolder.getView(R.id.chat_tv_linear)).setVisibility(View.GONE);
                    ((TextView) viewHolder.getView(R.id.chat_tv_content)).setText(item.getDataList().getMsg1());
                    ((TextView) viewHolder.getView(R.id.chat_tv_copy)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
                ((RelativeLayout) viewHolder.getView(R.id.chat_tv_relative)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((TextView) viewHolder.getView(R.id.chat_tv_info)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } else {
                if (item.getDataBean().getImg1() == null || item.getDataBean().getImg1() == "") {
                    FrescoUtil.setResPic(R.drawable.icon_pic1, ((SimpleDraweeView) viewHolder.getView(R.id.chat_iv_img)));
                } else {
                    FrescoUtil.setHttpPic(item.getDataBean().getImg1(), ((SimpleDraweeView) viewHolder.getView(R.id.chat_iv_img)));
                }
                ((TextView) viewHolder.getView(R.id.chat_tv_content)).setVisibility(View.GONE);
                ((LinearLayout) viewHolder.getView(R.id.chat_tv_linear)).setVisibility(View.GONE);
                ((RelativeLayout) viewHolder.getView(R.id.chat_tv_relative)).setVisibility(View.VISIBLE);
                ((TextView) viewHolder.getView(R.id.chat_tv_title)).setText(item.getDataBean().getTitle());
                ((TextView) viewHolder.getView(R.id.chat_tv_price)).setText(item.getDataBean().getPrice());
                if (item.getDataBean().getSex() == null || item.getDataBean().getSex() == "") {
                    sex = "男女不限";
                } else {
                    sex = item.getDataBean().getSex();
                }
                if (item.getDataBean().getPlace() == null || item.getDataBean().getPlace() == "") {
                    if (PreferenceUUID.getInstence().getCity() == "" || PreferenceUUID.getInstence().getCity() == null) {
                        place = "不限工作地点";
                    } else {
                        place = PreferenceUUID.getInstence().getCity();
                    }
                } else {
                    place = item.getDataBean().getPlace();
                }
                method = item.getDataBean().getMethod();
                mStringList.add(sex);
                mStringList.add(place);
                mStringList.add(method);
                String content = item.getDataBean().getContent();
                ((TextView) viewHolder.getView(R.id.webView)).setText(Html.fromHtml(content));
                ((TagFlowLayout) viewHolder.getView(R.id.tfl_lable)).setAdapter(new TagAdapter<String>(mStringList) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_chat_flow, ((TagFlowLayout) viewHolder.getView(R.id.tfl_lable)), false);
                        tv.setText(s);
                        return tv;
                    }
                });
                ((RelativeLayout) viewHolder.getView(R.id.chat_tv_relative)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener == null) {
                            return;
                        }
                        mOnItemClickListener.onItem(position, item.getDataBean().getId());
                    }
                });
                ((TextView) viewHolder.getView(R.id.chat_tv_info)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener == null) {
                            return;
                        }
                        mOnItemClickListener.onItem(position, item.getDataBean().getId());
                    }
                });
                ((TextView) viewHolder.getView(R.id.chat_tv_copy)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }
    }

    public interface OnItemClickListener {
        void onItem(int position, String id);
    }

    public interface OnItemCopyClickListener {
        void onItemCopy(int position);
    }
}
