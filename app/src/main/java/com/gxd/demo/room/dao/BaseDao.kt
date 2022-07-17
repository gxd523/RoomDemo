package com.gxd.demo.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import io.reactivex.Completable

interface BaseDao<T> {
    @Insert
    fun insert(obj: T): Completable

    @Insert
    fun insertRx(objList: List<T>): Completable

    @Insert
    fun insert(objList: List<T>)

    @Update
    fun update(obj: T): Completable

    @Delete
    fun delete(obj: T): Completable
}