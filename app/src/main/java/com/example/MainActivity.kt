package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        AppNavigation()
      }
    }
  }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                onNavigateToMarket = { navController.navigate("marketplace") },
                onNavigateToStudio = { navController.navigate("studio") },
                onNavigateToLogistics = { navController.navigate("logistics") },
                onNavigateToMessages = { navController.navigate("messages") }
            )
        }
        composable("marketplace") {
            MarketplaceScreen(onBack = { navController.popBackStack() })
        }
        composable("studio") {
            StudioScreen(onBack = { navController.popBackStack() })
        }
        composable("logistics") {
            LogisticsScreen(onBack = { navController.popBackStack() })
        }
        composable("messages") {
            MessagesScreen(onBack = { navController.popBackStack() })
        }
    }
}
