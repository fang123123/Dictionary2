package com.example.databaseHelper;

/**
 * Created by FJ on 2018/4/21.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by FJ on 2018/4/21.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "Dictionary.db";//数据库名字
    private final static int DATABASE_VERSION = 1;//数据库版本
    // 建表SQL
    private final static String SQL_CREATE_DATABASE = " CREATE TABLE "+
            DatabaseStatic.TABLE_NAME + " ( " +
            DatabaseStatic.WORD + " varchar(20) PRIMARY KEY, " +
            DatabaseStatic.MEANING + " varchar(20) not null, " +
            DatabaseStatic.SAMPLE + " varchar(20) not null ) ";
    private Context myContext = null;
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " +
            DatabaseStatic.TABLE_NAME;
    public MyHelper(Context context, String name,
                    CursorFactory factory, int version) {
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
    }

    public MyHelper(Context context)
    {
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
        myContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
        Log.i("123","成功创建数据库");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                          int oldVersion, int newVersion) {
        //当数据库升级时被调用，首先删除旧表，然后调用OnCreate()创建新表
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }
}
