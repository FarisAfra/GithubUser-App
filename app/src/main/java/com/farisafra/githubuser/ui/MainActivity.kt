package com.farisafra.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farisafra.githubuser.data.response.User
import com.farisafra.githubuser.data.response.UserGithubResponse
import com.farisafra.githubuser.data.retrofit.ApiClient
import com.farisafra.githubuser.databinding.ActivityMainBinding
import com.farisafra.githubuser.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private var USER_ID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java)

        binding.apply {
            rvSearchuser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvSearchuser.setHasFixedSize(true)
            rvSearchuser.adapter = adapter


            btnSearch.setOnClickListener {
                searchUser()
            }

            searchUser.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){

                    searchUser()

                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            viewModel.getSearchUser().observe(this@MainActivity,{
                if (it != null){
                    adapter.setList(it)
                    showLoading(false)

                }
            })

        }

        searchUser(isDefaultSearch = true)
    }

    private fun searchUser(){
        binding.apply {
            val query = searchUser.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query)

        }

        binding.searchUser.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                USER_ID = binding.searchUser.text.toString().trim()
                searchUser()
                true
            } else {
                false
            }
        }
    }


    private fun searchUser(isDefaultSearch: Boolean = false) {

        showLoading(true)
        totalUser()

    }

    private fun totalUser (){
        // Call API to search users
        val username = binding.searchUser.text.toString().takeIf { it.isNotBlank() } ?: "faris"
        val client = ApiClient.apiInstance.searchUsers(username)
        client.enqueue(object : Callback<UserGithubResponse> {
            override fun onResponse(
                call: Call<UserGithubResponse>,
                response: Response<UserGithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val totalCount = responseBody.total_count
                        showUserFoundCount(totalCount)

                        adapter.setList(responseBody.items)
                    }
                } else {
                    Log.e("TAG", "onFailure: ${response.message()}")
                    // Show error message to user
                    showToast("Failed to search users. Please try again later.")
                }
            }

            override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e("TAG", "onFailure: ${t.message}")
                // Show error message to user
                showToast("Failed to search users. Please check your internet connection.")
            }
        })
    }

    private fun showUserFoundCount(count: Int) {
        binding.userfound.text = "$count User Found"
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {

    }

}