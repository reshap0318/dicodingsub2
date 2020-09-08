package com.example.dicodsub2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dicodsub2.Helper.MappingHelper
import com.example.dicodsub2.Model.UserLite
import com.example.dicodsub2.R
import com.example.dicodsub2.SectionPagerAdapterFavorit
import com.example.dicodsub2.db.FollowerHelper
import com.example.dicodsub2.db.FollowingHelper
import com.example.dicodsub2.db.UserHelper
import kotlinx.android.synthetic.main.activity_favorit_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoritDetailActivity : AppCompatActivity() {

    private lateinit var userHelper: UserHelper
    private lateinit var followerHelper: FollowerHelper
    private lateinit var followingHelper: FollowingHelper
    private var isInDatabase = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorit_detail)

        val data = intent.getParcelableExtra<UserLite>("data")

        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()
        followerHelper = FollowerHelper.getInstance(applicationContext)
        followerHelper.open()
        followingHelper = FollowingHelper.getInstance(applicationContext)
        followingHelper.open()

        data.username?.let {
            checkDataUser(it)
        }

        initData(data)

        detail_like_fab_favorit.setOnClickListener{ view->
            if(isInDatabase){
                data.username?.let { deleteFavorit(it) }
            }
        }

    }

    fun initData(data: UserLite){
        tv_name_detail_favorit.text = data.name
        tv_username_detail_favorit.text = data.username
        tv_follower_detail_favorit.text = data.follower.toString()+" Followers"
        tv_following_detail_favorit.text = data.following.toString()+" Followings"
        tv_repo_detail_favorit.text = data.repository.toString()+" Repository"
        if(data.avatar != null){
            Glide.with(this)
                .load(data.avatar)
                .fitCenter()
                .centerCrop()
                .into(img_photo_detail_favorit);
        }

        //untuk tab
        val sectionsPagerAdapter = SectionPagerAdapterFavorit(this, supportFragmentManager)
        sectionsPagerAdapter.username = data.username
        view_pager_favorit.adapter = sectionsPagerAdapter
        tabs_favorit.setupWithViewPager(view_pager_favorit)
        supportActionBar?.elevation = 0f
    }

    fun deleteFavorit(username:String){
        followerHelper.deleteByFollowerUsername(username)
        followingHelper.deleteByFollowingUsername(username)
        val result = userHelper.deleteByUsername(username).toLong()
        if(result > 0){
            changeFAB(false)
            val intent = Intent(this, FavoritActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Berhasil Menghapus dari Favorit", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Gagal Menghapus dari Favorit", Toast.LENGTH_SHORT).show()
        }

    }

    fun checkDataUser(username: String){
        val _this = this
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = userHelper.queryByUsername(username)
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
            detail_like_fab_favorit.setImageResource(R.drawable.like)
        }else{
            isInDatabase = false
            detail_like_fab_favorit.setImageResource(R.drawable.unlike)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userHelper.close()
        followerHelper.close()
        followingHelper.close()
    }
}