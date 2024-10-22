package com.example.student_login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.student_login.api.VuAPI
import com.example.student_login.ui.theme.StudentloginTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.example.student_login.screens.StudentDashboard
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var vuAPI: VuAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Use lifecycleScope instead of GlobalScope
        lifecycleScope.launch {
            try {
                val response = vuAPI.getTopics("photography")
                Log.d("Test Vu Api", response.body().toString())
            } catch (e: Exception) {
                Log.e("Test Vu Api", "Error fetching data", e)
            }
        }

        setContent {
            StudentloginTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentDashboard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
