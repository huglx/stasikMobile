package cz.fit.cvut.stasikmobile.features.profile.presentetion.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.fit.cvut.stasikmobile.R
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

    ProfileScreen(
        uiState.name,
        viewModel::onNameChanged,
        viewModel::saveNameAndLogin,
        uiState.nameIsWrong
    )
}

@Composable
private fun ProfileScreen(
    name: String,
    onNameChanged: (String) -> Unit,
    saveNameAndLogin: () -> Unit,
    nameIsWrong: Boolean,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        Text(stringResource(R.string.user_input_nick))
        Spacer(Modifier.height(4.dp))
        UserInput(name, onNameChanged)

        Button(
            onClick = saveNameAndLogin,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(id = R.string.button_text_go))
        }

        if(nameIsWrong)
            ShowError()
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
        text = stringResource(id = R.string.user_input_error),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
fun ShowSuccess() {
    Text(
        text =stringResource(id = R.string.user_input_success),
        color = Color.Green,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 16.dp)
    )
}
