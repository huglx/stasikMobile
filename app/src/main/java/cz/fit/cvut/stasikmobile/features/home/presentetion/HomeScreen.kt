package cz.fit.cvut.stasikmobile.features.home.presentetion

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {


    val uiState: HomeScreenState by viewModel.homeState.collectAsStateWithLifecycle()
    when(val state = uiState.state){
        is HomeUIState.Data -> {
            if(state.data.isNotEmpty())
                Text(text = state.data[0][0].username)
        }
        HomeUIState.Loading -> {

        }
    }


}