package com.example.dicodsub2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodsub2.Model.User
import com.example.dicodsub2.Model.UserLite
import com.example.dicodsub2.R
import kotlinx.android.synthetic.main.row_user_main.view.*

class userFavoritAdapter(private val onItemClickListener : onItemClickFaforit) : RecyclerView.Adapter<userFavoritAdapter.viewHolder>(){

    var datas = ArrayList<UserLite>()

    fun setData(items: ArrayList<UserLite>) {
        datas.clear()
        datas.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: UserLite) {
        datas.add(item)
        notifyDataSetChanged()
    }

    fun clearData() {
        datas.clear()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): viewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_user_main, viewGroup, false)
        return viewHolder(mView)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {
        viewHolder.onBind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class viewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        fun onBind (user : UserLite){
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

typealias onItemClickFaforit = (UserLite) -> Unit