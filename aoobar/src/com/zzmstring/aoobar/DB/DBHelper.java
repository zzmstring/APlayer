package com.zzmstring.aoobar.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZGL on 2015/3/10.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context, "aoobar.db", null, 1);
    }
    /**
     * aoobar.db数据库下创建一个list表存储下载信息
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table list(_id integer PRIMARY KEY AUTOINCREMENT, "
                + "name char)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
