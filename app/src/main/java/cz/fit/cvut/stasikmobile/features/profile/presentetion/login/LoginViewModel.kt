package cz.fit.cvut.stasikmobile.features.profile.presentetion.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.fit.cvut.stasikmobile.usecases.GetLastLoginStateUseCase
import cz.fit.cvut.stasikmobile.usecases.LoginUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel (
    private val getLastLoginState: GetLastLoginStateUseCase,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _screenStateStream = MutableStateFlow(ProfileScreenState())
    val screenState get() = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            val (name, logged) = getLastLoginState.invoke()
            _screenStateStream.update { state ->
                state.copy(
                    name = name.first(),
                    nameWasCompleted = logged.first()
                )
            }
        }
    }

    fun onNameChanged(newName: String) {
        _screenStateStream.update { state ->
            state.copy(
                name = newName.filter { !it.isWhitespace() },
            )
        }
    }

    fun saveNameAndLogin(){
        val name = _screenStateStream.value.name
        viewModelScope.launch {
            if(loginUseCase.invoke(name)) {
                _screenStateStream.update { state ->
                    state.copy(
                        name = name,
                        nameWasCompleted = true,
                        nameIsWrong = false
                    )
                }
            }else {
                _screenStateStream.update { state ->
                    state.copy(
                        nameWasCompleted = false,
                        nameIsWrong = true,
                    )
                }
            }
        }
    }
}

data class ProfileScreenState(
    val name: String = "",
    val nameWasCompleted: Boolean = false,
    val nameIsWrong: Boolean = false,
)
