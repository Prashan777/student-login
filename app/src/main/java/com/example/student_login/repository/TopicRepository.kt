package com.example.student_login.repository

import com.example.student_login.api.VuAPI
import com.example.student_login.model.Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TopicRepository @Inject constructor(private var vuAPI: VuAPI) {

    private var _topics = MutableStateFlow<List<Entity>>(emptyList())
    val topics: StateFlow<List<Entity>>
        get() = _topics

    private var _totalTopics = MutableStateFlow<Int>(0)
    val totalTopics: StateFlow<Int>
        get() = _totalTopics

    suspend fun getTopics(key: String) {
        val response = vuAPI.getTopics(key)
        if (response.isSuccessful && response.body() != null) {
            _topics.emit(response.body()?.entities!!)
            _totalTopics.emit(response.body()?.entityTotal!!)
        }
    }
}