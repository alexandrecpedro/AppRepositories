package br.com.dio.app.repositories.presentation.di

import br.com.dio.app.repositories.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/** Managing Dependencies Injection (di) **/
// Using MainViewModel
object PresentationModule {

    // Concat all module data in one list which will be display on main class
    fun load() {
        loadKoinModules(viewModelModule())
    }

    // Provide all ViewModel and returns a module
    private fun viewModelModule(): Module {
        return module {
            viewModel { MainViewModel(get()) }
        }
    }
}