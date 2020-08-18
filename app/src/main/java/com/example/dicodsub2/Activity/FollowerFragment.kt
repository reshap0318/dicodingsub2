package com.example.dicodsub2.Activity


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodsub2.Adapter.userAdapter
import com.example.dicodsub2.Model.UserViewModel

import com.example.dicodsub2.R
import kotlinx.android.synthetic.main.fragment_follower.*


class FollowerFragment : Fragment() {

    companion object {
        var EXTRA_USERAME = "USERNAME"
    }

    private lateinit var userAdapter: userAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val username = arguments?.getString(EXTRA_USERAME)

            val layoutManager = LinearLayoutManager(activity)
            userAdapter = userAdapter{user ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("data",user)
                startActivity(intent)
            }
            userAdapter.notifyDataSetChanged()
            rv_list_user_follower.adapter = userAdapter
            rv_list_user_follower.layoutManager = layoutManager
            userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

            if(username != null){
                showLoading(true)
                userViewModel.setDataFollower(username)
                userViewModel.getData().observe(this, Observer { user ->
                    if (user != null) {
                        userAdapter.setData(user)
                        showLoading(false)
                    }
                })
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarFollower.visibility = View.VISIBLE
        } else {
            progressBarFollower.visibility = View.GONE
        }
    }

}
