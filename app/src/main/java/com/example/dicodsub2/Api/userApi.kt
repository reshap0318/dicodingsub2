package com.example.dicodsub2.Api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface userApi {
    @GET("search/users")
    @Headers("Authorization: token f6dcebbd2ae8234ebdad0ccb757bb8106565e079")
    fun search(@Query("q") id: String): Call<ResponseBody>

    @GET("users/{username}")
    @Headers("Authorization: token f6dcebbd2ae8234ebdad0ccb757bb8106565e079")
    fun detailUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token f6dcebbd2ae8234ebdad0ccb757bb8106565e079")
    fun followerUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/following")
    @Headers("Authorization: token f6dcebbd2ae8234ebdad0ccb757bb8106565e079")
    fun followingUser(@Path("username") username: String): Call<ResponseBody>
}