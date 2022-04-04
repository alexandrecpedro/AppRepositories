package br.com.dio.app.repositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.domain.ListUserRepositoriesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val listUserRepositoriesUseCase: ListUserRepositoriesUseCase
) : ViewModel() {

    private val _repos = MutableLiveData<State>()
    val repos: LiveData<State> = _repos

    fun getRepoList(user: String) {
        viewModelScope.launch {
            // listUserRepositoriesUseCase.execute(user)
            listUserRepositoriesUseCase(user)
                // return Flow<List<Repo>>
                .onStart {
                    _repos.postValue(State.Loading)
                }
                .catch {
                    // for exception
                    _repos.postValue(State.Error(it))
                }
                .collect {
                    // when finish
                    _repos.postValue(State.Success(it))
                }
        }
    }

    /** Manipulate a View **/
    // display 3 states on screen
    sealed class State {
        // State 1 : Loader
        object Loading : State()
        // State 2 : Result/Success
        data class Success(val list: List<Repo>) : State()
        // State 3 : Failure/Error
        data class Error(val error: Throwable) : State()
    }
}