package com.pasanlive.githubprofile.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.pasanlive.githubprofile.GithubProfileApplication
import com.pasanlive.githubprofile.R
import com.pasanlive.githubprofile.home.adapters.GitRepositoryListAdapter
import com.pasanlive.githubprofile.home.model.GithubData
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeContract.HomeView {
    @Inject
    lateinit var presenter: HomePresenter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)

        (application as GithubProfileApplication).appComponent.inject(this)
        presenter.setView(this)
        swipeRefreshLayout.isRefreshing = true
        presenter.loadGithubData(false)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadGithubData(true)
        }

    }

    override fun displayLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun dismissLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun displayGithubData(data: GithubData) {
        swipeRefreshLayout.isRefreshing = false
        data.githubProfile.apply {
            findViewById<TextView>(R.id.profile_name).text = this.name
            findViewById<TextView>(R.id.handle).text = this.login
            findViewById<TextView>(R.id.followers_count).text =
                this.followers.toString()
            findViewById<TextView>(R.id.following_count).text =
                this.following.toString()
            findViewById<TextView>(R.id.email).text = this.email

            val avatarImageView = findViewById<ImageView>(R.id.avatar)
            Glide.with(applicationContext).load(this.avatarUrl).transform(CircleCrop())
                .into(avatarImageView);

        }

        val topRepositoriesListLinearLayoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        val pinnedRepositoriesListLinearLayoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        val starredRepositoriesListLinearLayoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)


        val topRepositoriesList = findViewById<RecyclerView>(R.id.top_repositories)
        topRepositoriesList.layoutManager = topRepositoriesListLinearLayoutManager
        topRepositoriesList.adapter = GitRepositoryListAdapter(data.topRepositories, false)

        val pinnedRepositoriesList = findViewById<RecyclerView>(R.id.pinned_repositories)
        pinnedRepositoriesList.layoutManager = pinnedRepositoriesListLinearLayoutManager
        pinnedRepositoriesList.adapter = GitRepositoryListAdapter(data.pinnedRepositories, true)

        val starredRepositories = findViewById<RecyclerView>(R.id.starred_repositories)
        starredRepositories.layoutManager = starredRepositoriesListLinearLayoutManager
        starredRepositories.adapter = GitRepositoryListAdapter(data.starredRepositories, false)


    }

    override fun displayError(error: String?) {
        swipeRefreshLayout.isRefreshing = false
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

}