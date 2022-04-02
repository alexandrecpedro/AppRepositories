package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.core.UseCase
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow

/** Listing all repositories of an user **/
// private variable = Constructor from Koin
class ListUserRepositoriesUseCase(
    private val repository: RepoRepository
) : UseCase<String, List<Repo>>() {

    // param = username
    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}