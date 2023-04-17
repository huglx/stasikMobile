package cz.fit.cvut.stasikmobile.features.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.screenState.collectAsStateWithLifecycle()

    if(uiState.nameWasCompleted) {
        LaunchedEffect(Unit) {
            navigateToHome()
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        Text("Введи блин свой ник в косе потом сможеш поменять)")
        Spacer(Modifier.height(4.dp))
        TextField(
            value = uiState.name,
            onValueChange = {
                    value -> viewModel.onNameChanged(value)
            },
            modifier = Modifier.fillMaxWidth(),
        )


        Button(
            onClick = { viewModel.saveName() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("ВПЕРЕД")
        }

        if(uiState.nameIsWrong) {
            ShowError()
        }
    }
}
@Composable
fun ShowError() {
    Text(
        text = "Дурак блин введи еще раз",
        color = Color.Red,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 16.dp)
    )
}
