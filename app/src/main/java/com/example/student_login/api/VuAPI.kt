package com.example.student_login.api

import com.example.student_login.model.AuthKey
import com.example.student_login.model.Credentials
import com.example.student_login.model.Topic
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VuAPI {

    @GET("/dashboard/{key}")
    suspend fun getTopics(@Path("key") key: String): Response<Topic>;

    @POST("/sydney/auth")
    suspend fun login(@Body credentials: Credentials): Response<AuthKey>
}