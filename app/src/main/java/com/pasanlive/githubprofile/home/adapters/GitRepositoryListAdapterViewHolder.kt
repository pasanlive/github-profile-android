package com.pasanlive.githubprofile.home.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.pasanlive.githubprofile.R
import com.pasanlive.githubprofile.home.model.GitRepository

class GitRepositoryListAdapterViewHolder(private val repositoryView: View) :
    RecyclerView.ViewHolder(repositoryView) {

    fun bindGorRepository(gitRepository: GitRepository) {
        repositoryView.findViewById<TextView>(R.id.handle).text = gitRepository.login
        repositoryView.findViewById<TextView>(R.id.repo_name).text = gitRepository.name
        repositoryView.findViewById<TextView>(R.id.repo_description).text =
            gitRepository.description
        repositoryView.findViewById<TextView>(R.id.stars_count).text =
            gitRepository.starCount.toString()
        repositoryView.findViewById<TextView>(R.id.language).text =
            gitRepository.language


        val avatarImageView = repositoryView.findViewById<ImageView>(R.id.avatar)
        Glide.with(repositoryView.context)
            .load(gitRepository.avatarUrl)
            .transform(CircleCrop()).into(avatarImageView);
    }
}
