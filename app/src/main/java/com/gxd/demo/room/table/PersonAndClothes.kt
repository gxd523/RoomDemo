package com.gxd.demo.room.table

import androidx.room.Embedded
import androidx.room.Relation

data class PersonAndClothes(
    @Embedded
    var person: Person? = null,
    @Relation(parentColumn = "p_id", entityColumn = "owner_id")
    var clothesList: List<Clothes> = emptyList()
)
