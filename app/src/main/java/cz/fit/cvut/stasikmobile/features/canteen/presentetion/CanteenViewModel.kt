package cz.fit.cvut.stasikmobile.features.canteen.presentetion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.fit.cvut.stasikmobile.features.canteen.data.CanteenRepository
import cz.fit.cvut.stasikmobile.features.canteen.domain.CanteenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CanteenViewModel(private val repository: CanteenRepository): ViewModel() {
    private val _canteenState = MutableStateFlow(CanteenScreenState(CanteenUIState.Loading))
    val canteenState get() = _canteenState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = repository.getCanteen(2)
            _canteenState.value = CanteenScreenState(CanteenUIState.Loaded(result))
        }
    }
}

sealed interface CanteenUIState{
    data class Loaded(val listCanteen: CanteenResponse): CanteenUIState
    object Loading: CanteenUIState
}

data class CanteenScreenState(
    val state: CanteenUIState
)