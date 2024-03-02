package com.farisafra.githubuser.data.response

data class DetailUserResponse(
    val login : String,
    val id : Int,
    val avatar_url : String,
    val html_url : String,
    val name : String,
    val bio : String,
    val followers_url : String,
    val following_url : String,
    val followers : Int,
    val following : Int
)
