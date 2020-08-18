package com.example.dicodsub2.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodsub2.Api.apiBuilder
import com.example.dicodsub2.Api.userApi
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class UserViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun setData(search: String) {
        val displayList = ArrayList<User>()
        val apiBuilder = apiBuilder.buildService(userApi::class.java)
        val requestSearch = apiBuilder.search(search)
        requestSearch.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful()){
                    try {
                        Log.d("Pesan Saya", "Berhasil Load Data")
                        val result = response.body()?.string()
                        val responseObject = JSONObject(result)
                        val items = responseObject.getJSONArray("items");
                        for (i in 0 until items.length()) {
                            val jsonObject = items.getJSONObject(i)
                            val username = jsonObject.getString("login")
                            val requestDetail = apiBuilder.detailUser(username)
                            requestDetail.enqueue(object: Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if(response.isSuccessful()){
                                        try {
                                            val result = response.body()?.string()
                                            val responseObject = JSONObject(result)
                                            val username =  responseObject.getString("login")
                                            val name = responseObject.getString("name")
                                            val avatar = responseObject.getString("avatar_url")
                                            val compony = responseObject.getString("company")
                                            val location = responseObject.getString("location")
                                            val repo = responseObject.getInt("public_repos")
                                            val follower = responseObject.getInt("followers")
                                            val following = responseObject.getInt("following")
                                            displayList.add(User(
                                                username,
                                                name,
                                                avatar,
                                                compony,
                                                location,
                                                repo,
                                                follower,
                                                following
                                            ))
                                            listUsers.postValue(displayList)
                                        } catch (e: Exception) {
                                            Log.d("Exception", e.message.toString())
                                            e.printStackTrace()
                                        }
                                    }else{
                                        Log.d("Response_Gagal", "Respone Gagal load detail data")
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Log.e("debug", "onFailure: ERROR > " + t.toString());
                                }
                            })
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                    Log.d("Response_Gagal", "Respone Gagal load usernmae data")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }

        })
    }

    fun getData(): LiveData<ArrayList<User>> {
        return listUsers
    }
}