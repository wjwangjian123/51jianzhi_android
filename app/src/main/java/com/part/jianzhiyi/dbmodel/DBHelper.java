package com.part.jianzhiyi.dbmodel;

import android.content.Context;
import android.util.Log;

import com.greendao.gen.DaoMaster;
import com.part.jianzhiyi.constants.Constants;

import org.greenrobot.greendao.database.Database;


public class DBHelper extends DaoMaster.DevOpenHelper {
    private final static String TAG = DBHelper.class.getSimpleName();
    private Context context;

    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null);
        this.context = context;
    }


    @Override
    public void onUpgrade(Database sqLiteDatabase, int oldVersion, int newVersion) {

        Log.w(TAG, "Upgrade from " + oldVersion + " to " + newVersion);


    }

}
