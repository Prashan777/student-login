package com.example.student_login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.student_login.ui.theme.StudentloginTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.student_login.screens.LoginScreen
import com.example.student_login.screens.StudentDashboard
import com.example.student_login.screens.TopicDetails
import com.example.student_login.viewModel.AuthViewModel
import com.example.student_login.viewModel.TopicViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentloginTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                   App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()
    val keyPass = authViewModel.keyPass.collectAsState()

    val topicViewModel: TopicViewModel = hiltViewModel()

    // Set the starting destination based on the login state
    val startDestination = if (isLoggedIn.value) "dashboard/${keyPass.value}" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = "login") {
            LoginScreen(onClick = { credentials ->
                authViewModel.login(credentials)
            })
        }

        composable(route = "dashboard/{keypass}", arguments = listOf(
            navArgument("keypass") {
                type = NavType.StringType
            }
        )) {
            StudentDashboard(onClick = { subject ->
                navController.navigate("details/${subject}")
            })
        }

        composable(route = "details/{subject}", arguments = listOf(
            navArgument("subject") {
                type = NavType.StringType
            }
        )) {backStackEntry ->
            val subject = backStackEntry.arguments?.getString("subject")
            Log.d("Navigation", "Navigating to details with subject: $subject")
            TopicDetails(
                subject = subject,
                onBackClick = { navController.navigateUp() }
            )
        }
    }

    // Observe the isLoggedIn state and navigate when it changes
    LaunchedEffect(isLoggedIn.value) {
        if (isLoggedIn.value) {
            navController.navigate("dashboard/${keyPass.value}") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
}

