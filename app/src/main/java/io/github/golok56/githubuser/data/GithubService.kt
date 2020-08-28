package io.github.golok56.githubuser.data

import io.github.golok56.githubuser.data.repository.githubuser.GithubUserService
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GithubService {
    private const val BASE_URL = "https://api.github.com/"

    private val retrofitInstance by lazy {
        val okHttpClient = OkHttpClient().newBuilder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectionSpecs(
                listOf(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS,
                    ConnectionSpec.CLEARTEXT
                )
            )
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val githubUserService by lazy { retrofitInstance.create(GithubUserService::class.java) }
}