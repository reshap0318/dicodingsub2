package com.example.dicodsub2.Activity

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodsub2.Adapter.userFavoritAdapter
import com.example.dicodsub2.Helper.MappingHelper
import com.example.dicodsub2.R
import com.example.dicodsub2.db.DatabaseContract
import com.example.dicodsub2.db.DatabaseContract.UserColumn.Companion.CONTENT_URI_USER
import com.example.dicodsub2.db.UserHelper
import kotlinx.android.synthetic.main.activity_favorit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoritActivity : AppCompatActivity() {
    private lateinit var userAdapter: userFavoritAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorit)

        val layoutManager = LinearLayoutManager(this)
        userAdapter = userFavoritAdapter{user ->
            val intent = Intent(this, FavoritDetailActivity::class.java)
            intent.putExtra("data",user)
            startActivity(intent)
        }
        userAdapter.notifyDataSetChanged()
        rv_list_user_favorit.layoutManager = layoutManager
        rv_list_user_favorit.adapter = userAdapter
        rv_list_user_favorit.addItemDecoration(DividerItemDecoration(applicationContext, layoutManager.orientation))

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        showLoading(true)
        loadData()

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadData()
            }
        }
        Log.d("URI_CONTENT_URI_USER",CONTENT_URI_USER.toString())
        contentResolver.registerContentObserver(CONTENT_URI_USER, true, myObserver)

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarFavorit.visibility = View.VISIBLE
        } else {
            progressBarFavorit.visibility = View.GONE
        }
    }

    fun loadData(){
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(DatabaseContract.UserColumn.CONTENT_URI_USER, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val users = deferredNotes.await()
            if (users.size > 0) {
                userAdapter.setData(users)
            } else {
                userAdapter.setData(ArrayList())
            }
        }
        showLoading(false)
    }
}