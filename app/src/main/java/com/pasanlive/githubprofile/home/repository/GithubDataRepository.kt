package com.pasanlive.githubprofile.home.repository

import com.pasanlive.githubprofile.home.model.GithubData

interface GithubDataRepository {
    suspend fun getGithubData(refresh: Boolean): GithubData
}