package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greendao.gen.MessageResponseEntityDao;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.ChatAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.corecommon.utils.CopyTextLibrary;
import com.part.jianzhiyi.dbmodel.GreenDaoManager;
import com.part.jianzhiyi.model.entity.ChatEntity;
import com.part.jianzhiyi.model.entity.ChatJobInfoEntity;
import com.part.jianzhiyi.model.entity.MessageResponseEntity;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.InformationContract;
import com.part.jianzhiyi.mvp.presenter.InformationPresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.part.jianzhiyi.utils.OpenUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity<InformationPresenter> implements InformationContract.IInformationView {

    private LinearLayout mChatLinearPhone;
    private LinearLayout mChatLinearWechat;
    private LinearLayout mChatLinearZhiwei;
    private TextView mChatTvTime;
    private ListViewInScrollView mLvMessage;
    private ChatAdapter mChatAdapter;
    private List<ChatEntity> mChatEntities;
    private MessageResponseEntity mlist;
    private String phone;
    private String wechat;
    private int type = 1;
    private String sortId;
    private String id;
    private String contact;
    private int contact_type;
    private Handler mHandler;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {
        mChatLinearPhone = (LinearLayout) findViewById(R.id.chat_linear_phone);
        mChatLinearWechat = (LinearLayout) findViewById(R.id.chat_linear_wechat);
        mChatLinearZhiwei = (LinearLayout) findViewById(R.id.chat_linear_zhiwei);
        mChatTvTime = (TextView) findViewById(R.id.chat_tv_time);
        mLvMessage = (ListViewInScrollView) findViewById(R.id.lv_message);
        if (PreferenceUUID.getInstence().getShowWx() == 1) {
            mChatLinearWechat.setVisibility(View.VISIBLE);
        } else {
            mChatLinearWechat.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 0);
        sortId = getIntent().getStringExtra("sortId");
        mHandler = new Handler();
        mChatEntities = new ArrayList<>();
        mChatAdapter = new ChatAdapter(ChatActivity.this, mChatEntities);
        mLvMessage.setAdapter(mChatAdapter);
        if (mChatEntities != null) {
            mChatEntities.clear();
        }
        if (type == 1) {
            MessageResponseEntityDao messageResponseEntityDao = GreenDaoManager.getInstance().getDaoSession().getMessageResponseEntityDao();
            if (messageResponseEntityDao != null) {
                mlist = messageResponseEntityDao.queryBuilder().where(MessageResponseEntityDao.Properties.CompanyId.eq(id)).unique();
                if (mlist != null) {
                    initToolbar(mlist.getCompany());
                    mChatTvTime.setText(mlist.getDateMonth());
                    contact = mlist.getContact();
                    contact_type = mlist.getContactType();

                    ChatEntity chatEntity = new ChatEntity();
                    ChatEntity.DataList dataList = new ChatEntity.DataList();
                    dataList.setImg(mlist.getHeadimg1());
                    dataList.setImg1(mlist.getHeadimg());
                    dataList.setMsg1(mlist.getMsg2());
                    chatEntity.setDataList(dataList);
                    mChatEntities.add(chatEntity);

                    ChatEntity chatEntity1 = new ChatEntity();
                    chatEntity1.setImg(mlist.getHeadimg1());
                    chatEntity1.setImg1(mlist.getHeadimg());
                    chatEntity1.setMsg1(mlist.getMsg3());
                    mChatEntities.add(chatEntity1);

                    mChatAdapter.notifyDataSetChanged();
                }
            }
        } else if (type == 2) {
            mPresenter.getChatJobinfo(id);
        }
        mChatAdapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItem(int position, String id) {
                MobclickAgent.onEvent(ChatActivity.this, "see_mine_position");
                Intent intent = new Intent(ChatActivity.this, VocationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("position", Constants.POSITION_CHAT);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
        mChatAdapter.setOnItemCopyClickListener(new ChatAdapter.OnItemCopyClickListener() {
            @Override
            public void onItemCopy(int position) {
                MobclickAgent.onEvent(ChatActivity.this, "chat_copy");
                if (contact != null && contact != "") {
                    CopyTextLibrary copyButtonLibrary = new CopyTextLibrary(getApplicationContext(), contact);
                    copyButtonLibrary.init();
                    mPresenter.joincopyContact(id, sortId, contact, 3);
                    if (PreferenceUUID.getInstence().getShowWx() == 1) {
                        if (contact_type == 1) {
                            //微信
                            if (OpenUtils.isWeixinAvilible(ChatActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(ChatActivity.this, "微信");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openWx(ChatActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装微信");
                            }
                        } else if (contact_type == 2) {
                            //QQ
                            if (OpenUtils.isQQInstall(ChatActivity.this)) {
                                //弹框
                                OpenUtils.initDialog(ChatActivity.this, "QQ");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //弹框消失
                                        //拉起微信
                                        OpenUtils.cancelDialog();
                                        OpenUtils.openQQ(ChatActivity.this);
                                    }
                                }, 1000);
                            } else {
                                showToast("请安装QQ");
                            }
                        } else {
                            showToast("复制成功");
                        }
                    } else {
                        showToast("复制成功");
                    }
                }
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        mChatLinearPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(ChatActivity.this, "chat_phone");
                showToast("已成功交换联系方式，快去联系他吧");
            }
        });
        mChatLinearWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(ChatActivity.this, "chat_wechat");
                showToast("已成功交换联系方式，快去联系他吧");
            }
        });
        mChatLinearZhiwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    MobclickAgent.onEvent(ChatActivity.this, "chat_see_position");
                } else if (type == 2) {
                    MobclickAgent.onEvent(ChatActivity.this, "see_mine_position");
                }
                //查看职位
                Intent intent = new Intent(ChatActivity.this, VocationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("position", Constants.POSITION_CHAT);
                intent.putExtra("sortId", "" + sortId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected InformationPresenter createPresenter() {
        return new InformationPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updateList(List<MsgResponseEntity> list) {

    }

    @Override
    public void updategetChatJobinfo(ChatJobInfoEntity chatJobInfoEntity) {
        if (chatJobInfoEntity.getData() != null) {
            initToolbar(chatJobInfoEntity.getData().getCompany());
            mChatTvTime.setText(chatJobInfoEntity.getData().getData());
            ChatEntity chatEntity = new ChatEntity();
            ChatEntity.DataList dataList = new ChatEntity.DataList();
            dataList.setImg1(chatJobInfoEntity.getData().getImg());
            dataList.setMsg1(chatJobInfoEntity.getData().getMsg1());
            chatEntity.setDataList(dataList);
            mChatEntities.add(chatEntity);
            ChatEntity chatEntity1 = new ChatEntity();
            ChatEntity.DataBean dataBean = new ChatEntity.DataBean();
            dataBean.setImg1(chatJobInfoEntity.getData().getImg());
            dataBean.setId(chatJobInfoEntity.getData().getId());
            dataBean.setTitle(chatJobInfoEntity.getData().getTitle());
            dataBean.setPrice(chatJobInfoEntity.getData().getPrice());
            dataBean.setMethod(chatJobInfoEntity.getData().getMethod());
            dataBean.setPlace(chatJobInfoEntity.getData().getPlace());
            dataBean.setSex(chatJobInfoEntity.getData().getSex());
            dataBean.setContent(chatJobInfoEntity.getData().getContent());
            chatEntity1.setDataBean(dataBean);
            mChatEntities.add(chatEntity1);
            mChatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateviewedJob(ViewedEntity list) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("聊天页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("聊天页面");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
