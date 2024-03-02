package com.farisafra.githubuser.data.retrofit

import com.farisafra.githubuser.data.response.DetailUserResponse
import com.farisafra.githubuser.data.response.User
import com.farisafra.githubuser.data.response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<UserGithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getfollowers(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<ArrayList<User>>
}