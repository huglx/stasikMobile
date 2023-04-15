package cz.fit.cvut.stasikmobile.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.fit.cvut.stasikmobile.core.data.datastore.UserProfileSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val userProfileSource: UserProfileSource
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

    fun saveName() {
        viewModelScope.launch {
            userProfileSource.setName(screenState.value.name)
            userProfileSource.setLogged(true)
        }
    }
}

data class ProfileScreenState(
    val name: String = "",
    val nameWasCompleted: Boolean = true
)