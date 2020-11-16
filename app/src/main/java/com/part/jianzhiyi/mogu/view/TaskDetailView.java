package com.part.jianzhiyi.mogu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fendasz.moku.planet.constants.MokuConstants;
import com.fendasz.moku.planet.source.bean.ClientDetailTaskData;
import com.fendasz.moku.planet.source.bean.ClientTaskDataSubmitFormModel;
import com.fendasz.moku.planet.source.bean.TaskDataApplyRecord;
import com.fendasz.moku.planet.source.bean.TaskDataStep;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.loader.GlideRoundTransformUtil;
import com.part.jianzhiyi.mogu.utils.PhoneScreenUtils;
import com.part.jianzhiyi.mogu.utils.ScreenAdaptationUtils;
import com.part.jianzhiyi.mvp.ui.activity.TaskDetailActivity;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Trailwalker
 * @create 2020-08-13 19:30
 * @des
 */
public class TaskDetailView {

    //静态视图中的 全局控件

    /**
     * 可控的根布局
     */
    private RelativeLayout mContentView;
    /**
     * 任务模块的容器布局
     */
    private LinearLayout mLlTaskContainer;

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

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 屏幕适配帮助实例
     */
    private PhoneScreenUtils mPhoneScreenUtils;
    /**
     * 任务详情
     */
    private ClientDetailTaskData mClientDetailTaskData;
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

    public TaskDetailView(Context context, RelativeLayout contentView, PhoneScreenUtils phoneScreenUtils) {
        mContext = context;
        mContentView = contentView;
        mPhoneScreenUtils = phoneScreenUtils;
    }

    /**
     * 初始化静态视图
     */
    public void initStaticView() {
//        StyleUtils.setStatusBarColor((Activity) mContext, R.color.colorPrimary);

//        ((BaseMoguActivity) mContext).initTitle(true);
//        ((BaseMoguActivity) mContext).setTitleText("任务详情");

        LinearLayout llBackground = mContentView.findViewById(R.id.ll_background);
        ScreenAdaptationUtils.setHeight(mContext, llBackground, 200);

        LinearLayout llBottom = mContentView.findViewById(R.id.ll_bottom);
        ScreenAdaptationUtils.setHeight(mContext, llBottom, 168);

        LinearLayout llOnTask = mContentView.findViewById(R.id.ll_on_task);

        Button btnStartTask = mContentView.findViewById(R.id.btn_start_task);

        RelativeLayout rlDownload = mContentView.findViewById(R.id.rl_download);

        TextView tvCancelTask = mContentView.findViewById(R.id.tv_cancel_task);

        TextView tvDownload = mContentView.findViewById(R.id.tv_download);
        TextView tvEndTime = mContentView.findViewById(R.id.tv_end);

        //下载进度条
        ProgressBar pbDownload = mContentView.findViewById(R.id.pb_download);

        mLlTaskContainer = mContentView.findViewById(R.id.ll_task_container);

        ((TaskDetailActivity) mContext).initStaticView(llOnTask, rlDownload, tvDownload, btnStartTask, tvCancelTask, pbDownload, tvEndTime);
    }

    /**
     * 初始化动态视图
     *
     * @param clientDetailTaskData 任务详情
     * @param fileUploadImageList  需要上传的图片列表
     * @param ivUploadImageList    上传图片控件列表
     * @param etFormList           上传表单控件列表
     * @param formDatas            上传表单数据
     */
    public void initDynamicView(ClientDetailTaskData clientDetailTaskData, List<File> fileUploadImageList, List<ImageView> ivUploadImageList, List<EditText> etFormList, List<ClientTaskDataSubmitFormModel> formDatas) {
        mClientDetailTaskData = clientDetailTaskData;
        this.fileUploadImageList = fileUploadImageList;
        this.ivUploadImageList = ivUploadImageList;
        this.etFormList = etFormList;
        this.formDatas = formDatas;

        mLlTaskContainer.removeAllViews();
        //初始化基础信息
        initBasicInfoView();
        //初始化任务说明
        initTaskDescView();
        //初始化任务步骤
        initTaskFlowView();
        //初始化任务提交
        initTaskSubmitView();

        ((TaskDetailActivity) mContext).initDynamicView(mTvCommentContent, mTvBtnCommentCopy, mRlSearchTerms, mTvSearchTerms);
    }

