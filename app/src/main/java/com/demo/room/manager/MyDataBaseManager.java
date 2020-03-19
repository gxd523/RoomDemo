package com.demo.room.manager;

import com.demo.room.MyApplication;
import com.demo.room.database.MyDataBase;

import androidx.room.Room;

/**
 * Created by guoxiaodong on 2020/3/18 17:09
 */
public class MyDataBaseManager {
    private static final String DB_NAME = "room_test";
    public static MyDataBase dataBase = Room.databaseBuilder(MyApplication.instance, MyDataBase.class, DB_NAME)
            .allowMainThreadQueries()
            .build();
}
