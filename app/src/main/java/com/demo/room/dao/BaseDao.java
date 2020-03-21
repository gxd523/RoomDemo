package com.demo.room.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import io.reactivex.Completable;

/**
 * Created by guoxiaodong on 2020/3/21 10:55
 */
public interface BaseDao<T> {
    /**
     * 一次插入单条数据或多条
     * OnConflictStrategy.REPLACE:替换旧数据
     * OnConflictStrategy.ROLLBACK:回滚事务
     * OnConflictStrategy.ABORT:终止事务崩溃
     * OnConflictStrategy.FAIL:事务失败
     * OnConflictStrategy.IGNORE:忽略冲突
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    Completable insert(T... ts);

    /**
     * 一次删除单条数据或多条
     */
    @Delete
    Completable delete(T... ts);

    /**
     * 一次更新单条数据或多条
     */
    @Update
    Completable update(T... ts);
}
