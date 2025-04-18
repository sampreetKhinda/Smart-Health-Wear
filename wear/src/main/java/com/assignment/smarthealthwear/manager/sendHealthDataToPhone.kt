package com.assignment.smarthealthwear.manager

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

fun sendHealthDataToPhone(context: Context, steps: Int?, calories: Int?, heartRate: Int?, spo2: Int?) {
    val path = "/health-data"
    val dataMapRequest = PutDataMapRequest.create(path)

    steps?.let { dataMapRequest.dataMap.putInt("steps", it) }
    calories?.let { dataMapRequest.dataMap.putInt("calories", it) }
    heartRate?.let { dataMapRequest.dataMap.putInt("heartRate", it) }
    spo2?.let { dataMapRequest.dataMap.putInt("spo2", it) }
    dataMapRequest.dataMap.putLong("timestamp", System.currentTimeMillis())

    val request = dataMapRequest.asPutDataRequest().setUrgent()
    val dataClient = Wearable.getDataClient(context)

    dataClient.putDataItem(request)
        .addOnSuccessListener {
            Log.d("WearSync", "Health data sent successfully: $it")
        }
        .addOnFailureListener { e ->
            Log.e("WearSync", "Failed to send health data", e)
        }
}
