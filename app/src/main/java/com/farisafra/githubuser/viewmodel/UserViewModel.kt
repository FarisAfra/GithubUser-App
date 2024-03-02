package com.farisafra.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farisafra.githubuser.data.response.User
import com.farisafra.githubuser.data.response.UserGithubResponse
import com.farisafra.githubuser.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Query
import javax.security.auth.callback.Callback

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
                        totalcount.postValue(response.body()?.total_count)
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

    fun getTotalCount(): LiveData<Int>{
        return totalcount
    }
}