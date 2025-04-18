package com.assignment.smarthealthwear.presentation.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(
        timeText = { TimeText() }
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = screenWidth * 0.1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Smart Health Wear",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Chip(
                    label = { Text("Heart Rate") },
                    onClick = { navController.navigate("heart_rate") },
                    modifier = Modifier.fillMaxWidth()
                )

                Chip(
                    label = { Text("SpO₂ Monitoring") },
                    onClick = { navController.navigate("spo2") },
                    modifier = Modifier.fillMaxWidth()
                )

                Chip(
                    label = { Text("Steps & Calories") },
                    onClick = { navController.navigate("step_calorie") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
