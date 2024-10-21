package com.example.student_login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.student_login.ui.theme.StudentloginTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentloginTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    LoginScreen()
                    // Or, if you want to use StudentDashboard, you can uncomment this line:
                     StudentDashboard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
