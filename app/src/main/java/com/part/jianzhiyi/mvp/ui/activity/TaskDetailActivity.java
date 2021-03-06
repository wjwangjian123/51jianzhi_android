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

    //?????????????????? ????????????

    /**
     * ??????????????????
     */
    private RelativeLayout mContentView;
    /**
     * ????????????????????????
     */
    private LinearLayout mLlOnTask;
    /**
     * ???????????????????????????????????????
     */
    private RelativeLayout mRlDownload;
    /**
     * ???????????????
     */
    private ProgressBar mPbDownload;
    /**
     * ??????????????????????????????
     */
    private TextView mTvDownload;
    private TextView mTvEndTime;
    /**
     * ??????????????????(??????)
     */
    private TextView mTvCancelTask;
    /**
     * ??????????????????
     */
    private Button mBtnStartTask;


    //??????????????? ???????????????????????? ????????????


    //??????????????? ???????????????????????? ????????????


    //??????????????? ???????????????????????? ????????????
    /**
     * ????????????
     */
    private TextView mTvCommentContent;
    /**
     * ??????????????????
     */
    private TextView mTvBtnCommentCopy;
    /**
     * ????????????????????????
     */
    private RelativeLayout mRlSearchTerms;
    /**
     * ?????????????????????????????????
     */
    private TextView mTvSearchTerms;


    //??????????????? ???????????????????????? ????????????


    /**
     * ?????????
     */
    private Context mContext;

    /**
     * ????????????????????????
     */
    private PhoneScreenUtils mPhoneScreenUtils;
    /**
     * ?????????id
     */
    private int mTaskDataId;
    /**
     * ??????id
     */
    private String mUserId;
    /**
     * ????????????
     */
    ClientDetailTaskData mClientDetailTaskData;
    /**
     * ???????????????
     */
    private TaskDetailActivityHelper myHelper;
    /**
     * ???????????????????????????
     */
    private List<File> fileUploadImageList;
    /**
     * ????????????????????????
     */
    private List<ImageView> ivUploadImageList;
    /**
     * ????????????????????????
     */
    private List<EditText> etFormList;
    /**
     * ??????????????????
     */
    private List<ClientTaskDataSubmitFormModel> formDatas;
    /**
     * ?????????????????????
     */
    private ApiTaskOperationHelper mApiTaskOperationHelper;
    /**
     * ?????????
     */
    private TaskDetailView mTaskDetailView;
    /**
     * ????????????
     */
    private String mExpirationTime;
    private MokuPresenter mPresenter;
    private ImageView mTaskIvReturn;

    /**
     * ????????????????????????
     *
     * @return ????????????????????????????????????
     */
    @Override
    protected View getContentView() {
        mContext = this;
        mPhoneScreenUtils = PhoneScreenUtils.getInstance(mContext);

        mContentView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.activity_task_detail, null);
        return mContentView;
    }

    /**
     * ??????????????????????????????
     */
    @Override
    protected void onLoad() {
        initLoad();
        mTaskDetailView.initStaticView();
        initStaticViewListener();
        onReady();
    }

    /**
     * ????????????????????????
     *
     * @param llOnTask     ????????????????????????
     * @param rlDownload   ???????????????????????????????????????
     * @param tvDownload   ??????????????????????????????
     * @param btnStartTask ??????????????????
     * @param tvCancelTask ??????????????????(??????)
     * @param pbDownload   ???????????????
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
        //??????????????????
        myHelper.getTaskDetail(mTaskDataId, new ApiDataCallBack<ClientDetailTaskData>() {
            @Override
            public void success(int code, ClientDetailTaskData data) {
                loadingDialog.dismiss();
                mClientDetailTaskData = data;
                if (data != null && data.getTaskDataApplyRecord() != null && data.getTaskDataApplyRecord().getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                    //?????????
                    mExpirationTime = data.getTaskDataApplyRecord().getExpirationTime();
                }
                LogUtils.log(TAG, JSON.toJSONString(mClientDetailTaskData));
                //???????????????????????????????????????
                mTaskDetailView.initDynamicView(mClientDetailTaskData, fileUploadImageList, ivUploadImageList, etFormList, formDatas);
                //????????????????????????????????????
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
     * ????????????????????????
     *
     * @param tvCommentContent ????????????
     * @param tvBtnCommentCopy ??????????????????
     * @param rlSearchTerms    ????????????????????????
     * @param tvSearchTerms    ?????????????????????????????????
     */
    public void initDynamicView(TextView tvCommentContent, TextView tvBtnCommentCopy, RelativeLayout rlSearchTerms, TextView tvSearchTerms) {
        mTvCommentContent = tvCommentContent;
        mTvBtnCommentCopy = tvBtnCommentCopy;
        mRlSearchTerms = rlSearchTerms;
        mTvSearchTerms = tvSearchTerms;
    }

    /**
     * ??????????????????????????????
     */
    private void initStaticViewListener() {
        if (mRlDownload != null) {
            mRlDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OperationEnum operationEnum = mApiTaskOperationHelper.getOperationEnum();
                    switch (operationEnum) {
                        case SUCCESS_APPLY:
                            //???????????????
                            break;
                        case SUCCESS_DOWNLOAD:
                            //??????????????????
                        case SUCCESS_DOWNLOAD_APP:
                            //????????????
                        case SUCCESS_DOWNLOAD_LOADING:
                            //????????????
                        case SUCCESS_CONTINUE_DOWNLOAD:
                            //????????????
                        case ERROR_DOWNLOAD:
                            //????????????
                            break;
                        case SUCCESS_OPEN:
                            //??????????????????
                        case SUCCESS_OPEN_APP:
                            //????????????
                            break;
                        case SUCCESS_INSTALL:
                            //??????????????????
                            break;
                        case SUCCESS_INSTALL_APP:
                            //????????????
                            break;
                        case SUCCESS_CONTINUE_DEMO:
                            //????????????
                            break;
                        case SUCCESS_SUBMIT:
                            //????????????
                            MobclickAgent.onEvent(TaskDetailActivity.this, "moku_taskinfo_submit");
                            submitTask();
                            break;
                        case ERROR_OVERTIME:
                            //????????????
                            break;
                        case ERROR_TAKEUP:
                            //???????????????
                            break;
                        default:
                            break;
                    }
                    mApiTaskOperationHelper.startTask();
                }
            });
        }
        if (mBtnStartTask != null) {
            //?????????????????????????????????
            mBtnStartTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobclickAgent.onEvent(TaskDetailActivity.this, "moku_taskinfo_start");
                    LoadingDialog.Builder builder = new LoadingDialog.Builder(mContext);
                    LoadingDialog loadingDialog = builder.create();
                    loadingDialog.show();
                    //???????????????????????????????????????
                    myHelper.getTaskDataStatus(new ApiDataCallBack<TaskDataApplyRecord>() {
                        @Override
                        public void success(int code, TaskDataApplyRecord data) throws Exception {
                            if (data != null) {
                                loadingDialog.dismiss();
                                //???????????????????????????
                                Toast.makeText(mContext, "???????????????????????????", Toast.LENGTH_SHORT).show();
                                LogUtils.log(TAG, "another task is applying while the taskId is " + data.getTaskDataId() + " and applyId is " + data.getId());
                                ClientDetailTaskData clientDetailTaskData = new ClientDetailTaskData();
                                clientDetailTaskData.setTaskDataId(data.getTaskDataId());
                                clientDetailTaskData.setTaskDataApplyRecord(data);
                                myHelper.cancelTask(clientDetailTaskData, new ApiDataCallBack<TaskDataApplyRecord>() {
                                    @Override
                                    public void success(int code, TaskDataApplyRecord data) throws Exception {
                                        Toast.makeText(mContext, "??????????????????", Toast.LENGTH_SHORT).show();
                                        mPresenter.getAddTask(String.valueOf(mClientDetailTaskData.getTaskDataId()), PreferenceUUID.getInstence().getUserId(), mClientDetailTaskData.getShowName(), mClientDetailTaskData.getIconUrl(), mClientDetailTaskData.getShowMoney() + "", mClientDetailTaskData.getDesc(), "3", 3);
                                    }

                                    @Override
                                    public void error(int code, String message) throws Exception {
                                        LogUtils.log(TAG, "code:" + code + " message:" + message);
                                        Toast.makeText(mContext, "??????????????????" + message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                if (mApiTaskOperationHelper.checkStartTask()) {
                                    //????????????
                                    myHelper.applyTask(mClientDetailTaskData.getTaskDataId(), new ApiDataCallBack<TaskDataApplyRecord>() {
                                        @Override
                                        public void success(int code, TaskDataApplyRecord data) throws Exception {
                                            loadingDialog.dismiss();
                                            LogUtils.log(TAG, "TaskDataApplyRecord >> " + JSON.toJSONString(data));
                                            Toast.makeText(mContext, "??????????????????????????????????????????~", Toast.LENGTH_SHORT).show();
                                            mClientDetailTaskData.setTaskDataApplyRecord(data);
                                            mExpirationTime = data.getExpirationTime();
                                            //?????????????????????????????????????????????
                                            for (int i = 0; i < etFormList.size(); i++) {
                                                etFormList.get(i).setEnabled(true);
                                            }
                                            if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_COMMENT)) {
                                                //????????????????????????????????????????????????????????????????????????
                                                if (data != null && data.getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                                                    //?????????????????????
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
                                                    Toast.makeText(mContext, "???????????????????????????", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(mContext, "?????????????????? " + message, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(mContext, "?????????????????????????????? " + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        if (mTvCancelTask != null) {
            //???????????????????????????
            mTvCancelTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobclickAgent.onEvent(TaskDetailActivity.this, "moku_taskinfo_cancel");
                    LoadingDialog.Builder builder = new LoadingDialog.Builder(mContext);
                    LoadingDialog loadingDialog = builder.create();
                    loadingDialog.show();
                    //????????????
                    myHelper.cancelTask(mClientDetailTaskData, new ApiDataCallBack<TaskDataApplyRecord>() {
                        @Override
                        public void success(int code, TaskDataApplyRecord data) throws Exception {
                            loadingDialog.dismiss();
                            Toast.makeText(mContext, "????????????", Toast.LENGTH_SHORT).show();
                            mClientDetailTaskData.setTaskDataApplyRecord(data);
                            for (int i = 0; i < etFormList.size(); i++) {
                                etFormList.get(i).setEnabled(false);
                            }
                            if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_COMMENT)) {
                                //??????????????????????????????????????????????????????
                                if (data == null || !data.getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                                    if (mTvCommentContent != null) {
                                        mTvCommentContent.setEnabled(false);
                                        mTvCommentContent.setText("??????????????????");
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
                            Toast.makeText(mContext, "???????????? " + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    /**
     * ????????????
     */
    private void submitTask() {
        for (int i = 0; i < fileUploadImageList.size(); i++) {
            if (fileUploadImageList.get(i) == null) {
                Toast.makeText(mContext, "??????" + (i + 1) + "????????????", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        for (int i = 0; i < etFormList.size(); i++) {
            if (TextUtils.isEmpty(etFormList.get(i).getText().toString())) {
                Toast.makeText(mContext, "?????????" + formDatas.get(i).getKey(), Toast.LENGTH_SHORT).show();
                return;
            } else {
                formDatas.get(i).setValue(etFormList.get(i).getText().toString());
            }
        }
        //????????????????????????????????????
        LoadingDialog.Builder builder = new LoadingDialog.Builder(mContext);
        LoadingDialog loadingDialog = builder.create();
        loadingDialog.show();
        //????????????
        myHelper.submitTask(mClientDetailTaskData, fileUploadImageList, formDatas, new ApiDataCallBack<String>() {
            @Override
            public void success(int code, String data) throws Exception {
                loadingDialog.dismiss();
                LogUtils.log(TAG, JSON.toJSONString(data));
                Toast.makeText(mContext, "????????????", Toast.LENGTH_SHORT).show();
                mPresenter.getAddTask(String.valueOf(mClientDetailTaskData.getTaskDataId()), PreferenceUUID.getInstence().getUserId(), mClientDetailTaskData.getShowName(), mClientDetailTaskData.getIconUrl(), mClientDetailTaskData.getShowMoney() + "", mClientDetailTaskData.getDesc(), "0", 0);
            }

            @Override
            public void error(int code, String message) throws Exception {
                loadingDialog.dismiss();
                LogUtils.log(TAG, "code:" + code + " message:" + message);
                Toast.makeText(mContext, "???????????? " + message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext, "???????????????????????????", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext, "??????????????????", Toast.LENGTH_SHORT).show();
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
     * ????????????????????????
     *
     * @param operationEnum ??????????????????
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void taskStatus(OperationEnum operationEnum) {
        LogUtils.log(TAG, "operationEnum >> " + operationEnum.getCode() + ":" + operationEnum.getMessage());
        if (operationEnum.equals(OperationEnum.SUCCESS_START)) {
            //????????????
            mBtnStartTask.setVisibility(View.VISIBLE);
            mLlOnTask.setVisibility(View.GONE);
            mTvEndTime.setVisibility(View.GONE);
        } else {
            mBtnStartTask.setVisibility(View.GONE);
            mLlOnTask.setVisibility(View.VISIBLE);
            mPbDownload.setProgress(100);
            switch (operationEnum) {
                case SUCCESS_APPLY:
                    //???????????????
                    for (int i = 0; i < etFormList.size(); i++) {
                        etFormList.get(i).setEnabled(true);
                    }
                    break;
                case SUCCESS_DOWNLOAD:
                    //??????????????????
                    mTvDownload.setText("??????????????????");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("???????????????" + mExpirationTime));
                    break;
                case SUCCESS_DOWNLOAD_APP:
                    //????????????
                    mTvDownload.setText("????????????");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("???????????????" + mExpirationTime));
                    break;
                case SUCCESS_DOWNLOAD_LOADING:
                    //????????????
                case SUCCESS_CONTINUE_DOWNLOAD:
                    //????????????
                case ERROR_DOWNLOAD:
                    //????????????
                    break;
                case SUCCESS_OPEN:
                    //??????????????????
                    mTvDownload.setText("??????????????????");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("???????????????" + mExpirationTime));
                    break;
                case SUCCESS_OPEN_APP:
                    //????????????
                    mTvDownload.setText("????????????");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("???????????????" + mExpirationTime));
                    break;
                case SUCCESS_INSTALL:
                    //??????????????????
                    mTvDownload.setText("??????????????????");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("???????????????" + mExpirationTime));
                    break;
                case SUCCESS_INSTALL_APP:
                    //????????????
                    mTvDownload.setText("????????????");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("???????????????" + mExpirationTime));
                    break;
                case SUCCESS_CONTINUE_DEMO:
                    mTvDownload.setText("????????????");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("???????????????" + mExpirationTime));
                    //????????????
                    break;
                case SUCCESS_SUBMIT:
                    //????????????
                    mTvDownload.setText("????????????");
                    mTvEndTime.setVisibility(View.VISIBLE);
                    mTvEndTime.setText(TextUtils.isEmpty(mExpirationTime) ? "" : ("???????????????" + mExpirationTime));
                    break;
                case ERROR_OVERTIME:
                    //????????????
                    mTvDownload.setText("????????????");
                    mPbDownload.setProgress(0);
                    break;
                case ERROR_TAKEUP:
                    //???????????????
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * ????????????????????????
     *
     * @param operationEnum ???????????????????????????
     * @param progress      ?????????????????????
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void downloadProgress(OperationEnum operationEnum, String progress) {
        LogUtils.log(TAG, "operationEnum >> " + operationEnum.getCode() + ":" + operationEnum.getMessage() + " progress >> " + progress);
        int progressValue = (int) (Double.parseDouble(progress));
        String strProgress = progressValue + "%";
        if (operationEnum.equals(OperationEnum.SUCCESS_DOWNLOAD_APP)) {
            mTvDownload.setText("???????????? " + strProgress);
        } else if (operationEnum.equals(OperationEnum.SUCCESS_DOWNLOAD)) {
            mTvDownload.setText("?????????????????? " + strProgress);
        }
        LogUtils.log(TAG, "strProgress:" + strProgress);
        mPbDownload.setProgress(progressValue);
        LogUtils.log(TAG, "progressValue:" + progressValue);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param operationEnum ???????????????????????????
     * @param time          ????????????????????????????????????
     */
    @Override
    public void listenerTime(OperationEnum operationEnum, int time) {
        LogUtils.log(TAG, "operationEnum >> " + operationEnum.getCode() + ":" + operationEnum.getMessage() + " time >> " + time + " ??????");
        Toast.makeText(mContext, "?????????????????? " + time + " ??????", Toast.LENGTH_SHORT).show();
    }

    /**
     * ?????????????????????????????????
     *
     * @param operationEnum ???????????????????????????
     * @param content       ?????????????????????
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
