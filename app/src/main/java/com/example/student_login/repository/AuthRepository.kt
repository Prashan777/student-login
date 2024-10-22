package com.example.student_login.repository

import com.example.student_login.api.VuAPI
import com.example.student_login.model.Credentials
import com.example.student_login.model.Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthRepository @Inject constructor( private var vuAPI: VuAPI) {

    private var _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn: StateFlow<Boolean>
        get() = _isLoggedIn

    private var _keypass = MutableStateFlow<String>("")
    val keyPass: StateFlow<String>
        get() = _keypass

    suspend fun login(credentials: Credentials){
        var response = vuAPI.login(credentials);
        if (response.isSuccessful && response.body() != null) {
            _isLoggedIn.emit(true)
            _keypass.emit(response.body()?.keypass!!)
        }
    }
}