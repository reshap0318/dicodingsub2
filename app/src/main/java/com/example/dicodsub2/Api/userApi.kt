package com.example.dicodsub2.Api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface userApi {
    @GET("search/users")
    @Headers("Authorization: token")
    fun search(@Query("q") id: String): Call<ResponseBody>

    @GET("users/{username}")
    @Headers("Authorization: token")
    fun detailUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token")
    fun followerUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/following")
    @Headers("Authorization: token")
    fun followingUser(@Path("username") username: String): Call<ResponseBody>
}