package io.github.golok56.githubuser.data.dto

import com.google.gson.annotations.SerializedName

data class GithubUserDto(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("html_url") val githubUrl: String
) {
    data class SearchResult(
        @SerializedName("items") val items: List<GithubUserDto>
    )
}