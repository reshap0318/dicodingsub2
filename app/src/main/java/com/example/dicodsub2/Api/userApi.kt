package com.example.dicodsub2.Api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface userApi {
    @GET("search/users")
    @Headers("Authorization: token 15251153465256d96c1329ecc7c85d6dcfa5512f")
    fun search(@Query("q") id: String): Call<ResponseBody>

    @GET("users/{username}")
    @Headers("Authorization: token 15251153465256d96c1329ecc7c85d6dcfa5512f")
    fun detailUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token 15251153465256d96c1329ecc7c85d6dcfa5512f")
    fun followerUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/following")
    @Headers("Authorization: token 15251153465256d96c1329ecc7c85d6dcfa5512f")
    fun followingUser(@Path("username") username: String): Call<ResponseBody>
}