package com.example.student_login.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.student_login.ui.theme.StudentloginTheme
import com.example.student_login.viewModel.TopicViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.student_login.model.Entity
import com.example.student_login.viewModel.AuthViewModel
import javax.security.auth.Subject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboard(modifier: Modifier = Modifier, onClick :(subject: String) -> Unit ) {

    val topicViewModel: TopicViewModel = hiltViewModel()
    val topics = topicViewModel.topics.collectAsState()
    val totalTopics = topicViewModel.totalTopics.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Dashboard") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TitleCard("Topics")
                }

               item {
                   topics.value.forEach { topic ->
                       DashboardCard(
                           title = topic.subject,
                           subtitle = topic.description,
                           onClick = onClick
                       )
                       Spacer(
                           modifier = Modifier.height(8.dp)
                       )
                   }
               }
            }
        }
    )
}


@Composable
fun TitleCard(title: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, subtitle: String, onClick :(subject: String) -> Unit ) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onClick(title);
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = subtitle, fontSize = 16.sp)
        }
    }
}
