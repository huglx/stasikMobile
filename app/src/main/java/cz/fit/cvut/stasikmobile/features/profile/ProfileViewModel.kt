package cz.fit.cvut.stasikmobile.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.fit.cvut.stasikmobile.core.data.datastore.UserProfileSource
import cz.fit.cvut.stasikmobile.features.profile.data.LoginRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val userProfileSource: UserProfileSource,
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _screenStateStream = MutableStateFlow(ProfileScreenState())
    val screenState get() = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            _screenStateStream.update { state ->
                state.copy(
                    name = userProfileSource.getName().first(),
                    nameWasCompleted = userProfileSource.getLogged().first()
                )
            }
        }
    }

    fun onNameChanged(name: String) {
        _screenStateStream.update { state ->
            state.copy(
                name = name,
            )
        }
    }

    fun saveName(): Boolean {
        var isSuccess = false
        val userName = screenState.value.name
        viewModelScope.launch {
            if(login(userName)) {
                userProfileSource.setName(screenState.value.name)
                userProfileSource.setLogged(true)
                _screenStateStream.update { state ->
                    state.copy(
                        name = userProfileSource.getName().first(),
                        nameWasCompleted = userProfileSource.getLogged().first(),
                        nameIsWrong = false
                    )
                }
                isSuccess = true
            }else {
                _screenStateStream.update { state ->
                    state.copy(
                        nameWasCompleted = false,
                        nameIsWrong = true,
                    )
                }
                isSuccess = false
            }
        }
        return isSuccess
    }

    private suspend fun login(userName: String): Boolean {
        val result = loginRepository.getUsersList()
        return result.users.contains(userName.trim())
    }
}

data class ProfileScreenState(
    val name: String = "",
    val nameWasCompleted: Boolean = false,
    val nameIsWrong: Boolean = false,
)