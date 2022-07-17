package com.gxd.demo.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.gxd.demo.room.table.Clothes
import io.reactivex.Completable

@Dao
interface ClothesDao : BaseDao<Clothes> {
    @Query("DELETE FROM t_clothes")
    fun deleteAllRx(): Completable
}