package com.example.student_login.repository

import android.util.Log
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

    private var  _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    suspend fun login(credentials: Credentials){
        _errorMessage.value = ""
        var response = vuAPI.login(credentials);
        if (response.isSuccessful && response.body() != null) {

            if (response.code() != 200) {
                _errorMessage.emit("Username or password do not match")
            }
            _keypass.emit(response.body()?.keypass!!)
            _isLoggedIn.emit(true)
        } else {
            _errorMessage.emit("Username or password do not match")
        }
    }
}