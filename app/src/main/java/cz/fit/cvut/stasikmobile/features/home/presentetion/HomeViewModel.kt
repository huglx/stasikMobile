package cz.fit.cvut.stasikmobile.features.home.presentetion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.fit.cvut.stasikmobile.features.home.data.UserRepository
import cz.fit.cvut.stasikmobile.features.home.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository): ViewModel() {
    private val _homeState = MutableStateFlow(HomeScreenState(HomeUIState.Data(listOf())))
    val homeState get() = _homeState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = repository.getUsers().users
            _homeState.update { it.copy(
                state = HomeUIState.Data(result)
            ) }
        }
    }
}

sealed interface HomeUIState{
    data class Data(val data: List<List<User>>): HomeUIState
    object Loading: HomeUIState
}

data class HomeScreenState(
    val state: HomeUIState
)