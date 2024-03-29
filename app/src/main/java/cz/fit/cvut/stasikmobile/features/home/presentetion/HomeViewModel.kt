package cz.fit.cvut.stasikmobile.features.home.presentetion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.fit.cvut.stasikmobile.core.data.datastore.UserProfileSource
import cz.fit.cvut.stasikmobile.features.home.data.UserRepository
import cz.fit.cvut.stasikmobile.features.home.domain.User
import cz.fit.cvut.stasikmobile.usecases.GetLastLoginStateUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: UserRepository,
    private val userProfileSource: UserProfileSource,
    private val getLastLoginStateUseCase: GetLastLoginStateUseCase
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
            _homeState.value = HomeScreenState(HomeUIState.Loading)
            val userName = getLastLoginStateUseCase.invoke().first.first()
            val usersResponse = repository.getUsers()

            if(!usersResponse.isSuccess){
                _homeState.value = HomeScreenState(HomeUIState.Error)
                return@launch
            }

            val user = usersResponse.users[index].first {
                it.username == userName
            }
            findOverlapWithOthers(usersResponse.users[index], user)

            _homeState.value = HomeScreenState(HomeUIState.Loaded(user, getDaysOfWeek()[index], userName))
        }
    }

    private fun findOverlapWithOthers(users: List<User>, currentUser: User) {
        users.filter { it != currentUser && it.subjects != null }.forEach { user ->
            currentUser.subjects?.forEach { subject1 ->
                user.subjects?.forEach { subject2 ->
                    if (subject1.starts_at.split("T")[1].split("+")[0] == subject2.starts_at.split("T")[1].split("+")[0]
                        && subject1.links.room == subject2.links.room
                    ) {
                        if (subject1.overlapWith.isEmpty()) {
                            subject1.overlapWith += user.username
                        } else {
                            subject1.overlapWith += ", ${user.username}"
                        }
                    }
                }
            }
        }
    }

    fun getDaysOfWeek(): List<String> {
        return listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    }
}
sealed interface HomeUIState{
    data class Loaded(val data: User,
                      val topBarTitle: String,
                      val currentUserName: String
    ): HomeUIState

    object Loading: HomeUIState

    object Error: HomeUIState
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