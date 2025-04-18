package com.assignment.smarthealthwear.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.assignment.smarthealthwear.R
import com.assignment.smarthealthwear.sensor.SpO2Tracker

@Composable
fun SpO2Screen(navController: NavHostController) {

    val context = LocalContext.current
    val spO2Tracker = remember { SpO2Tracker(context) }
    val spO2 = spO2Tracker.spO2Flow.collectAsState()
    var isLow = true
    LaunchedEffect(Unit) {
        spO2Tracker.start()
        //isLow = spO2.value!! < 90
    }

    DisposableEffect(Unit) {
        onDispose { spO2Tracker.stop() }
    }




    Scaffold(
        timeText = { TimeText() }
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val iconSize = screenWidth * 0.25f
            val spacing = screenHeight * 0.03f
            val textSize = screenWidth * 0.12f

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = screenWidth * 0.1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_oxygen),
                    contentDescription = "SpO₂ Icon",
                    tint = if (isLow) Color.Red else Color.Green,
                    modifier = Modifier.size(36.dp)
                )

                Text(
                    text = "${spO2.value}%",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = if (isLow) "Oxygen level is low!" else "Oxygen level is normal",
                    fontSize = 14.sp,
                    color = if (isLow) Color.Red else Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                Chip(
                    label = { Text("Back to Dashboard") },
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}