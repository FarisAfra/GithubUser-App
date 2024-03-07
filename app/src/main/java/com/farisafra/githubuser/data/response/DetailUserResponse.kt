package com.farisafra.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val name: String,
    val bio: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    val followers: Int,
    val following: Int
)
