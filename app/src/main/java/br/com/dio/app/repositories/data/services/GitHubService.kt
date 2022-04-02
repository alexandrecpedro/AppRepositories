package br.com.dio.app.repositories.data.services

import br.com.dio.app.repositories.data.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

/** Retrofit turns your HTTP API into a Java interface. **/
interface GitHubService {
    @GET("users/{user}/repos")
    // using coroutines (suspend function)
    suspend fun listRepositories(@Path("user") user: String): List<Repo>
}