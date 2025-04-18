package com.assignment.smarthealthwear.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.assignment.smarthealthwear.R
import com.assignment.smarthealthwear.sensor.StepTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StepCalorieScreen(navController: NavHostController) {
    val context = LocalContext.current
    val stepTracker = remember { StepTracker(context) }

    val steps = stepTracker.stepFlow.collectAsState()
    val calories = stepTracker.caloriesFlow.collectAsState()

    fun isRunningOnEmulator(): Boolean {
        return (android.os.Build.FINGERPRINT.startsWith("generic")
                || android.os.Build.FINGERPRINT.lowercase().contains("emulator")
                || android.os.Build.MODEL.contains("Emulator")
                || android.os.Build.MODEL.contains("Android SDK built for x86")
                || android.os.Build.MANUFACTURER.contains("Genymotion")
                || android.os.Build.BRAND.startsWith("generic") && android.os.Build.DEVICE.startsWith("generic")
                || "google_sdk" == android.os.Build.PRODUCT)
    }

    LaunchedEffect(Unit) {
        if (!isRunningOnEmulator()){
            stepTracker.start()
        } else {
            stepTracker.startTest()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            // Suspend function workaround inside coroutine
            CoroutineScope(Dispatchers.Default).launch {
                if (!isRunningOnEmulator()){
                    stepTracker.stop()
                } else {
                    stepTracker.stopTest()
                }
            }
        }
    }

    Scaffold(timeText = { TimeText() }) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val iconSize = screenWidth * 0.20f
            val spacing = screenHeight * 0.03f
            val textSize = screenWidth * 0.12f

            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = screenWidth * 0.1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    // Step Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing)
                            .clip(RoundedCornerShape(12.dp)),
                        backgroundPainter = CardDefaults.cardBackgroundPainter(),
                        onClick = {}
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = spacing),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_steps),
                                contentDescription = "Steps Icon",
                                tint = Color(0xFF2196F3),
                                modifier = Modifier.size(iconSize)
                            )
                            Text(
                                text = "${steps.value} steps",
                                fontSize = textSize.value.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }

                item {
                    // Calorie Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing)
                            .clip(RoundedCornerShape(12.dp)),
                        backgroundPainter = CardDefaults.cardBackgroundPainter(),
                        onClick = {}
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = spacing),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_calories_burned),
                                contentDescription = "Calories Icon",
                                tint = Color(0xFFFF5722),
                                modifier = Modifier.size(iconSize)
                            )
                            Text(
                                text = "${calories.value} kcal burned",
                                fontSize = textSize.value.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(spacing))
                    Chip(
                        label = { Text("Back to Dashboard") },
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ChipDefaults.primaryChipColors(
                            backgroundColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(spacing))
                }
            }
        }
    }
}
