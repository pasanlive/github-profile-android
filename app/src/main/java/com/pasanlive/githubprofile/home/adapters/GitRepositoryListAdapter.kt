package com.pasanlive.githubprofile.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pasanlive.githubprofile.R
import com.pasanlive.githubprofile.home.model.GitRepository

class GitRepositoryListAdapter(
    private val gitRepositories: List<GitRepository>,
    private val expandedView: Boolean
) :
    RecyclerView.Adapter<GitRepositoryListAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GitRepositoryListAdapterViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(
                if (expandedView) R.layout.expanded_repository_item else R.layout.repository_item,
                parent,
                false
            )

        return GitRepositoryListAdapterViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: GitRepositoryListAdapterViewHolder, position: Int) {
        val item = gitRepositories[position]
        return holder.bindGorRepository(item)
    }

    override fun getItemCount(): Int = gitRepositories.size

    private fun getLayoutParams(): ViewGroup.LayoutParams =
        ViewGroup.LayoutParams(
            if (expandedView) ViewGroup.LayoutParams.MATCH_PARENT else 200,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
}