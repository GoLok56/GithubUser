package io.github.golok56.githubuser.presentation.models

import androidx.recyclerview.widget.DiffUtil

data class GithubUser(
    val login: String,
    val avatar: String,
    val githubUrl: String
) {
    companion object {
        @JvmStatic
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser) =
                oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser) =
                oldItem == newItem
        }
    }
}