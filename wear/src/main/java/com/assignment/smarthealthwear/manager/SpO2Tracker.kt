package com.assignment.smarthealthwear.sensor

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.PassiveMonitoringClient
import androidx.health.services.client.clearPassiveListenerCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer

import androidx.health.services.client.data.DataType
import com.assignment.smarthealthwear.repository.HealthDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SpO2Tracker(context: Context) {
    private var job: Job? = null
    val spO2Flow = MutableStateFlow<Int?>(null)
    private val repository = HealthDataRepository(context)
    private val callback = object : PassiveListenerCallback {
        override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {

        }

    }



    fun start() {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                spO2Flow.value = (94..99).random()
                repository.insertPartialData(
                    heartRate = spO2Flow.value!!.toInt()
                )
                delay(5000)
            }
        }
    }

    fun stop() {
        job?.cancel()
    }
}