    /**
     * 初始化动态视图-基础信息模块
     */
    @SuppressLint("SetTextI18n")
    private void initBasicInfoView() {
        LinearLayout llBasicInfo = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.basic_infomation, null);
        mLlTaskContainer.addView(llBasicInfo);
        ScreenAdaptationUtils.setMarginBottom(mContext, llBasicInfo, 30);

        ImageView ivIcon = llBasicInfo.findViewById(R.id.iv_icon);
        Glide.with(mContext).load(mClientDetailTaskData.getIcon()).transform(new GlideRoundTransformUtil(mContext, 5)).into(ivIcon);
        ScreenAdaptationUtils.setSize(mContext, ivIcon, 136, 136);

        TextView tvTaskName = llBasicInfo.findViewById(R.id.tv_task_name);
        tvTaskName.setText(mClientDetailTaskData.getShowName());
        tvTaskName.setTextSize(mPhoneScreenUtils.getNormalTextSize());

        TextView tvAward = llBasicInfo.findViewById(R.id.tv_award);
        tvAward.setText(mClientDetailTaskData.getShowMoney() + "");
        tvAward.setTextSize(mPhoneScreenUtils.getNormalTextSize());
    }

    /**
     * 初始化动态视图-任务说明模块
     */
    public void initTaskDescView() {
        String keyPoint = mClientDetailTaskData.getKeyPoint();

        if (!TextUtils.isEmpty(keyPoint)) {
            RelativeLayout rlTaskDescModule = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.process_module, null);
            mLlTaskContainer.addView(rlTaskDescModule);
            ScreenAdaptationUtils.setMarginBottom(mContext, rlTaskDescModule, 30);

            TextView tvProcessLabel = rlTaskDescModule.findViewById(R.id.tv_process_label);
            tvProcessLabel.setText("任务说明");

            LinearLayout llChildProcessContainer = rlTaskDescModule.findViewById(R.id.ll_child_process);
            LinearLayout llChildProcessContent = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.child_process_content, null);
            TextView tvTaskDesc = new TextView(mContext);
            tvTaskDesc.setText(keyPoint);
            tvTaskDesc.setTextSize(mPhoneScreenUtils.getNormalTextSize());
            tvTaskDesc.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvTaskDesc.setLayoutParams(tvLayoutParams);
            llChildProcessContent.addView(tvTaskDesc);
            llChildProcessContainer.addView(llChildProcessContent);
        }
    }

    /**
     * 初始化动态视图-任务步骤模块
     */
    public void initTaskFlowView() {
        RelativeLayout rlTaskFlowModule = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.process_module, null);
        mLlTaskContainer.addView(rlTaskFlowModule);
        ScreenAdaptationUtils.setMarginBottom(mContext, rlTaskFlowModule, 30);

        TextView tvProcessLabel = rlTaskFlowModule.findViewById(R.id.tv_process_label);
        tvProcessLabel.setText("任务步骤");

        if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_KEYWORD)) {
            //关键词任务

            LinearLayout llChildProcessContainer = rlTaskFlowModule.findViewById(R.id.ll_child_process);
            setChildProcessDesc(llChildProcessContainer, 1, "打开 <font color=\"#ff0000\">" + mClientDetailTaskData.getAppGalleryName() + "</font> 搜索 <font color=\"#ff0000\">" + mClientDetailTaskData.getKeyword() + "</font> 找到该图标软件");

            LinearLayout llChildProcessContent = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.child_process_content, null);
            llChildProcessContainer.addView(llChildProcessContent);

            ImageView ivAppIcon = new ImageView(mContext);
            llChildProcessContent.addView(ivAppIcon);
            ScreenAdaptationUtils.setMarginTop(mContext, ivAppIcon, 30);
            ScreenAdaptationUtils.setSize(mContext, ivAppIcon, 200, 200);
            String iconUrl = mClientDetailTaskData.getIconUrl();
            Glide.with(mContext).load(iconUrl).transform(new GlideRoundTransformUtil(mContext, 5)).into(ivAppIcon);

            TextView tvAppDownloadInfo = new TextView(mContext);
            llChildProcessContent.addView(tvAppDownloadInfo);
            tvAppDownloadInfo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ScreenAdaptationUtils.setMarginTop(mContext, tvAppDownloadInfo, 30);
            tvAppDownloadInfo.setTextSize(mPhoneScreenUtils.getNormalTextSize());
            tvAppDownloadInfo.setText(Html.fromHtml("【软件排在第 <font color=\"#ff0000\">" + mClientDetailTaskData.getRank() + "</font> 位】"));
            tvAppDownloadInfo.setTextColor(Color.GRAY);

            setChildProcessDesc(llChildProcessContainer, 2, "必须使用下方搜索词搜索，否则无奖励");

            mRlSearchTerms = new RelativeLayout(mContext);
            llChildProcessContainer.addView(mRlSearchTerms);
            ScreenAdaptationUtils.setMargin(mContext, mRlSearchTerms, 30, 60, null, 30);
            ScreenAdaptationUtils.setHeight(mContext, mRlSearchTerms, 180);
            mRlSearchTerms.setBackgroundResource(R.drawable.blue_border_rectangle_shape);

            mTvSearchTerms = new TextView(mContext);
            mRlSearchTerms.addView(mTvSearchTerms);
            RelativeLayout.LayoutParams tvLayoutParams = (RelativeLayout.LayoutParams) mTvSearchTerms.getLayoutParams();
            tvLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mTvSearchTerms.setText(mClientDetailTaskData.getKeyword());
            mTvSearchTerms.setTextSize(mPhoneScreenUtils.getBigTextSize());
            mTvSearchTerms.setTextColor(Color.RED);

            TextView tvCopy = new TextView(mContext);
            mRlSearchTerms.addView(tvCopy);
            ScreenAdaptationUtils.setSize(mContext, tvCopy, 180, 180);
            RelativeLayout.LayoutParams tvCpyLayoutParams = (RelativeLayout.LayoutParams) tvCopy.getLayoutParams();
            tvCpyLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tvCpyLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            tvCopy.setGravity(Gravity.CENTER);
            tvCopy.setText("点击\n复制");
            tvCopy.setTextColor(Color.WHITE);
            tvCopy.setTextSize(mPhoneScreenUtils.getBigTextSize());
            tvCopy.setBackgroundResource(R.drawable.shape_moku_copy_bg);
        }

        if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_COMMENT)) {
            //评论任务

            LinearLayout llChildProcessContainer = rlTaskFlowModule.findViewById(R.id.ll_child_process);
            setChildProcessDesc(llChildProcessContainer, 1, "打开 <font color=\"#ff0000\">" + mClientDetailTaskData.getAppGalleryName() + "</font> 搜索 <font color=\"#ff0000\">" + mClientDetailTaskData.getKeyword() + "</font> 找到该图标软件");

            LinearLayout llChildProcessContent = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.child_process_content, null);
            llChildProcessContainer.addView(llChildProcessContent);

            ImageView ivAppIcon = new ImageView(mContext);
            llChildProcessContent.addView(ivAppIcon);
            ScreenAdaptationUtils.setMarginTop(mContext, ivAppIcon, 30);
            ScreenAdaptationUtils.setSize(mContext, ivAppIcon, 200, 200);
            String iconUrl = mClientDetailTaskData.getIconUrl();
            Glide.with(mContext).load(iconUrl).into(ivAppIcon);

            TextView tvAppDownloadInfo = new TextView(mContext);
            llChildProcessContent.addView(tvAppDownloadInfo);
            tvAppDownloadInfo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ScreenAdaptationUtils.setMarginTop(mContext, tvAppDownloadInfo, 30);
            tvAppDownloadInfo.setTextSize(mPhoneScreenUtils.getNormalTextSize());
            tvAppDownloadInfo.setText("【请下载对应软件，否则无法提交】");
            tvAppDownloadInfo.setTextColor(Color.GRAY);

            setChildProcessDesc(llChildProcessContainer, 2, "下载安装软件后，使用下方评论内容 <font color=\"#ff0000\">" + mClientDetailTaskData.getCommentStar() + "</font> 星评论");
            Integer commentType = mClientDetailTaskData.getCommentType();
            if (commentType.equals(MokuConstants.COMMENTTYPE_SPECIFIED)) {
                //指定内容评论
                LinearLayout llCommentCopyItem = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.comment_copy_item, null);
                llChildProcessContainer.addView(llCommentCopyItem);
                ScreenAdaptationUtils.setMarginTop(mContext, llCommentCopyItem, 30);
                mTvCommentContent = llCommentCopyItem.findViewById(R.id.tv_comment_content);
                mTvBtnCommentCopy = llCommentCopyItem.findViewById(R.id.tv_comment_copy);
                if (mClientDetailTaskData != null && mClientDetailTaskData.getTaskDataApplyRecord() != null && mClientDetailTaskData.getTaskDataApplyRecord().getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                    //已经申请了任务
                    mTvCommentContent.setEnabled(true);
                    String taskDataCommentData = "";
                    if (!TextUtils.isEmpty(mClientDetailTaskData.getTaskDataApplyRecord().getTaskDataCommentData().getTaskDataCommentData())) {
                        taskDataCommentData = mClientDetailTaskData.getTaskDataApplyRecord().getTaskDataCommentData().getTaskDataCommentData();
                    }
                    mTvCommentContent.setText(taskDataCommentData);
                    mTvCommentContent.setTextSize(mPhoneScreenUtils.getNormalTextSize());
                    mTvBtnCommentCopy.setEnabled(true);
                    mTvBtnCommentCopy.setTextSize(mPhoneScreenUtils.getNormalTextSize());
                } else {
                    //没有申请任务
                    mTvCommentContent.setEnabled(false);
                    mTvCommentContent.setText("请先开始任务");
                    mTvCommentContent.setTextSize(mPhoneScreenUtils.getNormalTextSize());
                    mTvBtnCommentCopy.setEnabled(false);
                    mTvBtnCommentCopy.setTextSize(mPhoneScreenUtils.getNormalTextSize());
                }
            } else if (commentType.equals(MokuConstants.COMMENTTYPE_FREE)) {
                //自由评论
                setChildProcessDesc(llChildProcessContainer, 3, "评论内容自由发挥 （字数10-15字左右）");
            } else if (commentType.equals(MokuConstants.COMMENTTYPE_KEYWORD)) {
                //关键词评论
                setChildProcessDesc(llChildProcessContainer, 3, "评论内容必须包括 <font color=\"#ff0000\">" + mClientDetailTaskData.getCommentKeyword() + "</font> 这些关键字 （字数10-15字左右）");
            }
        }

        if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_HP)) {
            //截图任务

            Map<Integer, List<TaskDataStep>> integerListMap = mClientDetailTaskData.getIntegerListMap();
            LinearLayout llChildProcessContainer = rlTaskFlowModule.findViewById(R.id.ll_child_process);
            //步骤数
            int steps = 0;
            if (integerListMap != null) {
                int mScreenShotCount = 0;
                for (int i = 0; i < integerListMap.size(); i++) {
                    List<TaskDataStep> taskDataSteps = integerListMap.get(i + 1);
                    if (taskDataSteps != null && taskDataSteps.size() > 0) {

                        steps++;
                        setChildProcessDesc(llChildProcessContainer, steps, taskDataSteps.get(0).getTaskDataCustomDesc());
                        for (int j = 0; j < taskDataSteps.size(); j++) {
                            TaskDataStep taskDataStep = taskDataSteps.get(j);
                            if (taskDataStep.isExamplePicStep()) {
                                if (!TextUtils.isEmpty(taskDataStep.getTaskDataSampleScreenshotUrl().trim())) {
                                    mScreenShotCount++;
                                    setImageCommitView(llChildProcessContainer, taskDataStep.getTaskDataSampleScreenshotUrl(), mScreenShotCount);
                                }
                            } else if (taskDataStep.getType() == 2) {
                                if (!TextUtils.isEmpty(taskDataStep.getTaskDataSampleScreenshotUrl().trim())) {
                                    LinearLayout llChildProcessContent2 = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.child_process_content, null);
                                    llChildProcessContainer.addView(llChildProcessContent2);
                                    ImageView ivNormal = new ImageView(mContext);
                                    llChildProcessContent2.addView(ivNormal);
                                    ScreenAdaptationUtils.setMarginTop(mContext, ivNormal, 30);
                                    ScreenAdaptationUtils.setSize(mContext, ivNormal, 300, 300);
                                    ivNormal.setBackgroundResource(R.color.transparency);
                                    ivNormal.setScaleType(ImageView.ScaleType.FIT_XY);
                                    Glide.with(mContext).load(taskDataStep.getTaskDataSampleScreenshotUrl()).into(ivNormal);
                                }
                            }
                        }
                    }
                }
            }
            List<String> formList = mClientDetailTaskData.getFormList();
            if (formList != null) {
                for (int i = 0; i < formList.size(); i++) {
                    String key = formList.get(i);
                    if (!TextUtils.isEmpty(key)) {
                        steps++;
                        setChildProcessDesc(llChildProcessContainer, steps, "请提交 <font color=\"#ff0000\">" + key + "</font>");
                        LinearLayout llInputItem = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.input_item, null);
                        llChildProcessContainer.addView(llInputItem);
                        ScreenAdaptationUtils.setMarginTop(mContext, llInputItem, 30);
                        ScreenAdaptationUtils.setHeight(mContext, llInputItem, 130);
                        EditText etInput = llInputItem.findViewById(R.id.et_input);
                        etInput.setHint(key);
                        etInput.setTextSize(mPhoneScreenUtils.getNormalTextSize());
                        etInput.setEnabled(false);
                        etFormList.add(etInput);
                        ClientTaskDataSubmitFormModel formData = new ClientTaskDataSubmitFormModel();
                        formData.setKey(key);
                        formDatas.add(formData);
                    }
                }
            }
        }

    }

    private void initTaskSubmitView() {
        if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_HP)) {
            //一般截图任务没有任务提交模块，直接退出，不加载该模块
            return;
        }
        RelativeLayout rlTaskSubmitModule = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.process_module, null);
        mLlTaskContainer.addView(rlTaskSubmitModule);
        ScreenAdaptationUtils.setMarginBottom(mContext, rlTaskSubmitModule, 30);

        TextView tvProcessLabel = rlTaskSubmitModule.findViewById(R.id.tv_process_label);
        tvProcessLabel.setText("任务提交");

        if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_KEYWORD) || mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_COMMENT)) {
            //关键词任务或评论任务

            Map<Integer, List<TaskDataStep>> integerListMap = mClientDetailTaskData.getIntegerListMap();
            LinearLayout llChildProcessContainer = rlTaskSubmitModule.findViewById(R.id.ll_child_process);
            //步骤数
            int steps = 0;
            if (integerListMap != null) {
                int mScreenShotCount = 0;
                if (mClientDetailTaskData.getIsCustomDesc().equals(MokuConstants.CUSTOM_DESC)) {
                    //自定义描述
                    for (int i = 0; i < integerListMap.size(); i++) {
                        List<TaskDataStep> taskDataSteps = integerListMap.get(i + 1);
                        if (taskDataSteps != null && taskDataSteps.size() > 0) {

                            steps++;
                            setChildProcessDesc(llChildProcessContainer, steps, taskDataSteps.get(0).getTaskDataCustomDesc());
                            for (int j = 0; j < taskDataSteps.size(); j++) {
                                TaskDataStep taskDataStep = taskDataSteps.get(j);
                                if (taskDataStep.isExamplePicStep()) {
                                    //示例图
                                    if (!TextUtils.isEmpty(taskDataStep.getTaskDataSampleScreenshotUrl().trim())) {
                                        mScreenShotCount++;
                                        setImageCommitView(llChildProcessContainer, taskDataStep.getTaskDataSampleScreenshotUrl(), mScreenShotCount);
                                    }
                                } else if (taskDataStep.getType() == 2) {
                                    //普通图片
                                    if (!TextUtils.isEmpty(taskDataStep.getTaskDataSampleScreenshotUrl().trim())) {
                                        LinearLayout llChildProcessContent2 = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.child_process_content, null);
                                        llChildProcessContainer.addView(llChildProcessContent2);
                                        ImageView ivNormal = new ImageView(mContext);
                                        llChildProcessContent2.addView(ivNormal);
                                        ScreenAdaptationUtils.setMarginTop(mContext, ivNormal, 30);
                                        ScreenAdaptationUtils.setSize(mContext, ivNormal, 300, 300);
                                        ivNormal.setBackgroundResource(R.color.transparency);
                                        ivNormal.setScaleType(ImageView.ScaleType.FIT_XY);
                                        Glide.with(mContext).load(taskDataStep.getTaskDataSampleScreenshotUrl()).transform(new GlideRoundTransformUtil(mContext, 5)).into(ivNormal);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    //固定描述
                    for (int i = 0; i < integerListMap.size(); i++) {
                        List<TaskDataStep> taskDataSteps = integerListMap.get(i + 1);
                        if (taskDataSteps != null && taskDataSteps.size() > 0) {
                            TaskDataStep taskDataStep = taskDataSteps.get(0);

                            steps++;
                            if (steps == 1) {
                                setChildProcessDesc(llChildProcessContainer, steps, "复制搜索词，市场搜索下载，截取 <font color=\"#ff0000\">下载中截图</font>");
                            }
                            if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_KEYWORD)) {
                                if (steps == 2) {
                                    setChildProcessDesc(llChildProcessContainer, steps, "打开软件 <font color=\"#ff0000\">试玩" + mClientDetailTaskData.getListenerTime() + "秒</font> 以上，试玩过程中截图");
                                }
                            }
                            if (mClientDetailTaskData.getClassify().equals(MokuConstants.TASK_TYPE_COMMENT)) {
                                if (steps == 2) {
                                    setChildProcessDesc(llChildProcessContainer, steps, "评论编辑截图");
                                }
                                if (steps == 3) {
                                    setChildProcessDesc(llChildProcessContainer, steps, "评论成功截图");
                                }
                                if (steps == 4) {
                                    setChildProcessDesc(llChildProcessContainer, steps, "打开软件 <font color=\"#ff0000\">试玩" + (mClientDetailTaskData.getListenerTime() != null ? mClientDetailTaskData.getListenerTime() : 20) + "秒</font> 以上，试玩过程中截图");
                                }
                            }
                            if (taskDataStep.isExamplePicStep()) {
                                //示例图
                                if (!TextUtils.isEmpty(taskDataStep.getTaskDataSampleScreenshotUrl().trim())) {
                                    mScreenShotCount++;
                                    setImageCommitView(llChildProcessContainer, taskDataStep.getTaskDataSampleScreenshotUrl(), mScreenShotCount);
                                }
                            }
                        }
                    }
                }
            }
            List<String> formList = mClientDetailTaskData.getFormList();
            if (formList != null) {
                for (int i = 0; i < formList.size(); i++) {
                    String key = formList.get(i);
                    if (!TextUtils.isEmpty(key)) {
                        steps++;
                        setChildProcessDesc(llChildProcessContainer, steps, "请提交 <font color=\"#ff0000\">" + key + "</font>");
                        LinearLayout llInputItem = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.input_item, null);
                        llChildProcessContainer.addView(llInputItem);
                        ScreenAdaptationUtils.setMarginTop(mContext, llInputItem, 30);
                        ScreenAdaptationUtils.setHeight(mContext, llInputItem, 130);
                        EditText etInput = llInputItem.findViewById(R.id.et_input);
                        etInput.setHint(key);
                        etInput.setTextSize(mPhoneScreenUtils.getNormalTextSize());
                        etInput.setEnabled(false);
                        etFormList.add(etInput);
                        ClientTaskDataSubmitFormModel formData = new ClientTaskDataSubmitFormModel();
                        formData.setKey(key);
                        formDatas.add(formData);
                    }
                }
            }
        }
    }

    /**
     * 创建并设置子过程的描述视图
     *
     * @param llChildProcessContainer 容器视图
     * @param index                   索引
     * @param content                 描述的内容
     */
    private void setChildProcessDesc(LinearLayout llChildProcessContainer, int index, String content) {
        //content="<p>步骤要求yyyyyy&nbsp; &nbsp;<fontstyle style=\"color: rgb(255,0,0);\">字体颜色测试</fontstyle></p>";
        LinearLayout llChildProcessDesc = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.child_process_desc, null);
        llChildProcessContainer.addView(llChildProcessDesc);
        if (index > 1) {
            ScreenAdaptationUtils.setMarginTop(mContext, llChildProcessDesc, 30);
        }

        TextView tvChildProcessIndex = llChildProcessDesc.findViewById(R.id.tv_child_process_index);
        tvChildProcessIndex.setText(String.valueOf(index));
        tvChildProcessIndex.setTextSize(mPhoneScreenUtils.getMiddleTextSize());
        TextView tvChildProcessDesc = llChildProcessDesc.findViewById(R.id.tv_child_process_desc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvChildProcessDesc.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
            //tvChildProcessDesc.setText(Html.fromHtml(content,null, new CustomTagHandler(mContext, tvChildProcessDesc.getTextColors())));
        } else {
            tvChildProcessDesc.setText(Html.fromHtml(content));
            //tvChildProcessDesc.setText(Html.fromHtml(content,null, new CustomTagHandler(mContext, tvChildProcessDesc.getTextColors())));
        }
        tvChildProcessDesc.setMovementMethod(LinkMovementMethod.getInstance());
        tvChildProcessDesc.setTextSize(mPhoneScreenUtils.getMiddleTextSize());
        //RichText.fromHtml(content).into(tvChildProcessDesc);
    }

    /**
     * 创建并设置截图提交视图
     *
     * @param llChildProcessContainer 容器视图
     * @param index                   索引（截图index）
     */
    private void setImageCommitView(LinearLayout llChildProcessContainer, String src, int index) {
        LinearLayout llScreenshotSubmit = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.screenshot_submit, null);
        llChildProcessContainer.addView(llScreenshotSubmit);
        ScreenAdaptationUtils.setMarginTop(mContext, llScreenshotSubmit, 30);

        //示例图
