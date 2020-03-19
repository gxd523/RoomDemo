package com.demo.room.dao;

import com.demo.room.bean.Clothes;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by guoxiaodong on 2020/3/19 10:23
 */
@Dao
public interface ClothesDao {
    /**
     * 一次插入单条数据或多条
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Clothes... clothes);

    @Query("DELETE FROM Clothes")
    void deleteAll();

    @Query("SELECT * FROM Clothes")
    List<Clothes> getAll();
}
