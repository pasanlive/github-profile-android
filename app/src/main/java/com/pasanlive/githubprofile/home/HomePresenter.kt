package com.pasanlive.githubprofile.home

import android.content.Context
import com.pasanlive.githubprofile.R
import com.pasanlive.githubprofile.common.GithubProfileAppException
import com.pasanlive.githubprofile.home.repository.GithubDataRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val repository: GithubDataRepository,
    private val applicationContext: Context
) : HomeContract.Presenter {
    private lateinit var homeView: HomeContract.HomeView


    override fun setView(homeView: HomeContract.HomeView) {
        this.homeView = homeView
    }


    override fun loadGithubData(refresh: Boolean) {
        homeView.dismissLoading()
        MainScope().launch {
            try {
                val data = repository.getGithubData(refresh)
                homeView.dismissLoading()
                homeView.displayGithubData(data)
            } catch (ex: GithubProfileAppException) {
                homeView.dismissLoading()
                homeView.displayError(applicationContext.getString(R.string.data_load_failed_error))
            }


        }
    }

}