package br.com.dio.app.repositories.domain.di

import br.com.dio.app.repositories.domain.ListUserRepositoriesUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/** Managing Dependencies Injection (di) **/
// Using ListUserRepositoriesUseCase
object DomainModule {

    // Concat all module data in one list which will be display on main class
    fun load() {
        loadKoinModules(useCaseModule())
    }

    // Provide all UseCases and returns a module
    private fun useCaseModule(): Module {
        return module {
            // Every time the application needs an UseCase, the system re-instantiates a UseCase
            factory { ListUserRepositoriesUseCase(get()) }
        }
    }
}