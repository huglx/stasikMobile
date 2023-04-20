package cz.fit.cvut.stasikmobile.features.profile.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: LoginViewModel = koinViewModel(),
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
        UserInput(uiState.name, viewModel::onNameChanged)

        Button(
            onClick = { viewModel.saveNameAndLogin() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("ВПЕРЕД")
        }

        if(uiState.nameIsWrong) {
            ShowError()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInput(
    input: String,
    onNameChanged: (String) -> Unit
) {
    TextField(
        value = input,
        onValueChange = {
                value -> onNameChanged(value)
        },
        modifier = Modifier.fillMaxWidth(),
    )
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

@Composable
fun ShowSuccess() {
    Text(
        text = "Такой ник сущесвтует в дб молодец ",
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 16.dp)
    )
}
