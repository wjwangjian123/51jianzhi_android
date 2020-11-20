package com.part.jianzhiyi.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fendasz.moku.planet.constants.MokuConstants;
import com.fendasz.moku.planet.entity.APIOperationCallBack;
import com.fendasz.moku.planet.entity.ApiDataCallBack;
import com.fendasz.moku.planet.entity.OperationEnum;
import com.fendasz.moku.planet.helper.ApiTaskOperationHelper;
import com.fendasz.moku.planet.source.bean.ClientDetailTaskData;
import com.fendasz.moku.planet.source.bean.ClientTaskDataSubmitFormModel;
import com.fendasz.moku.planet.source.bean.TaskDataApplyRecord;
import com.fendasz.moku.planet.utils.LogUtils;
import com.fendasz.moku.planet.utils.SystemUtils;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuTaskListEntity;
import com.part.jianzhiyi.mogu.base.BaseMoguActivity;
import com.part.jianzhiyi.mogu.dialog.LoadingDialog;
import com.part.jianzhiyi.mogu.helper.TaskDetailActivityHelper;
import com.part.jianzhiyi.mogu.utils.FileUtils;
import com.part.jianzhiyi.mogu.utils.PhoneScreenUtils;
import com.part.jianzhiyi.mogu.view.TaskDetailView;
import com.part.jianzhiyi.mvp.contract.moku.MokuContract;
import com.part.jianzhiyi.mvp.presenter.moku.MokuPresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class TaskDetailActivity extends BaseMoguActivity implements APIOperationCallBack, MokuContract.IMokuView {

    public static final String TAG = TaskDetailActivity.class.getSimpleName();

    //静态视图中的 全局控件

    /**
     * 可控的根布局
     */
    private RelativeLayout mContentView;
    /**
     * 任务进行中的控件
     */
    private LinearLayout mLlOnTask;
    /**
     * 提交任务等所依赖的“按钮”
     */
    private RelativeLayout mRlDownload;
    /**
     * 下载进度条
     */
    private ProgressBar mPbDownload;
    /**
     * 下载按钮中的文字信息
     */
    private TextView mTvDownload;
    private TextView mTvEndTime;
    /**
     * 取消任务按钮(文字)
     */
    private TextView mTvCancelTask;
    /**
     * 开始任务按钮
     */
    private Button mBtnStartTask;


    //动态视图中 基础信息模块中的 全局控件


    //动态视图中 任务说明模块中的 全局控件


    //动态视图中 任务步骤模块中的 全局控件
    /**
     * 评论内容
     */
    private TextView mTvCommentContent;
    /**
     * 复制评论按钮
     */
    private TextView mTvBtnCommentCopy;
    /**
     * 关键词任务复制框
     */
    private RelativeLayout mRlSearchTerms;
    /**
     * 关键词任务复制内容容器
     */
    private TextView mTvSearchTerms;


    //动态视图中 任务提交模块中的 全局控件


    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 屏幕适配帮助实例
     */
    private PhoneScreenUtils mPhoneScreenUtils;
    /**
     * 子任务id
     */
    private int mTaskDataId;
    /**
     * 用户id
     */
    private String mUserId;
    /**
     * 任务详情
     */
    ClientDetailTaskData mClientDetailTaskData;
    /**
     * 业务帮助类
     */
    private TaskDetailActivityHelper myHelper;
    /**
     * 需要上传的图片列表
     */
    private List<File> fileUploadImageList;
    /**
     * 上传图片控件列表
     */
    private List<ImageView> ivUploadImageList;
    /**
     * 上传表单控件列表
     */
    private List<EditText> etFormList;
    /**
     * 上传表单数据
     */
    private List<ClientTaskDataSubmitFormModel> formDatas;
    /**
     * 任务操作帮助类
     */
    private ApiTaskOperationHelper mApiTaskOperationHelper;
    /**
     * 视图层
     */
    private TaskDetailView mTaskDetailView;
    /**
     * 到期时间
     */
    private String mExpirationTime;
    private MokuPresenter mPresenter;
    private ImageView mTaskIvReturn;

    /**
     * 获取根布局时调用
     *
     * @return 需要返回一个可控的根布局
     */
    @Override
    protected View getContentView() {
        mContext = this;
        mPhoneScreenUtils = PhoneScreenUtils.getInstance(mContext);

        mContentView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.activity_task_detail, null);
        return mContentView;
    }

    /**
     * 根布局创建结束后调用
     */
    @Override
    protected void onLoad() {
        initLoad();
        mTaskDetailView.initStaticView();
        initStaticViewListener();
        onReady();
    }

    /**
     * 获取静态控件对象
     *
     * @param llOnTask     任务进行中的控件
     * @param rlDownload   提交任务等所依赖的“按钮”
     * @param tvDownload   下载按钮中的文字信息
     * @param btnStartTask 开始任务按钮
     * @param tvCancelTask 取消任务按钮(文字)
     * @param pbDownload   下载进度条
     */
    public void initStaticView(LinearLayout llOnTask, RelativeLayout rlDownload, TextView tvDownload, Button btnStartTask, TextView tvCancelTask, ProgressBar pbDownload, TextView tvEndTime) {
        mLlOnTask = llOnTask;
        mRlDownload = rlDownload;
        mTvDownload = tvDownload;
        mBtnStartTask = btnStartTask;
        mTvCancelTask = tvCancelTask;
        mPbDownload = pbDownload;
        mTvEndTime = tvEndTime;
    }

    private void onReady() {
        LoadingDialog.Builder builder = new LoadingDialog.Builder(mContext);
        LoadingDialog loadingDialog = builder.create();
        loadingDialog.show();
        //获取任务详情
        myHelper.getTaskDetail(mTaskDataId, new ApiDataCallBack<ClientDetailTaskData>() {
            @Override
            public void success(int code, ClientDetailTaskData data) {
                loadingDialog.dismiss();
                mClientDetailTaskData = data;
                if (data != null && data.getTaskDataApplyRecord() != null && data.getTaskDataApplyRecord().getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                    //申请中
                    mExpirationTime = data.getTaskDataApplyRecord().getExpirationTime();
                }
                LogUtils.log(TAG, JSON.toJSONString(mClientDetailTaskData));
                //获取到任务后，更新页面数据
                mTaskDetailView.initDynamicView(mClientDetailTaskData, fileUploadImageList, ivUploadImageList, etFormList, formDatas);
                //初始化动态加载控件的事件
                initDynamicViewListener();

                mApiTaskOperationHelper = new ApiTaskOperationHelper.HelperBuilder(mClientDetailTaskData).setCallBack(TaskDetailActivity.this).create(mContext);
                mApiTaskOperationHelper.init();
            }

            @Override
            public void error(int code, String message) {
                LogUtils.log(TAG, "code:" + code + " message:" + message);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * 获取动态控件对象
     *
     * @param tvCommentContent 评论内容
     * @param tvBtnCommentCopy 复制评论按钮
     * @param rlSearchTerms    关键词任务复制框
     * @param tvSearchTerms    关键词任务复制内容容器
     */
    public void initDynamicView(TextView tvCommentContent, TextView tvBtnCommentCopy, RelativeLayout rlSearchTerms, TextView tvSearchTerms) {
        mTvCommentContent = tvCommentContent;
        mTvBtnCommentCopy = tvBtnCommentCopy;
        mRlSearchTerms = rlSearchTerms;
        mTvSearchTerms = tvSearchTerms;
    }

    /**
     * 初始化静态控件的事件
     */
    private void initStaticViewListener() {
        if (mRlDownload != null) {
            mRlDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OperationEnum operationEnum = mApiTaskOperationHelper.getOperationEnum();
                    switch (operationEnum) {
                        case SUCCESS_APPLY:
                            //任务进行中
                            break;
                        case SUCCESS_DOWNLOAD:
                            //下载应用市场
                        case SUCCESS_DOWNLOAD_APP:
                            //下载应用
                        case SUCCESS_DOWNLOAD_LOADING:
                            //正在下载
                        case SUCCESS_CONTINUE_DOWNLOAD:
                            //继续下载
                        case ERROR_DOWNLOAD:
                            //下载失败
                            break;
                        case SUCCESS_OPEN:
                            //打开应用市场
                        case SUCCESS_OPEN_APP:
                            //打开应用
                            break;
                        case SUCCESS_INSTALL:
                            //安装应用市场
                            break;
                        case SUCCESS_INSTALL_APP:
                            //安装应用
                            break;
                        case SUCCESS_CONTINUE_DEMO:
                            //继续试玩
                            break;
                        case SUCCESS_SUBMIT:
                            //提交任务
                            MobclickAgent.onEvent(TaskDetailActivity.this, "moku_taskinfo_submit");
                            submitTask();
                            break;
                        case ERROR_OVERTIME:
                            //任务超时
                            break;
                        case ERROR_TAKEUP:
                            //任务已抢完
                            break;
                        default:
                            break;
                    }
                    mApiTaskOperationHelper.startTask();
                }
            });
        }
        if (mBtnStartTask != null) {
            //开始任务按钮的点击事件
            mBtnStartTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobclickAgent.onEvent(TaskDetailActivity.this, "moku_taskinfo_start");
                    LoadingDialog.Builder builder = new LoadingDialog.Builder(mContext);
                    LoadingDialog loadingDialog = builder.create();
                    loadingDialog.show();
                    //判断是否有正在进行中的任务
                    myHelper.getTaskDataStatus(new ApiDataCallBack<TaskDataApplyRecord>() {
                        @Override
                        public void success(int code, TaskDataApplyRecord data) throws Exception {
                            if (data != null) {
                                loadingDialog.dismiss();
                                //获取已经存在的任务
                                Toast.makeText(mContext, "其他任务正在进行中", Toast.LENGTH_SHORT).show();
                                LogUtils.log(TAG, "another task is applying while the taskId is " + data.getTaskDataId() + " and applyId is " + data.getId());
                                ClientDetailTaskData clientDetailTaskData = new ClientDetailTaskData();
                                clientDetailTaskData.setTaskDataId(data.getTaskDataId());
                                clientDetailTaskData.setTaskDataApplyRecord(data);
                                myHelper.cancelTask(clientDetailTaskData, new ApiDataCallBack<TaskDataApplyRecord>() {
                                    @Override
                                    public void success(int code, TaskDataApplyRecord data) throws Exception {
                                        Toast.makeText(mContext, "任务取消成功", Toast.LENGTH_SHORT).show();
                                        mPresenter.getAddTask(String.valueOf(mClientDetailTaskData.getTaskDataId()), PreferenceUUID.getInstence().getUserId(), mClientDetailTaskData.getShowName(), mClientDetailTaskData.getIconUrl(), mClientDetailTaskData.getShowMoney() + "", mClientDetailTaskData.getDesc(), "3", 3);
                                    }

                                    @Override
                                    public void error(int code, String message) throws Exception {
                                        LogUtils.log(TAG, "code:" + code + " message:" + message);
                                        Toast.makeText(mContext, "任务取消失败" + message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                if (mApiTaskOperationHelper.checkStartTask()) {
                                    //申请任务
                                    myHelper.applyTask(mClientDetailTaskData.getTaskDataId(), new ApiDataCallBack<TaskDataApplyRecord>() {
                                        @Override
                                        public void success(int code, TaskDataApplyRecord data) throws Exception {
                                            loadingDialog.dismiss();
                                            LogUtils.log(TAG, "TaskDataApplyRecord >> " + JSON.toJSONString(data));
                                            Toast.makeText(mContext, "任务开始了，按任务要求完成吧~", Toast.LENGTH_SHORT).show();
                                            mClientDetailTaskData.setTaskDataApplyRecord(data);
                                            mExpirationTime = data.getExpirationTime();
                                            //任务申请成功后，表单可以输入了
                                            for (int i = 0; i < etFormList.size(); i++) {
                                                etFormList.get(i).setEnabled(true);
                                            }
                                            if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_COMMENT)) {
                                                //评论任务在申请成功后需要改变评论内容的状态为可见
                                                if (data != null && data.getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                                                    //已经申请了任务
                                                    if (mTvCommentContent != null) {
                                                        mTvCommentContent.setEnabled(true);
                                                        String taskDataCommentData = "";
                                                        if (!TextUtils.isEmpty(data.getTaskDataCommentData().getTaskDataCommentData())) {
                                                            taskDataCommentData = data.getTaskDataCommentData().getTaskDataCommentData();
                                                        }
                                                        mTvCommentContent.setText(taskDataCommentData);
                                                    }
                                                    if (mTvBtnCommentCopy != null) {
                                                        mTvBtnCommentCopy.setEnabled(true);
                                                    }
                                                }
                                                if (mTvCommentContent != null && mTvCommentContent.isEnabled()) {
                                                    SystemUtils.copyToClipboard((Activity) mContext, "comment", mTvCommentContent.getText().toString());
                                                    Toast.makeText(mContext, "评论已复制到剪贴板", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            mApiTaskOperationHelper.init();
                                            mApiTaskOperationHelper.startTask();
                                            mPresenter.getAddTask(String.valueOf(mClientDetailTaskData.getTaskDataId()), PreferenceUUID.getInstence().getUserId(), mClientDetailTaskData.getShowName(), mClientDetailTaskData.getIconUrl(), mClientDetailTaskData.getShowMoney() + "", mClientDetailTaskData.getDesc(), "1", 1);
                                        }

                                        @Override
                                        public void error(int code, String message) throws Exception {
                                            loadingDialog.dismiss();
                                            LogUtils.log(TAG, "code:" + code + " message:" + message);
                                            Toast.makeText(mContext, "任务申请失败 " + message, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    loadingDialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void error(int code, String message) throws Exception {
                            loadingDialog.dismiss();
                            LogUtils.log(TAG, "code:" + code + " message:" + message);
                            Toast.makeText(mContext, "获取进行中的任务失败 " + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        if (mTvCancelTask != null) {
            //取消按钮的点击事件
            mTvCancelTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobclickAgent.onEvent(TaskDetailActivity.this, "moku_taskinfo_cancel");
                    LoadingDialog.Builder builder = new LoadingDialog.Builder(mContext);
                    LoadingDialog loadingDialog = builder.create();
                    loadingDialog.show();
                    //取消任务
                    myHelper.cancelTask(mClientDetailTaskData, new ApiDataCallBack<TaskDataApplyRecord>() {
                        @Override
                        public void success(int code, TaskDataApplyRecord data) throws Exception {
                            loadingDialog.dismiss();
                            Toast.makeText(mContext, "取消成功", Toast.LENGTH_SHORT).show();
                            mClientDetailTaskData.setTaskDataApplyRecord(data);
                            for (int i = 0; i < etFormList.size(); i++) {
                                etFormList.get(i).setEnabled(false);
                            }
                            if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_COMMENT)) {
                                //评论任务在任务取消后需要隐藏评论内容
                                if (data == null || !data.getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                                    if (mTvCommentContent != null) {
                                        mTvCommentContent.setEnabled(false);
                                        mTvCommentContent.setText("请先开始任务");
                                    }
                                    if (mTvBtnCommentCopy != null) {
                                        mTvBtnCommentCopy.setEnabled(false);
                                    }
                                }
                            }
                            mApiTaskOperationHelper.cancelTask();
                            mPresenter.getAddTask(String.valueOf(mClientDetailTaskData.getTaskDataId()), PreferenceUUID.getInstence().getUserId(), mClientDetailTaskData.getShowName(), mClientDetailTaskData.getIconUrl(), mClientDetailTaskData.getShowMoney() + "", mClientDetailTaskData.getDesc(), "3", 3);
                        }

                        @Override
                        public void error(int code, String message) throws Exception {
                            loadingDialog.dismiss();
                            LogUtils.log(TAG, "code:" + code + " message:" + message);
                            Toast.makeText(mContext, "取消失败 " + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    /**
     * 提交任务
     */
    private void submitTask() {
        for (int i = 0; i < fileUploadImageList.size(); i++) {
            if (fileUploadImageList.get(i) == null) {
                Toast.makeText(mContext, "图片" + (i + 1) + "不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        for (int i = 0; i < etFormList.size(); i++) {
            if (TextUtils.isEmpty(etFormList.get(i).getText().toString())) {
                Toast.makeText(mContext, "请输入" + formDatas.get(i).getKey(), Toast.LENGTH_SHORT).show();
                return;
            } else {
                formDatas.get(i).setValue(etFormList.get(i).getText().toString());
            }
        }
        //检测结束，数据全部不为空
        LoadingDialog.Builder builder = new LoadingDialog.Builder(mContext);
        LoadingDialog loadingDialog = builder.create();
        loadingDialog.show();
        //任务提交
        myHelper.submitTask(mClientDetailTaskData, fileUploadImageList, formDatas, new ApiDataCallBack<String>() {
            @Override
            public void success(int code, String data) throws Exception {
                loadingDialog.dismiss();
                LogUtils.log(TAG, JSON.toJSONString(data));
                Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                mPresenter.getAddTask(String.valueOf(mClientDetailTaskData.getTaskDataId()), PreferenceUUID.getInstence().getUserId(), mClientDetailTaskData.getShowName(), mClientDetailTaskData.getIconUrl(), mClientDetailTaskData.getShowMoney() + "", mClientDetailTaskData.getDesc(), "0", 0);
            }

            @Override
            public void error(int code, String message) throws Exception {
                loadingDialog.dismiss();
                LogUtils.log(TAG, "code:" + code + " message:" + message);
                Toast.makeText(mContext, "提交失败 " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initLoad() {
        Intent intent = getIntent();
        mTaskDataId = intent.getIntExtra("taskDataId", -1);
        mUserId = intent.getStringExtra("userId");
        fileUploadImageList = new ArrayList<>();
        ivUploadImageList = new ArrayList<>();
        etFormList = new ArrayList<>();
        formDatas = new ArrayList<>();
        myHelper = new TaskDetailActivityHelper(mContext, mUserId);
        mTaskDetailView = new TaskDetailView(mContext, mContentView, mPhoneScreenUtils);
        mTaskIvReturn = findViewById(R.id.task_iv_return);
        mPresenter = new MokuPresenter(this);
        mTaskIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(TaskDetailActivity.this, "moku_taskinfo_back");
                finish();
            }
        });
    }

    private void initDynamicViewListener() {
        if (mTvBtnCommentCopy != null) {
            mTvBtnCommentCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTvCommentContent != null && mTvCommentContent.isEnabled()) {
                        SystemUtils.copyToClipboard(mContext, "comment", mTvCommentContent.getText().toString());
                        Toast.makeText(mContext, "评论已复制到剪贴板", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (mRlSearchTerms != null) {
            mRlSearchTerms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTvSearchTerms != null) {
                        SystemUtils.copyToClipboard(mContext, MokuConstants.TASK_TYPE_KEYWORD, mTvSearchTerms.getText().toString());
                        Toast.makeText(mContext, "已复制搜索词", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Uri uri = data.getData();
        String imagePath = FileUtils.getUriPath(this, uri);
        fileUploadImageList.set(requestCode, new File(imagePath));
        ImageView ivUploadImage = ivUploadImageList.get(requestCode);
        ivUploadImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mApiTaskOperationHelper != null) {
            mApiTaskOperationHelper.onRestart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mApiTaskOperationHelper != null) {
            mApiTaskOperationHelper.onDestroy();
        }
        if (mPresenter == null) {
            return;
        }
        mPresenter.detachView();
    }

    /**
     * 回调任务操作状态
     *
     * @param operationEnum 任务操作状态
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void taskStatus(OperationEnum operationEnum) {
        LogUtils.log(TAG, "operationEnum >> " + operationEnum.getCode() + ":" + operationEnum.getMessage());
        if (operationEnum.equals(OperationEnum.SUCCESS_START)) {
            //开始任务
            mBtnStartTask.setVisibility(View.VISIBLE);
            mLlOnTask.setVisibility(View.GONE);
            mTvEndTime.setVisibility(View.GONE);
        } else {
            mBtnStartTask.setVisibility(View.GONE);
            mLlOnTask.setVisibility(View.VISIBLE);
            mPbDownload.setProgress(100);
            switch (operationEnum) {
                case SUCCESS_APPLY:
                    //任务进行中
                    for (int i = 0; i < etFormList.size(); i++) {
                        etFormList.get(i).setEnabled(true);
                    }
                    break;
                case SUCCESS_DOWNLOAD:
                    //下载应用市场
                    mTvDownload.setText("下载应用市场");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("截止时间：" + mExpirationTime));
                    break;
                case SUCCESS_DOWNLOAD_APP:
                    //下载应用
                    mTvDownload.setText("下载应用");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("截止时间：" + mExpirationTime));
                    break;
                case SUCCESS_DOWNLOAD_LOADING:
                    //正在下载
                case SUCCESS_CONTINUE_DOWNLOAD:
                    //继续下载
                case ERROR_DOWNLOAD:
                    //下载失败
                    break;
                case SUCCESS_OPEN:
                    //打开应用市场
                    mTvDownload.setText("打开应用市场");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("截止时间：" + mExpirationTime));
                    break;
                case SUCCESS_OPEN_APP:
                    //打开应用
                    mTvDownload.setText("打开应用");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("截止时间：" + mExpirationTime));
                    break;
                case SUCCESS_INSTALL:
                    //安装应用市场
                    mTvDownload.setText("安装应用市场");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("截止时间：" + mExpirationTime));
                    break;
                case SUCCESS_INSTALL_APP:
                    //安装应用
                    mTvDownload.setText("安装应用");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("截止时间：" + mExpirationTime));
                    break;
                case SUCCESS_CONTINUE_DEMO:
                    mTvDownload.setText("继续试玩");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("截止时间：" + mExpirationTime));
                    //继续试玩
                    break;
                case SUCCESS_SUBMIT:
                    //提交任务
                    mTvDownload.setText("提交任务");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("截止时间：" + mExpirationTime));
                    break;
                case ERROR_OVERTIME:
                    //任务超时
                    mTvDownload.setText("任务超时");
                    mPbDownload.setProgress(0);
                    break;
                case ERROR_TAKEUP:
                    //任务已抢完
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 回调应用下载进度
     *
     * @param operationEnum 任务的操作状态获取
     * @param progress      下载的进度获取
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void downloadProgress(OperationEnum operationEnum, String progress) {
        LogUtils.log(TAG, "operationEnum >> " + operationEnum.getCode() + ":" + operationEnum.getMessage() + " progress >> " + progress);
        int progressValue = (int) (Double.parseDouble(progress));
        String strProgress = progressValue + "%";
        if (operationEnum.equals(OperationEnum.SUCCESS_DOWNLOAD_APP)) {
            mTvDownload.setText("下载应用 " + strProgress);
        } else if (operationEnum.equals(OperationEnum.SUCCESS_DOWNLOAD)) {
            mTvDownload.setText("下载应用市场 " + strProgress);
        }
        LogUtils.log(TAG, "strProgress:" + strProgress);
        mPbDownload.setProgress(progressValue);
        LogUtils.log(TAG, "progressValue:" + progressValue);
    }

    /**
     * 回调关键词任务任务体验时间
     *
     * @param operationEnum 任务的操作状态获取
     * @param time          剩余监听时长（单位分钟）
     */
    @Override
    public void listenerTime(OperationEnum operationEnum, int time) {
        LogUtils.log(TAG, "operationEnum >> " + operationEnum.getCode() + ":" + operationEnum.getMessage() + " time >> " + time + " 分钟");
        Toast.makeText(mContext, "需要继续试玩 " + time + " 分钟", Toast.LENGTH_SHORT).show();
    }

    /**
     * 回调评论任务的评论内容
     *
     * @param operationEnum 任务的操作状态获取
     * @param content       需要复制的内容
     */
    @Override
    public void copyContent(OperationEnum operationEnum, String content) {
        LogUtils.log(TAG, "operationEnum >> " + operationEnum.getCode() + ":" + operationEnum.getMessage() + " content " + content);
    }

    @Override
    public void updategetTaskList(MokuTaskListEntity responseData) {

    }

    @Override
    public void updategetAddTask(ResponseData responseData, int statusid) {
        if (statusid == 0) {
            ((Activity) mContext).finish();
        }
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void startIntent() {

    }
}
