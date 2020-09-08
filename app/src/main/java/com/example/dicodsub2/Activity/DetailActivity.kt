package com.example.dicodsub2.Activity

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dicodsub2.Helper.MappingHelper
import com.example.dicodsub2.Model.User
import com.example.dicodsub2.Model.UserViewModel
import com.example.dicodsub2.R
import com.example.dicodsub2.SectionsPagerAdapter
import com.example.dicodsub2.db.DatabaseContract
import com.example.dicodsub2.db.DatabaseContract.UserColumn.Companion.CONTENT_URI_FOLLOWER
import com.example.dicodsub2.db.DatabaseContract.UserColumn.Companion.CONTENT_URI_FOLLOWING
import com.example.dicodsub2.db.DatabaseContract.UserColumn.Companion.CONTENT_URI_USER
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var followerViewModel: UserViewModel
    private lateinit var followingViewModel: UserViewModel
    private var followerData = ArrayList<User>()
    private var followingData = ArrayList<User>()
    private var isInDatabase = false
    private lateinit var uriUserWithUsername: Uri
    private lateinit var uriFollowerWithUsername: Uri
    private lateinit var uriFollowingWithUsername: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<User>("data")

        initData(data)

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        data.username?.let {
            followerViewModel.setDataFollower(it)
            followingViewModel.setDataFollowing(it)
            uriUserWithUsername = Uri.parse(CONTENT_URI_USER.toString() + "/" + it)
            uriFollowerWithUsername = Uri.parse(CONTENT_URI_FOLLOWER.toString() + "/" + it)
            uriFollowingWithUsername= Uri.parse(CONTENT_URI_FOLLOWING.toString() + "/" + it)
        }

        checkDataUser()

        followerViewModel.getDataFollower().observe(this, Observer { user ->
            if (user != null) {
                followerData = user
            }
        })

        followingViewModel.getDataFollowing().observe(this, Observer { user ->
            if (user != null) {
                followingData = user
            }
        })

        detail_like_fab.setOnClickListener{ view->
            if(isInDatabase){
                deleteFavorit()
            }else{
                saveFavorit(data)
            }
        }
    }

    fun initData(data: User){
        tv_name_detail_user.text = data.name
        tv_compony_detail_user.text = data.username
        tv_follower_detail_user.text = data.follower.toString()+" Followers"
        tv_following_detail_user.text = data.following.toString()+" Followings"
        tv_repo_detail_user.text = data.repository.toString()+" Repository"
        if(data.avatar != null){
            Glide.with(this)
                .load(data.avatar)
                .fitCenter()
                .centerCrop()
                .into(img_photo_detail_user);
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = data.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    fun saveFavorit(data: User){
        val values = ContentValues()
        values.put(DatabaseContract.UserColumn.USERNAME, data.username)
        values.put(DatabaseContract.UserColumn.NAME, data.name)
        values.put(DatabaseContract.UserColumn.AVATAR, data.avatar)
        values.put(DatabaseContract.UserColumn.REPOSITORY, data.repository)
        values.put(DatabaseContract.UserColumn.FOLLOWER, data.follower)
        values.put(DatabaseContract.UserColumn.FOLLOWING, data.following)

        data.username?.let {
            saveFollowerFavorit(it)
            saveFollowingFavorit(it)
        }

        contentResolver.insert(CONTENT_URI_USER, values)
        changeFAB(true)
        Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
    }

    fun saveFollowerFavorit(username: String){
        if(followerData.size > 0){
            for (data in followerData){
                val values = ContentValues()
                values.put(DatabaseContract.UserColumn.USERNAME, data.username)
                values.put(DatabaseContract.UserColumn.NAME, data.name)
                values.put(DatabaseContract.UserColumn.AVATAR, data.avatar)
                values.put(DatabaseContract.UserColumn.REPOSITORY, data.repository)
                values.put(DatabaseContract.UserColumn.FOLLOWER, data.follower)
                values.put(DatabaseContract.UserColumn.FOLLOWING, data.following)
                values.put(DatabaseContract.UserColumn.FOLLOWER_USERNAME, username)
                contentResolver.insert(CONTENT_URI_FOLLOWER, values)
                Log.d("INSERT_FOLLOWER","Berhasil Menambahkan Data $data.username")
            }
        }
    }

    fun saveFollowingFavorit(username: String){
        if(followingData.size > 0){
            for (data in followingData){
                val values = ContentValues()
                values.put(DatabaseContract.UserColumn.USERNAME, data.username)
                values.put(DatabaseContract.UserColumn.NAME, data.name)
                values.put(DatabaseContract.UserColumn.AVATAR, data.avatar)
                values.put(DatabaseContract.UserColumn.REPOSITORY, data.repository)
                values.put(DatabaseContract.UserColumn.FOLLOWER, data.follower)
                values.put(DatabaseContract.UserColumn.FOLLOWING, data.following)
                values.put(DatabaseContract.UserColumn.FOLLOWING_USERNAME, username)
                contentResolver.insert(CONTENT_URI_FOLLOWING, values)
                Log.d("INSERT_FOLLOWING","Berhasil Menambahkan Data $data.username")
            }
        }
    }

    fun deleteFavorit(){
        contentResolver.delete(uriFollowerWithUsername, null, null)
        contentResolver.delete(uriFollowingWithUsername, null, null)
        contentResolver.delete(uriUserWithUsername, null, null)
        changeFAB(false)
        Toast.makeText(this, "Berhasil Menghapus dari Favorit", Toast.LENGTH_SHORT).show()

    }

    fun checkDataUser(){
        val _this = this
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(uriUserWithUsername, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                _this.changeFAB(true)
            }
        }
    }

    fun changeFAB(status:Boolean){
        if(status){
            isInDatabase = true
            detail_like_fab.setImageResource(R.drawable.like)
        }else{
            isInDatabase = false
            detail_like_fab.setImageResource(R.drawable.unlike)
        }
    }

}
