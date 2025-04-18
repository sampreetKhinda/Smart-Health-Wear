package com.assignment.smarthealthwear.repository

import android.content.Context
import com.assignment.smarthealthwear.db.HealthDatabase
import com.assignment.smarthealthwear.manager.sendHealthDataToPhone
import com.assignment.smarthealthwear.model.HealthData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HealthDataRepository(private val context: Context) {
    private val dao = HealthDatabase.getInstance(context).healthDataDao()

    suspend fun insertHealthData(
        steps: Int,
        calories: Int,
        heartRate: Int? = null,
        spo2: Int? = null,
        syncToPhone: Boolean = true
    ) {
        val data = HealthData(
            steps = steps,
            calories = calories,
            heartRate = heartRate,
            spo2 = spo2,
        )
        withContext(Dispatchers.IO) {
            dao.insert(data)
        }
        // Safe to access context on main/default thread
        if (syncToPhone) {
            sendHealthDataToPhone(
                context = this.context,
                steps = steps,
                calories = calories,
                heartRate = heartRate,
                spo2 = spo2
            )
        }
    }

    suspend fun insertPartialData(
        steps: Int? = null,
        calories: Int? = null,
        heartRate: Int? = null,
        spo2: Int? = null,
        syncToPhone: Boolean = true,
    ) {
        val data = HealthData(
            steps = steps,
            calories = calories,
            heartRate = heartRate,
            spo2 = spo2
        )
        withContext(Dispatchers.IO) {
            dao.insert(data)
        }
        if (syncToPhone) {
            sendHealthDataToPhone(
                context = context,
                steps = steps,
                calories = calories,
                heartRate = heartRate,
                spo2 = spo2
            )
        }

    }

    suspend fun getAllHealthRecords(): List<HealthData> = withContext(Dispatchers.IO) {
        dao.getAll()
    }
}
