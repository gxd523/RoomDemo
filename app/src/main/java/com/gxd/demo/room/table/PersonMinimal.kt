package com.gxd.demo.room.table

import androidx.room.ColumnInfo

data class PersonMinimal(
    @ColumnInfo(name = "p_id")
    val id: Int,
    val name: String,
    val age: Int
)