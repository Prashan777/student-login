package com.example.student_login.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_login.model.Entity
import com.example.student_login.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private var repository: TopicRepository) : ViewModel() {
    val topics: StateFlow<List<Entity>>
        get() = repository.topics

    val totalTopics: StateFlow<Int>
        get() = repository.totalTopics

    val topic: StateFlow<Entity?>
        get() = repository.currentTopic

    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    init {
        viewModelScope.launch {
            repository.getTopics()
        }
    }


    fun getTopicBySubject(subject: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("TopicViewModel", "Getting topic for subject: $subject")

            // First ensure topics are loaded
            repository.getTopics()

            // Then find the specific topic
            repository.getTopicsBySubject(subject)

            _isLoading.value = false
        }
    }
}