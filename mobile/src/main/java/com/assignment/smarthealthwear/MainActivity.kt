package com.assignment.smarthealthwear

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.assignment.smarthealthwear.companionListener.HealthDataListener
import com.assignment.smarthealthwear.view.HealthDataScreen
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable
import org.json.JSONObject

class MainActivity : ComponentActivity(), DataClient.OnDataChangedListener {
    private val dataListener = HealthDataListener()
    private var steps = mutableStateOf<Int?>(null)
    private var calories = mutableStateOf<Int?>(null)
    private var heartRate = mutableStateOf<Int?>(null)
    private var spo2 = mutableStateOf<Int?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HealthDataScreen(steps.value, calories.value, heartRate.value, spo2.value)
        }
    }

    override fun onResume() {
        super.onResume()
        Wearable.getDataClient(this).addListener(dataListener)
    }

    override fun onPause() {
        super.onPause()
        Wearable.getDataClient(this).removeListener(dataListener)
    }


    override fun onDataChanged(dataEvents: DataEventBuffer) {
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED &&
                event.dataItem.uri.path == "/health-data") {

                val json = DataMapItem.fromDataItem(event.dataItem)
                    .dataMap.getString("health_json")

                val data = JSONObject(json)
                steps.value = data.optInt("steps", -1).takeIf { it >= 0 }
                calories.value = data.optInt("calories", -1).takeIf { it >= 0 }
                heartRate.value = data.optInt("heartRate", -1).takeIf { it >= 0 }
                spo2.value = data.optInt("spo2", -1).takeIf { it >= 0 }
            }
        }
    }
}
