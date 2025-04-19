/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.assignment.smarthealthwear.presentation


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.assignment.smarthealthwear.presentation.theme.SmartHealthWearTheme
import com.assignment.smarthealthwear.presentation.view.DashboardScreen
import com.assignment.smarthealthwear.presentation.view.HeartRateScreen
import com.assignment.smarthealthwear.presentation.view.SpO2Screen
import com.assignment.smarthealthwear.presentation.view.StepCalorieScreen
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHealthWearTheme {
                val navController = rememberSwipeDismissableNavController()

                SwipeDismissableNavHost(
                    navController = navController,
                    startDestination = "dashboard"
                ) {
                    composable("dashboard") {
                        DashboardScreen(navController)
                    }
                    composable("heart_rate") {
                        HeartRateScreen(navController)
                    }
                    composable("spo2") {
                        SpO2Screen(navController)
                    }
                    composable("step_calorie") {
                        StepCalorieScreen(navController)
                    }
                }
            }
        }

        Wearable.getCapabilityClient(this)
            .getCapability("health_data_receiver", CapabilityClient.FILTER_REACHABLE)
            .addOnSuccessListener { capabilityInfo ->
                val nodes = capabilityInfo.nodes
                if (nodes.isNotEmpty()) {
                    Log.d("WearSync", "Connected to phone!")
                } else {
                    Log.d("WearSync", "Phone app NOT connected or capability not registered.")
                }
            }
            .addOnFailureListener {
                Log.e("WearSync", "Capability check failed", it)
            }
    }
}
