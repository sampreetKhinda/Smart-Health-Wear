package com.assignment.smarthealthwear.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.assignment.smarthealthwear.model.HealthData

@Database(entities = [HealthData::class], version = 1)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun healthDataDao(): HealthDataDao

    companion object {
        @Volatile private var INSTANCE: HealthDatabase? = null

        fun getInstance(context: Context): HealthDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "health_data.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
