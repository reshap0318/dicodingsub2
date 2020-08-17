package com.example.dicodsub2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodsub2.Model.User
import com.example.dicodsub2.R
import kotlinx.android.synthetic.main.row_user_main.view.*

class userAdapter(private val onItemClickListener : onItemClick) : RecyclerView.Adapter<userAdapter.viewHolder>(){

    var datas = mutableListOf<User>()
        set(value){
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder = viewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.row_user_main, parent, false))

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.onBind(datas[position])
    }

    inner class viewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        fun onBind (user : User){
            itemView.tv_name_row_main.text = user.name
            itemView.tv_follower_row_main.text = user.follower.toString()
            itemView.tv_repo_row_main.text = user.repository.toString()
            if(user.avatar != null){
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .fitCenter()
                    .centerCrop()
                    .into(itemView.img_photo_row_main);
            }
            itemView.setOnClickListener{
                onItemClickListener(user)
            }

        }
    }

}

typealias onItemClick = (User) -> Unit