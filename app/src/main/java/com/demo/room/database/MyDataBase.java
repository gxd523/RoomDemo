package com.demo.room.database;

import com.demo.room.bean.Clothes;
import com.demo.room.bean.Person;
import com.demo.room.dao.ClothesDao;
import com.demo.room.dao.PersonDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class, Clothes.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    public abstract PersonDao getPersonDao();

    public abstract ClothesDao getClothesDao();
}
