package com.demo.room.dao;

import com.demo.room.bean.Clothes;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by guoxiaodong on 2020/3/19 10:23
 */
@Dao
public interface ClothesDao {
    /**
     * 一次插入单条数据或多条
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Clothes... clothes);

    @Query("DELETE FROM Clothes")
    Completable deleteAll();

    @Query("SELECT * FROM Clothes")
    Observable<List<Clothes>> getAll();
}
