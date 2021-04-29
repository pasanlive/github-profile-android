package com.pasanlive.githubprofile.home.model

data class GithubProfile(
    val name: String,
    val login: String,
    val followers: Int,
    val following: Int,
    val email: String,
    val avatarUrl: String,
)
