package com.anucodes.vrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anucodes.vrid.presentation.BlogDetailScreen
import com.anucodes.vrid.presentation.HomeScreen
import com.anucodes.vrid.presentation.viewModel.AppViewModel
import com.anucodes.vrid.ui.theme.VridTheme

class MainActivity : ComponentActivity() {

    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            VridTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home"){
                            HomeScreen(
                                appViewModel = appViewModel,
                                navController = navController
                            )
                        }

                        composable("details"){
                            BlogDetailScreen(
                                appViewModel = appViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}