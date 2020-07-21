package com.part.jianzhiyi.dbmodel;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.part.jianzhiyi.app.ODApplication;


/**
 */

public class GreenDaoManager {
    public static GreenDaoManager mInstance;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private GreenDaoManager(){
        DBHelper dbHelper = new DBHelper(ODApplication.context().getApplicationContext());
        daoMaster = new DaoMaster(dbHelper.getReadableDatabase());
        daoSession = daoMaster.newSession();
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ODApplication.context(),"app-db",null);
//        daoMaster = new DaoMaster(helper.getWritableDatabase());
//        daoSession = daoMaster.newSession();
    }

    public static GreenDaoManager getInstance(){
        if(mInstance == null){
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
