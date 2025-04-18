package com.assignment.smarthealthwear.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.smarthealthwear.model.HealthData

@Dao
interface HealthDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: HealthData)

    @Query("SELECT * FROM health_data ORDER BY timestamp DESC")
    suspend fun getAll(): List<HealthData>
}