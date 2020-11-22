package com.cocooncreations.topstories.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cocooncreations.topstories.data.local.constant.DatabaseConstant
import com.cocooncreations.topstories.data.local.dao.LocalDao
import com.cocooncreations.topstories.data.model.ResultEntity
import com.cocooncreations.topstories.utils.CustomTypeConverter

@Database(entities = arrayOf(ResultEntity::class), version = 1, exportSchema = false)
@TypeConverters(CustomTypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    /*
    localDao method to call insert, update, getList etc method from DAO interface
    */

    abstract fun localDao(): LocalDao

    /*
    initializing database object
    */

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    DatabaseConstant.DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
