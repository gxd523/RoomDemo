package com.gxd.demo.room.table

import androidx.room.*

/**
 * 如果您的应用需要通过全文搜索 (FTS) 快速访问数据库信息，请使用虚拟表（使用 FTS3 或 FTS4 SQLite 扩展模块）为您的实体提供支持
 */
@Entity(tableName = "t_person")
data class Person
constructor(
    /**
     * SQLite中的表和列名称不区分大小写
     * 使用全文搜索时，铸剑名称必须为p_id且类型为INTEGER
     */
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
    @Embedded(prefix = "first_")
    val firstAddress: Address,

    @Embedded(prefix = "second_")
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