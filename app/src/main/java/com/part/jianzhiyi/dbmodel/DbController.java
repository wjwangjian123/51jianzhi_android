package com.part.jianzhiyi.dbmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.greendao.gen.LoginResponseEntityDao;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class DbController {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private LoginResponseEntityDao mLoginResponseEntityDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DbController.class) {
                if (mDbController == null) {
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, "jianzhiyi.db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        mLoginResponseEntityDao = mDaoSession.getLoginResponseEntityDao();
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "jianzhiyi.db", null);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "jianzhiyi.db", null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     *
     * @param loginResponseEntity
     */
    public void insertOrReplace(LoginResponseEntity loginResponseEntity) {
        mLoginResponseEntityDao.insertOrReplace(loginResponseEntity);
    }

    /**
     * 插入一条记录，表里面要没有与之相同的记录
     *
     * @param loginResponseEntity
     */
    public long insert(LoginResponseEntity loginResponseEntity) {
        return mLoginResponseEntityDao.insert(loginResponseEntity);
    }

    /**
     * 按条件查询数据
     */
    public LoginResponseEntity searchByWhere(String wherecluse) {
        LoginResponseEntity personInfors = (LoginResponseEntity) mLoginResponseEntityDao.queryBuilder().where(LoginResponseEntityDao.Properties.Id.eq(wherecluse)).build().unique();
        return personInfors;
    }

    /**
     * 删除数据
     */
    public void delete(String wherecluse) {
        mLoginResponseEntityDao.queryBuilder().where(LoginResponseEntityDao.Properties.Id.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除数据
     */
    public void deleteAll() {
        mLoginResponseEntityDao.deleteAll();
    }
}
