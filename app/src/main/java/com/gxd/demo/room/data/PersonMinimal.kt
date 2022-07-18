package com.gxd.demo.room.data

import androidx.room.ColumnInfo

data class PersonMinimal(
    @ColumnInfo(name = "p_id")
    val id: Int,
    val name: String,
    val age: Int
)