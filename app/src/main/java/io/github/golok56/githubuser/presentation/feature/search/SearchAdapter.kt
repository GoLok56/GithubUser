package io.github.golok56.githubuser.presentation.feature.search

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.golok56.githubuser.databinding.ItemGithubUserBinding
import io.github.golok56.githubuser.presentation.models.GithubUser


class SearchAdapter :
    PagedListAdapter<GithubUser, SearchAdapter.ViewHolder>(GithubUser.DIFF_CALLBACK) {
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGithubUserBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(
        private val binding: ItemGithubUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: GithubUser?) = with(binding) {
            sdvAvatar.setImageURI(githubUser?.avatar)
            tvGithubUserLogin.text = githubUser?.login
            root.setOnClickListener {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(githubUser?.githubUrl)
                }.run { root.context.startActivity(this) }
            }
        }
    }
}