package com.gxd.demo.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gxd.demo.room.dao.ClothesDao
import com.gxd.demo.room.dao.PersonDao
import com.gxd.demo.room.table.Address
import com.gxd.demo.room.table.Clothes
import com.gxd.demo.room.table.Person
import com.gxd.demo.room.thread.ioThread
import java.io.File

@Database(entities = [Person::class, Clothes::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    abstract fun clothesDao(): ClothesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            File(context.applicationContext.getExternalFilesDir(null), "room_test.db").absolutePath
        ).addCallback(object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {// 当数据库第一次被创建时调用此方法
                ioThread {
                    getInstance(context).personDao().insert(PREPOPULATE_DATA)// 预填充数据,插入真数据之后貌似回清除预填充数据
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                // 当数据库被打开时回调此方法
            }
        }).build()

        val PREPOPULATE_DATA = listOf(
            Person(name = "gxd", age = 31, firstAddress = Address("cn")),
            Person(name = "pdd", age = 42, firstAddress = Address("us"))
        )
    }
}