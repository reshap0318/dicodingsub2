package com.example.dicodsub2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodsub2.Adapter.userAdapter
import com.example.dicodsub2.Model.UserViewModel
import com.example.dicodsub2.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: userAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        userAdapter = userAdapter{user ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("data",user)
            startActivity(intent)
        }
        userAdapter.notifyDataSetChanged()
        rv_list_user_main.layoutManager = layoutManager
        rv_list_user_main.adapter = userAdapter
        rv_list_user_main.addItemDecoration(DividerItemDecoration(applicationContext, layoutManager.orientation))

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)


        showLoading(true)
        userViewModel.setDataPencarian("a")

        userViewModel.getData().observe(this, Observer { user ->
            if (user != null) {
                userAdapter.setData(user)
                showLoading(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)
        if(menuItem != null){
            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showLoading(true)
                    userViewModel.setDataPencarian(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        val search = newText.toLowerCase(Locale.getDefault())
                        showLoading(true)
                        userViewModel.setDataPencarian(search)
                    }
                    else{
                        showLoading(true)
                        userViewModel.setDataPencarian("a")
                    }
                    return false
                }
            })

        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}
