package com.assignment.smarthealthwear.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HealthDataScreen(
    steps: Int?,
    calories: Int?,
    heartRate: Int?,
    spo2: Int?
) {
    /*Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //Text("Synced Health Data", style = MaterialTheme.typography.headlineSmall)

            steps?.let {
                //Text("🦶 Steps: $it")
            }

            calories?.let {
                //Text("🔥 Calories: $it kcal")
            }

            heartRate?.let {
                //Text("❤️ Heart Rate: $it bpm")
            }

            spo2?.let {
               // Text("🩸 SpO₂: $it %")
            }

            if (steps == null && calories == null && heartRate == null && spo2 == null) {
               // Text("Waiting for data from Wear OS...")
            }
        }
    }*/
}
