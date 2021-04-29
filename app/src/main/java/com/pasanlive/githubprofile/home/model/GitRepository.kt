package com.pasanlive.githubprofile.home.model

data class GitRepository(
    val name: String,
    val login: String,
    val avatarUrl: String,
    val description: String,
    val language: String,
    val starCount: Int,
) {

}
