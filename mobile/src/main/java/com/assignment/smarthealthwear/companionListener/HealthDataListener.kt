package com.assignment.smarthealthwear.companionListener

import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import org.json.JSONObject

class HealthDataListener : DataClient.OnDataChangedListener {

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED &&
                event.dataItem.uri.path == "/health-data"
            ) {
                val json = DataMapItem.fromDataItem(event.dataItem)
                    .dataMap.getString("health_json")

                val data = JSONObject(json)
                val steps = data.optInt("steps", -1)
                val calories = data.optInt("calories", -1)
                val heartRate = data.optInt("heartRate", -1)
                val spo2 = data.optInt("spo2", -1)

                Log.d("PhoneSync", "Received health data:")
                Log.d("PhoneSync", "Steps: $steps, Calories: $calories, HR: $heartRate, SpO₂: $spo2")

            }
        }
    }
}