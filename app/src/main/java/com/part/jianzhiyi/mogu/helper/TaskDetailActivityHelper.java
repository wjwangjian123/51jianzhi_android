package com.part.jianzhiyi.mogu.helper;

import android.content.Context;

import com.fendasz.moku.planet.entity.ApiDataCallBack;
import com.fendasz.moku.planet.helper.ApiDataHelper;
import com.fendasz.moku.planet.source.bean.ClientDetailTaskData;
import com.fendasz.moku.planet.source.bean.ClientTaskDataSubmitFormModel;
import com.fendasz.moku.planet.source.bean.TaskDataApplyRecord;

import java.io.File;
import java.util.List;

/**
 * @author Trailwalker
 * @create 2020-07-30 16:29
 * @des
 */
public class TaskDetailActivityHelper {

    private Context mContext;
    private String mUserId;
    /**
     * 蘑菇星球api帮助类
     */
    private ApiDataHelper mApiDataHelper;

    public TaskDetailActivityHelper(Context context, String userId) {
        this.mContext = context;
        this.mUserId = userId;
        mApiDataHelper = new ApiDataHelper.HelperBuilder(mUserId).create(mContext);
    }

    public void submitTask(ClientDetailTaskData clientDetailTaskData, List<File> files, List<ClientTaskDataSubmitFormModel> forms, ApiDataCallBack<String> callBack) {
        mApiDataHelper.submitTask(clientDetailTaskData, files, forms, callBack);
    }

    public void getTaskDetail(Integer taskId, ApiDataCallBack<ClientDetailTaskData> callBack) {
        mApiDataHelper.getTaskDetail(taskId, callBack);
    }

    public void applyTask(Integer taskId, ApiDataCallBack<TaskDataApplyRecord> callBack) {
        mApiDataHelper.applyTask(taskId, callBack);
    }

    public void cancelTask(ClientDetailTaskData clientDetailTaskData, ApiDataCallBack<TaskDataApplyRecord> callBack) {
        mApiDataHelper.cancelTask(clientDetailTaskData, callBack);
    }

    public void getTaskDataStatus(ApiDataCallBack<TaskDataApplyRecord> callBack) {
        mApiDataHelper.getTaskDataStatus(callBack);
    }
}
