package com.gxd.demo.room.table

import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "fts_person")
@Fts4(contentEntity = Person::class)
data class PersonFTS(
    val name: String,
    val age: Int
)