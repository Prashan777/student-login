package com.example.student_login.repository

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.example.student_login.api.VuAPI
import com.example.student_login.model.Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class TopicRepository @Inject constructor(
    private var vuAPI: VuAPI,
    private val savedStateHandle: SavedStateHandle
) {
    private var _topics = MutableStateFlow<List<Entity>>(emptyList())
    val topics: StateFlow<List<Entity>>
        get() = _topics

    private var _totalTopics = MutableStateFlow<Int>(0)
    val totalTopics: StateFlow<Int>
        get() = _totalTopics

    private var _currentTopic = MutableStateFlow<Entity?>(null)
    val currentTopic: StateFlow<Entity?>
        get() = _currentTopic

    suspend fun getTopics() {
        try {
            Log.d("TopicRepository", "Fetching topics from API")
            val key = savedStateHandle.get<String>("keypass") ?: "photography"
            val response = vuAPI.getTopics(key)

            if (response.isSuccessful && response.body() != null) {
                _topics.emit(response.body()?.entities!!)
                _totalTopics.emit(response.body()?.entityTotal!!)
                Log.d("TopicRepository", "Successfully loaded ${_topics.value.size} topics")
            } else {
                Log.e("TopicRepository", "Failed to load topics: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("TopicRepository", "Error loading topics", e)
        }
    }

    fun getTopicsBySubject(explicitSubject: String?) {
        val subject = explicitSubject ?: savedStateHandle.get<String>("subject") ?: ""
        Log.d("TopicRepository", "Searching for subject: $subject")
        Log.d("TopicRepository", "Available topics: ${topics.value.size}")

        val foundTopic = topics.value.find { topic ->
            Log.d("TopicRepository", "Comparing '${topic.subject}' with '$subject'")
            topic.subject == subject
        }

        Log.d("TopicRepository", "Found topic: $foundTopic")
        _currentTopic.value = foundTopic
    }
}