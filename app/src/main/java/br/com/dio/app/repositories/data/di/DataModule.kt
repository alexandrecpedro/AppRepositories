package br.com.dio.app.repositories.data.di

import android.util.Log
import br.com.dio.app.repositories.data.repositories.RepoRepository
import br.com.dio.app.repositories.data.repositories.RepoRepositoryImpl
import br.com.dio.app.repositories.data.services.GitHubService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Managing Dependencies Injection (di) **/
object DataModule {

    // For verification
    private const val OK_HTTP = "OkHttp"

    // Concat all module data in one list which will be display on main class
    fun load() {
        loadKoinModules(networkModules() + repositoriesModule())
    }

    /** Using Retrofit **/
    // Providing all NetworkModules and return a module
    private fun networkModules(): Module {
        return module {
            // Same instance = similar to singleton pattern
            single {
                // Interceptor = knows what's being sent, received
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it)
                }
                // Display at screen what's being sent, received
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                // Encoding to and decoding from JSON
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                // Providing service (classType = GitHubService)
                createService<GitHubService>(get(), get())
            }
        }
    }

    // Providing all RepositoriesModules and return a module
    private fun repositoriesModule(): Module {
        return module {
            single<RepoRepository> { RepoRepositoryImpl(get()) }
        }
    }

    // Implementing GitHub API in Kotlin
    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory) : T {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }
}