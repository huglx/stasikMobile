package cz.fit.cvut.stasikmobile.features.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    Column(
        Modifier.fillMaxWidth().padding(10.dp)
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
            onClick = {
                viewModel.saveName()
                navigateToHome() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("ВПЕРЕД")
        }
    }
}