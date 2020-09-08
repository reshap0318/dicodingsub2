package com.example.dicodsub2.Activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodsub2.Adapter.userFavoritAdapter
import com.example.dicodsub2.Helper.MappingHelper
import com.example.dicodsub2.R
import com.example.dicodsub2.db.FollowerHelper
import com.example.dicodsub2.db.FollowingHelper
import kotlinx.android.synthetic.main.fragment_favorit_following.*
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class favoritFollowingFragment : Fragment() {

    private lateinit var userAdapter: userFavoritAdapter
    private lateinit var followingHelper: FollowingHelper

    companion object {
        var EXTRA_USERAME = "USERNAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_favorit_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val username = arguments?.getString(FollowerFragment.EXTRA_USERAME)

            followingHelper = activity?.let { FollowingHelper.getInstance(it) }!!
            followingHelper.open()

            val layoutManager = LinearLayoutManager(activity)
            userAdapter = userFavoritAdapter{user ->
                Toast.makeText(activity, "Maaf Lagi Malas Lanjutin Kodingan :)", Toast.LENGTH_SHORT).show()
            }
            userAdapter.notifyDataSetChanged()
            rv_list_user_following_favorit.adapter = userAdapter
            rv_list_user_following_favorit.layoutManager = layoutManager

            showLoading(true)
            GlobalScope.launch(Dispatchers.Main) {
                val deferredNotes = async(Dispatchers.IO) {
                    val cursor = username?.let { followingHelper.queryByFollowingUsername(it) }
                    MappingHelper.mapCursorToArrayList(cursor)
                }
                val followers = deferredNotes.await()
                userAdapter.setData(followers)
            }
            showLoading(false)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarFollowingFavorit.visibility = View.VISIBLE
        } else {
            progressBarFollowingFavorit.visibility = View.GONE
        }
    }


}