package com.gxd.demo.room.table

import androidx.room.*

/**
 * 如果您的应用需要通过全文搜索 (FTS) 快速访问数据库信息，请使用虚拟表（使用 FTS3 或 FTS4 SQLite 扩展模块）为您的实体提供支持
 */
//@Fts4
@Entity(tableName = "t_person")
data class Person
constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "p_id")
    val id: Int = 0,
    /**
     * 不修改字段名就不需要加@ColumnInfo注解
     */
    val name: String,
    val age: Int,
    /**
     * 实体类中引用其他实体类
     */
    @Embedded(prefix = "first")
    val firstAddress: Address,

    @Embedded(prefix = "second")
    val secondAddress: Address? = null
) {
    /**
     * Ignore表示该字段不存入表中
     */
    @Ignore
    val income: Int? = null
}

data class Address(
    val country: String,
    val city: String? = null,
)