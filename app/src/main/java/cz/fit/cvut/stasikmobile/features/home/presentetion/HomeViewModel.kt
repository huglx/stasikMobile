package cz.fit.cvut.stasikmobile.features.home.presentetion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.fit.cvut.stasikmobile.core.data.datastore.UserProfileSource
import cz.fit.cvut.stasikmobile.features.home.data.UserRepository
import cz.fit.cvut.stasikmobile.features.home.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: UserRepository,
    private val userProfileSource: UserProfileSource
): ViewModel() {
    private val _loggingState = MutableStateFlow(LoggingScreenState(LoggingUIState.LoggedIn))
    val loggingState get() = _loggingState.asStateFlow()

    private val _homeState = MutableStateFlow(HomeScreenState(HomeUIState.Loading))
    val homeState get() = _homeState.asStateFlow()

    init {
        viewModelScope.launch{
            _loggingState.update {
                it.copy(
                    state = if(userProfileSource.getLogged().first()) {
                        fetchData(0)
                        LoggingUIState.LoggedIn
                    }else {
                        LoggingUIState.NotLoggedIn
                    }

                )
            }
        }
    }

    fun fetchData(index: Int) {
        viewModelScope.launch {
            val userName = userProfileSource.getName().first()
            val result = repository.getUsers().users[index].first {
                it.username == userName
            }
            _homeState.update {
                it.copy(
                    state = HomeUIState.Data(result)
                )
            }
        }
    }
}
sealed interface HomeUIState{
    data class Data(val data: User): HomeUIState
    object Loading: HomeUIState
}

data class HomeScreenState(
    val state: HomeUIState
)

sealed interface LoggingUIState{
    object LoggedIn: LoggingUIState

    object NotLoggedIn: LoggingUIState
}

data class LoggingScreenState(
    val state: LoggingUIState
)