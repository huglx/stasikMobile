package cz.fit.cvut.stasikmobile.features.home.presentetion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.fit.cvut.stasikmobile.features.home.domain.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(),
               navigateToProfile: () -> Unit
) {
    val loginState: LoggingScreenState by viewModel.loggingState.collectAsStateWithLifecycle()
    val uiState: HomeScreenState by viewModel.homeState.collectAsStateWithLifecycle()

    when(val state = loginState.state){
        LoggingUIState.LoggedIn -> {
            HomeScreenContent(uiState, viewModel::fetchData)
        }
        LoggingUIState.NotLoggedIn -> {
            LaunchedEffect(state) {
                navigateToProfile()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(uiState: HomeScreenState, fetchData: (index: Int) -> Unit) {
    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SpinnerList(fetchData = fetchData)
            when(val state = uiState.state){
                is HomeUIState.Loaded -> {
                    LoadedState(
                        subjects = state.data.subjects,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
                HomeUIState.Loading -> {
                    LoadingState()
                }
            }
        }
    }

}
@Composable
private fun LoadedState(modifier: Modifier ,subjects: List<User.Subject>?) {
    Column(modifier = modifier.fillMaxSize()) {
        if (subjects != null) {
            LazyColumn(contentPadding = PaddingValues(all = 8.dp), modifier = Modifier.weight(1f)) {
                items(subjects) {
                    SubjectItem(subject = it)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SpinnerList(fetchData: (index: Int) -> Unit) {
    val options = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text("Day of week") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        fetchData(options.indexOf(selectionOption))
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


@Composable
private fun SubjectItem(subject: User.Subject) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface) {
        Column() {
            Text(modifier = Modifier.padding(8.dp), text = subject.links.course, fontSize = 16.sp)
            Text(modifier = Modifier.padding(8.dp), text = subject.starts_at, fontSize = 12.sp)
            Text(modifier = Modifier.padding(8.dp), text = subject.overlapWith, fontSize = 12.sp)

        }
    }
}
