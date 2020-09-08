package com.example.dicodsub2.Activity

import android.content.Intent
import android.net.Uri
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
import com.example.dicodsub2.db.DatabaseContract
import com.example.dicodsub2.db.FollowerHelper
import kotlinx.android.synthetic.main.fragment_favorit_follower.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoritFollowerFragment : Fragment() {

    private lateinit var userAdapter: userFavoritAdapter
    private lateinit var uriFollowerWithUsername: Uri

    companion object {
        var EXTRA_USERAME = "USERNAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_favorit_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val username = arguments?.getString(FollowerFragment.EXTRA_USERAME)
            uriFollowerWithUsername = Uri.parse(DatabaseContract.UserColumn.CONTENT_URI_FOLLOWER.toString() + "/" + username)

            val layoutManager = LinearLayoutManager(activity)
            userAdapter = userFavoritAdapter{user ->
                Toast.makeText(activity, "Maaf Lagi Malas Lanjutin Kodingan :)", Toast.LENGTH_SHORT).show()
            }
            userAdapter.notifyDataSetChanged()
            rv_list_user_follower_favorit.adapter = userAdapter
            rv_list_user_follower_favorit.layoutManager = layoutManager

            showLoading(true)
            GlobalScope.launch(Dispatchers.Main) {
                val deferredNotes = async(Dispatchers.IO) {
                    val cursor = activity?.contentResolver?.query(uriFollowerWithUsername, null, null, null, null)
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
            progressBarFollowerFavorit.visibility = View.VISIBLE
        } else {
            progressBarFollowerFavorit.visibility = View.GONE
        }
    }
}