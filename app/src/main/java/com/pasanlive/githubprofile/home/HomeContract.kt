package com.pasanlive.githubprofile.home

import com.pasanlive.githubprofile.home.model.GithubData

interface HomeContract {
    interface Presenter {
        fun setView(homeView: HomeView)
        fun loadGithubData(refresh: Boolean)
    }

    interface HomeView {
        fun displayLoading()

        fun dismissLoading()

        fun displayGithubData(data: GithubData)

        fun displayError(error: String?)
    }
}