package com.pasanlive.githubprofile.home.model

data class GithubData(
    val githubProfile: GithubProfile,
    val pinnedRepositories: List<GitRepository>,
    val topRepositories: List<GitRepository>,
    val starredRepositories: List<GitRepository>,
)
