package com.demo.room.manager;

import com.demo.room.MyApplication;
import com.demo.room.database.MyDataBase;

import java.io.File;

import androidx.room.Room;

/**
 * Created by guoxiaodong on 2020/3/18 17:09
 */
public class MyDataBaseManager {
    /**
     * 可指定路径
     */
    private static final String DB_NAME = new File(MyApplication.instance.getExternalFilesDir(null), "room_test.db").getAbsolutePath();
    public static MyDataBase dataBase = Room.databaseBuilder(MyApplication.instance, MyDataBase.class, DB_NAME)
            .allowMainThreadQueries()// 允许主线程进行数据库操作,仅为Demo方便展示使用
            .addMigrations(MyDataBase.MIGRATION_1_2)
            .build();
}
