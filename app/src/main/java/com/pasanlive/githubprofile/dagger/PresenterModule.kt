package com.pasanlive.githubprofile.dagger

import android.content.Context
import com.pasanlive.githubprofile.home.HomePresenter
import com.pasanlive.githubprofile.home.repository.GithubDataRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PresenterModule {
    @Provides
    fun provideHomePresenter(repository: GithubDataRepository, appContext: Context): HomePresenter =
        HomePresenter(repository, appContext)
}