package cz.fit.cvut.stasikmobile.features.profile.presentetion.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.fit.cvut.stasikmobile.R
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.LoginViewModel
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.ShowError
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.ShowSuccess
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.UserInput
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.screenState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {SettingsAppBar(navigateToHome)}
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues = paddingValues)
        ){
            SettingsScreen(
                uiState.name,
                viewModel::onNameChanged,
                viewModel::saveNameAndLogin
            )

            if(uiState.nameIsWrong)
                ShowError()
            else
                ShowSuccess()
        }
    }
}

@Composable
private fun SettingsScreen(
    name: String,
    onNameChanged: (String) -> Unit,
    saveAndLogin: () -> Unit,
    ) {
    Column(
        Modifier
            .padding(10.dp)
    ) {

        Text(stringResource(R.string.user_input_nick))
        Spacer(Modifier.height(4.dp))
        UserInput(name, onNameChanged)

        Button(
            onClick = saveAndLogin,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAppBar(navigateToHome: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        title = {
            Text(text = stringResource(id = R.string.app_bar_settings), textAlign = TextAlign.Start, fontWeight = FontWeight.Bold)
        },
        navigationIcon =  {
            IconButton(onClick = navigateToHome ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                )
            }
        },

        )
}

