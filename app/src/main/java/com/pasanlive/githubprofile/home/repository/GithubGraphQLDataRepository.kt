package com.pasanlive.githubprofile.home.repository

import LoadDataQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.pasanlive.githubprofile.common.GithubProfileAppException
import com.pasanlive.githubprofile.home.model.GitRepository
import com.pasanlive.githubprofile.home.model.GithubData
import com.pasanlive.githubprofile.home.model.GithubProfile
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class GithubGraphQLDataRepository constructor(
    private val apolloClient: ApolloClient
) : GithubDataRepository {
    override suspend fun getGithubData(refresh: Boolean): GithubData {
        val res = CompletableDeferred<GithubData>()

        // Clear apolloClient cache when force refresh requested
        if (refresh) {
            apolloClient.clearNormalizedCache()
        }

        MainScope().launch {
            val response = try {
                apolloClient.query(LoadDataQuery()).await()
            } catch (e: ApolloException) {
                // handle protocol errors
                res.completeExceptionally(GithubProfileAppException())
                return@launch
            }

            val launch = response.data?.viewer
            if (launch == null || response.hasErrors()) {
                // handle application errors
                res.completeExceptionally(GithubProfileAppException())
                return@launch
            }

            res.complete(mapData(launch))

        }
        return res.await()
    }

    /*
    * Map GraphQL github data to GithubData model
    * */
    private fun mapData(data: LoadDataQuery.Viewer): GithubData = GithubData(
        githubProfile = mapProfileData(data),
        pinnedRepositories = mapPinnedRepositories(data.pinnedItems),
        topRepositories = mapTopRepositories(data.topRepositories),
        starredRepositories = mapStarredRepositories(data.starredRepositories)
    )

    private fun mapProfileData(data: LoadDataQuery.Viewer): GithubProfile =
        GithubProfile(
            name = data.name!!,
            email = data.email,
            login = data.login,
            avatarUrl = data.avatarUrl.toString(),
            followers = data.followers.totalCount,
            following = data.following.totalCount
        )

    private fun mapPinnedRepositories(pinnedItems: LoadDataQuery.PinnedItems): List<GitRepository> =
        List(pinnedItems.edges?.size!!, init = { index ->
            pinnedItems.edges[index]?.node?.asRepository!!.let {
                GitRepository(
                    name = it.name,
                    login = it.owner.login,
                    avatarUrl = it.owner.avatarUrl.toString(),
                    description = it.description ?: "",
                    starCount = it.stargazerCount,
                    language = if (it.languages?.nodes != null && it.languages.nodes.isNotEmpty()) it.languages.nodes[0]?.name.toString() else ""
                )
            }
        })


    private fun mapTopRepositories(topRepositories: LoadDataQuery.TopRepositories): List<GitRepository> =
        List(topRepositories.edges?.size!!, init = { index ->
            topRepositories.edges[index]?.node!!.let {
                GitRepository(
                    name = it.name,
                    login = it.owner.login,
                    avatarUrl = it.owner.avatarUrl.toString(),
                    description = it.description ?: "",
                    starCount = it.stargazerCount,
                    language = if (it.languages?.nodes != null && it.languages.nodes.isNotEmpty()) it.languages.nodes[0]?.name.toString() else ""
                )
            }
        })

    private fun mapStarredRepositories(starredRepositories: LoadDataQuery.StarredRepositories): List<GitRepository> =
        List(starredRepositories.edges?.size!!, init = { index ->
            starredRepositories.edges[index]?.node!!.let {
                GitRepository(
                    name = it.name,
                    login = it.owner.login,
                    avatarUrl = it.owner.avatarUrl.toString(),
                    description = it.description ?: "",
                    starCount = it.stargazerCount,
                    language = if (it.languages?.nodes != null && it.languages.nodes.isNotEmpty()) it.languages.nodes[0]?.name.toString() else ""
                )
            }
        })
}