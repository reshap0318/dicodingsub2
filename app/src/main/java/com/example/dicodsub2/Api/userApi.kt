package com.example.dicodsub2.Api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface userApi {
    @GET("search/users")
    @Headers("Authorization: token aaf5a754ca2dac6f7e6a3faed6e198b65a677e4f")
    fun search(@Query("q") id: String): Call<ResponseBody>

    @GET("users/{username}")
    @Headers("Authorization: token aaf5a754ca2dac6f7e6a3faed6e198b65a677e4f")
    fun detailUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token aaf5a754ca2dac6f7e6a3faed6e198b65a677e4f")
    fun followerUser(@Path("username") username: String): Call<ResponseBody>

    @GET("/users/{username}/following")
    @Headers("Authorization: token aaf5a754ca2dac6f7e6a3faed6e198b65a677e4f")
    fun followingUser(@Path("username") username: String): Call<ResponseBody>
}