//        TextView tvScreenshotLabel = llScreenshotSubmit.findViewById(R.id.tv_screenshot_label);
//        tvScreenshotLabel.setText("截图" + index);
        if (!TextUtils.isEmpty(src)) {
            ImageView ivSample = llScreenshotSubmit.findViewById(R.id.iv_sample);
            Glide.with(mContext).load(src).transform(new GlideRoundTransformUtil(mContext, 5)).into(ivSample);
        }

        //上传图片
        RelativeLayout rlUploadImage = llScreenshotSubmit.findViewById(R.id.rl_upload_image);
        ImageView ivUploadImage = llScreenshotSubmit.findViewById(R.id.iv_upload_image);
        ivUploadImageList.add(ivUploadImage);
        fileUploadImageList.add(null);
        rlUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClientDetailTaskData != null && mClientDetailTaskData.getTaskDataApplyRecord() != null && mClientDetailTaskData.getTaskDataApplyRecord().getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                    //任务的状态处于申请中
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT < 19) {
                        //因为Android SDK在4.4版本后图片action变化了 所以在这里先判断一下
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                    } else {
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    }
                    intent.setType("image/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    ((TaskDetailActivity) mContext).startActivityForResult(intent, index - 1);
                } else {
                    Toast.makeText(mContext, "请先开始任务", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
