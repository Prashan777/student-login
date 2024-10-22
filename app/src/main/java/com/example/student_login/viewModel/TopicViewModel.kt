package com.example.student_login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_login.model.Entity
import com.example.student_login.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private var repository: TopicRepository) : ViewModel() {
    val topics: StateFlow<List<Entity>>
        get() = repository.topics

    val totalTopics: StateFlow<Int>
        get() = repository.totalTopics

    init {
        viewModelScope.launch {
            repository.getTopics("photography")
        }
    }
}