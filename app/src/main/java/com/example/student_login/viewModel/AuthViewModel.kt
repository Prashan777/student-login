package com.example.student_login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_login.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private var repository: AuthRepository) : ViewModel() {
    val isLoggedIn: StateFlow<Boolean>
        get() = repository.isLoggedIn

    val keyPass: StateFlow<String>
        get() = repository.keyPass

    init {
        viewModelScope.launch {

        }
    }
}