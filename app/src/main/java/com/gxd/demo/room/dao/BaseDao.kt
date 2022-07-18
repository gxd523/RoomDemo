package com.gxd.demo.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Single

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Long

    @Insert
    fun insertRx(obj: T): Single<Long>

    @Insert
    fun insertRx(objList: List<T>): Single<List<Long>>

    @Insert
    fun insert(objList: List<T>)

    /**
     * 返回成功行数
     */
    @Update
    fun update(obj: T): Single<Int>

    @Delete
    fun delete(obj: T): Single<Int>
}