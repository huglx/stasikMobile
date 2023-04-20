package cz.fit.cvut.stasikmobile.features.profile.presentetion.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.LoginViewModel
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.ShowError
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.ShowSuccess
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.UserInput
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.screenState.collectAsStateWithLifecycle()
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

        if(!uiState.nameIsWrong) {
            ShowSuccess()
        }
    }
}

