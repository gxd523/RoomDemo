package com.demo.room.database;

import com.demo.room.bean.Clothes;
import com.demo.room.bean.Person;
import com.demo.room.dao.ClothesDao;
import com.demo.room.dao.PersonDao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Person.class, Clothes.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 告诉person表要添加一个phone字段
            database.execSQL("ALTER TABLE t_person ADD COLUMN phone TEXT");
        }
    };

    public abstract PersonDao getPersonDao();

    public abstract ClothesDao getClothesDao();
}
