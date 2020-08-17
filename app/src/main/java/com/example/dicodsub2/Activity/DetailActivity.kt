package com.example.dicodsub2.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.dicodsub2.Model.User
import com.example.dicodsub2.R
import com.example.dicodsub2.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.row_user_main.view.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<User>("data")

        tv_name_detail_user.text = if(data.name!=null){ data.name }else{ "Name Not Set User" }
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
}
