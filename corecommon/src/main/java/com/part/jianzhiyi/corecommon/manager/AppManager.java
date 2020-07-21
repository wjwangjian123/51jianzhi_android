package com.part.jianzhiyi.corecommon.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 16/7/15.
 *
 * @author
 */
public final class AppManager {
    private static final Object lockObj = new Object();
    private static volatile AppManager manager;

    private List<Activity> activityList = new LinkedList<>();
    private Application application;

    public static AppManager getInstance() {
        if (manager == null) {
            synchronized (lockObj) {
                if (manager == null) {
                    manager = new AppManager();
                }
            }
        }
        return manager;
    }

    public synchronized void addActivity(Activity activity) {
        if (activity != null) {
            activityList.add(activity);
        }
    }

    public synchronized void removeActivity(Activity activity) {
        if (activityList == null || activityList.isEmpty()) {
            return;
        }
        if (activity != null) {
            activityList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public List<Activity> getActivities() {
        return activityList;
    }

    public void attachApplication(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public Context getContext() {
        return application == null ? null : application.getApplicationContext();
    }

    // 遍历所有Activity并finish
    public void exitApp() {
        for (Activity activity : activityList) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
        System.exit(0);
    }

    public void finishActivities() {
        for (Activity activity : activityList) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }
}