package com.example.qigao.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotesDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME="notes";
    public static final String CONTENT="content";
    public static final String PATH="path";
    public static final String VIDEO="video";
    public static final String ID="_id";
    public static final String TIME="time";


    public NotesDB(Context context) {
        super(context, "notes", null, 1);
    }



//onCreate(SQLiteDatabase db) : 当数据库被首次创建时执行该方法，
// 一般将创建表等初始化操作在该方法中执行。
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME+"("+
        ID+" INTEGER PRIMARY KEY ATUOINCREMENT,"+CONTENT+" TEXT NOT NULL,"
               + VIDEO+" TEXT NOT NULL,"+PATH+" TEXT NOT NULL,"+TIME+" TEXT NOT NULL)");
    }
// onUpgrade(SQLiteDatabse dv, int oldVersion,int new Version)：
// 当打开数据库时传入的版本号与当前的版本号不同时会调用该方法。
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
