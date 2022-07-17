package com.gxd.demo.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "t_clothes",
    primaryKeys = ["brand", "color"]/*复合主键*/,
    foreignKeys = [ForeignKey(/*外键*/
        entity = Person::class,
        parentColumns = ["p_id"],
        childColumns = ["owner_id"],
        onUpdate = ForeignKey.CASCADE/*person表的一条数据的pid更新时会更新ownerId*/,
        onDelete = ForeignKey.CASCADE/*person表的一条数据的被删除时会删除与pid相同的ownerId的数据*/
    )]
)
data class Clothes(
    /**
     * 复合主键的字段
     */
    val brand: String,
    /**
     * 复合主键的字段
     */
    val color: String,
    @ColumnInfo(name = "owner_id")
    val ownerId: Int,
    val size: String
)
