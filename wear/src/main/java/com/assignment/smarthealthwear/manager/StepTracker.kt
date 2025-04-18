package com.assignment.smarthealthwear.sensor

import android.content.Context
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.PassiveMonitoringClient
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import com.assignment.smarthealthwear.repository.HealthDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StepTracker(context: Context) {

    private val client: PassiveMonitoringClient =
        HealthServices.getClient(context).passiveMonitoringClient
    private var job: Job? = null
    private val _stepFlow = MutableStateFlow(0)
    val stepFlow: StateFlow<Int> = _stepFlow

    private val _caloriesFlow = MutableStateFlow(0)
    val caloriesFlow: StateFlow<Int> = _caloriesFlow
    private val repository = HealthDataRepository(context)
    private val callback = object : PassiveListenerCallback {
        override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
            dataPoints.getData(DataType.STEPS).forEach { point ->
                val steps = point.value.toInt()
                CoroutineScope(Dispatchers.Default).launch {
                    repository.insertPartialData(steps = steps)
                }
                _stepFlow.value = steps
                _caloriesFlow.value = (steps * 0.04).toInt() // Estimate: 0.04 kcal/step
            }
        }

    }

    fun startTest() {
        job = CoroutineScope(Dispatchers.Default).launch {
            var steps = 0
            while (isActive) {
                delay(2000)
                steps += 1
                repository.insertPartialData(steps = steps)
                _stepFlow.value = steps
                _caloriesFlow.value = (steps * 0.04).toInt()
            }
        }
    }

    fun stopTest() {
        job?.cancel()
    }

    suspend fun start() {
        val config = PassiveListenerConfig.Builder()
            .setDataTypes(setOf(DataType.STEPS))
            .build()

        client.setPassiveListenerCallback(config, callback)
    }

    suspend fun stop() {
        client.clearPassiveListenerCallbackAsync()
    }
}