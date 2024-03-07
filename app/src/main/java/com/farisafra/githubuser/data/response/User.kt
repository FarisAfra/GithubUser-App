package com.farisafra.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class User(

    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String
)
