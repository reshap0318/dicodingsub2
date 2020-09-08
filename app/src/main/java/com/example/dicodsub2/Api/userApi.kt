package com.example.dicodsub2.Api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface userApi {
    @GET("search/users")
    @Headers("Authorization: token c1e861afa2357303c39173694a1d387f01fbd0b6")
    fun search(@Query("q") id: String): Call<ResponseBody>

    @GET("users/{username}")
    @Headers("Authorization: token c1e861afa2357303c39173694a1d387f01fbd0b6")
    fun detailUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token c1e861afa2357303c39173694a1d387f01fbd0b6")
    fun followerUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/following")
    @Headers("Authorization: token c1e861afa2357303c39173694a1d387f01fbd0b6")
    fun followingUser(@Path("username") username: String): Call<ResponseBody>
}