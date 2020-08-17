package com.example.dicodsub2.Activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodsub2.Adapter.userAdapter
import com.example.dicodsub2.Api.apiBuilder
import com.example.dicodsub2.Api.userApi
import com.example.dicodsub2.Model.User
import com.example.dicodsub2.R
import kotlinx.android.synthetic.main.fragment_following.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingFragment : Fragment() {

    companion object {
        var EXTRA_USERAME = "USERNAME"
    }

    val displayList = ArrayList<User>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val username = arguments?.getString(FollowingFragment.EXTRA_USERAME)

            val layoutManager = LinearLayoutManager(activity)
            val userAdapter = userAdapter{user ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("data",user)
                startActivity(intent)
            }
            rv_list_user_following.adapter = userAdapter
            rv_list_user_following.layoutManager = layoutManager
            if (username != null) {
                requestData(username)
            }
            userAdapter.datas = displayList
        }
    }

    private fun requestData(username: String) {
        val apiBuilder = apiBuilder.buildService(userApi::class.java)
        val requestSearch = apiBuilder.followingUser(username)
        requestSearch.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful()){
                    try {
                        val result = response.body()?.string()
                        val responseItems = JSONArray(result)
                        for (i in 0 until responseItems.length()) {
                            val jsonObject = responseItems.getJSONObject(i)
                            val login = jsonObject.getString("login")
                            requestDetailUser(login)
                        }
                    } catch (e: Exception) {
                        Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(activity, "Gagal Load Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }

        })
    }

    private fun requestDetailUser(username: String){
        val apiBuilder = apiBuilder.buildService(userApi::class.java)
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
                        rv_list_user_following.adapter!!.notifyDataSetChanged()
                    } catch (e: Exception) {
                        Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(activity, "Gagal Load Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        })
    }


}
