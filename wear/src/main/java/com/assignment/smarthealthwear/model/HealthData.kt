package com.assignment.smarthealthwear.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_data")
data class HealthData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val steps: Int? = null,
    val calories: Int? = null,
    val heartRate: Int? = null,
    val spo2: Int? = null
)
