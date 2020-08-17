package com.example.dicodsub2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodsub2.Adapter.userAdapter
import com.example.dicodsub2.Api.apiBuilder
import com.example.dicodsub2.Api.userApi
import com.example.dicodsub2.Model.User
import com.example.dicodsub2.R
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val displayList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadAdapter("a")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if(menuItem != null){

            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    displayList.clear()
                    loadAdapter(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    displayList.clear()
                    if(newText!!.isNotEmpty()){
                        val search = newText.toLowerCase(Locale.getDefault())
//                        Toast.makeText(this@MainActivity, search, Toast.LENGTH_SHORT).show()
//                        requestData(search)
                    }
                    else{
//                        requestData("a")
                    }
                    return false
                }
            })

        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun loadAdapter(data: String){
        val layoutManager = LinearLayoutManager(this)
        val userAdapter = userAdapter{user ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("data",user)
            startActivity(intent)
        }
        rv_list_user_main.adapter = userAdapter
        rv_list_user_main.layoutManager = layoutManager
        rv_list_user_main.addItemDecoration(DividerItemDecoration(applicationContext, layoutManager.orientation))
        requestData(data)

        Toast.makeText(this@MainActivity, displayList.size.toString(), Toast.LENGTH_SHORT).show()
        userAdapter.datas = displayList
    }

    private fun requestData(search: String) {
//        displayList.clear()
        val apiBuilder = apiBuilder.buildService(userApi::class.java)
        val requestSearch = apiBuilder.search(search)

        requestSearch.enqueue(object: Callback<ResponseBody>{

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful()){
                    try {
                        val result = response.body()?.string()
                        val responseObject = JSONObject(result)
                        val items = responseObject.getJSONArray("items");
                        for (i in 0 until items.length()) {
                            val jsonObject = items.getJSONObject(i)
                            val username = jsonObject.getString("login")
                            requestDetailUser(username)
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Gagal Load Data", Toast.LENGTH_SHORT).show()
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
        requestDetail.enqueue(object: Callback<ResponseBody>{
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
                        rv_list_user_main.adapter!!.notifyDataSetChanged()
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Gagal Load Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        })
    }

}
