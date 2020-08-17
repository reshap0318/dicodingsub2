package com.example.dicodsub2.Api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface userApi {
    @GET("search/users")
    @Headers("Authorization: token ef4541f0ef998b489798179e5996032f3bd4cdf0")
    fun search(@Query("q") id: String): Call<ResponseBody>

    @GET("users/{username}")
    @Headers("Authorization: token ef4541f0ef998b489798179e5996032f3bd4cdf0")
    fun detailUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ef4541f0ef998b489798179e5996032f3bd4cdf0")
    fun followerUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/following")
    @Headers("Authorization: token ef4541f0ef998b489798179e5996032f3bd4cdf0")
    fun followingUser(@Path("username") username: String): Call<ResponseBody>
}