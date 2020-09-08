package com.example.appcustomer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private var isInDatabase = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<UserLite>("data")

        data?.let { initData(it) }

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

}