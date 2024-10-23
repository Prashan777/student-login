package com.example.student_login.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.student_login.model.Entity
import com.example.student_login.viewModel.TopicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicDetails(
    subject: String?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topicViewModel: TopicViewModel = hiltViewModel()
    val topic = topicViewModel.topic.collectAsState().value
    val isLoading = topicViewModel.isLoading.collectAsState().value

    LaunchedEffect(subject) {
        Log.d("TopicDetails", "Subject received: $subject")
        topicViewModel.getTopicBySubject(subject)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Topic Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                topic != null -> {
                    Text(
                        text = topic.subject,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    InfoItem(label = "Description", value = topic.description)
                    InfoItem(label = "Equipment", value = topic.equipment)
                    InfoItem(label = "Pioneering Photographer", value = topic.pioneeringPhotographer)
                    InfoItem(label = "Technique", value = topic.technique)
                    InfoItem(label = "Year Introduced", value = topic.yearIntroduced.toString())
                }
                else -> {
                    Text(
                        text = "Topic not found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun InfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
    }
}
