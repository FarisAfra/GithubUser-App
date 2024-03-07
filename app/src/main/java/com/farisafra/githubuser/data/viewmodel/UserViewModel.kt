package com.farisafra.githubuser.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farisafra.githubuser.data.response.User
import com.farisafra.githubuser.data.response.UserGithubResponse
import com.farisafra.githubuser.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Response

class UserViewModel: ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()
    val totalcount = MutableLiveData<Int>()

    fun setSearchUser(query: String){
        ApiClient.apiInstance
            .searchUsers(query)
            .enqueue(object : retrofit2.Callback<UserGithubResponse>{
                override fun onResponse(
                    call: Call<UserGithubResponse>,
                    response: Response<UserGithubResponse>
                ) {
                    if ( response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                        totalcount.postValue(response.body()?.totalCount)
                    }
                }

                override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                    Log.e("MainActivity", "onFailure: ${t.message}")
                }
            })
    }

    fun getSearchUser(): LiveData<ArrayList<User>>{
        return listUsers
    }

}