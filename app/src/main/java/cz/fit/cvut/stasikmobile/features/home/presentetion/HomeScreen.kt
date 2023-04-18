package cz.fit.cvut.stasikmobile.features.home.presentetion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.fit.cvut.stasikmobile.features.home.domain.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToProfile: () -> Unit
) {
    val loginState: LoggingScreenState by viewModel.loggingState.collectAsStateWithLifecycle()
    val uiState: HomeScreenState by viewModel.homeState.collectAsStateWithLifecycle()

    when(val state = loginState.state){
        LoggingUIState.LoggedIn -> {
            HomeScreenContent(uiState, viewModel::fetchData, viewModel::getDaysOfWeek)
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
private fun HomeScreenContent(
    uiState: HomeScreenState,
    fetchData: (index: Int) -> Unit,
    getDaysOfWeek: () -> List<String>
) {
    var appBarTitle by remember { mutableStateOf("Monday") }

    Scaffold(
        topBar = { CharactersListAppBar(appBarTitle) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SpinnerList(fetchData = fetchData, getDaysOfWeek())
            when(val state = uiState.state){
                is HomeUIState.Loaded -> {
                    LoadedState(
                        subjects = state.data.subjects
                    )
                    appBarTitle = state.topBarTitle
                }
                HomeUIState.Loading -> {
                    LoadingState()
                }
            }
        }
    }

}
@Composable
private fun LoadedState(subjects: List<User.Subject>?) {
    if (subjects != null) {
        LazyColumn(contentPadding = PaddingValues(all = 8.dp)) {
            items(subjects) {
                SubjectItem(subject = it)
            }
        }
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
        backgroundColor = MaterialTheme.colorScheme.surface) {
        Column {
            Text(modifier = Modifier.padding(8.dp), text = subject.links.course, fontSize = 16.sp)
            Text(modifier = Modifier.padding(8.dp), text = subject.starts_at, fontSize = 12.sp)
            Text(modifier = Modifier.padding(8.dp), text = subject.overlapWith, fontSize = 12.sp)

        }
    }
}
@Composable
private fun SpinnerList(
    fetchData: (index: Int) -> Unit,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Options"
            )
        }
        DropdownMenu(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersListAppBar(title: String) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        title = {
        Text(text = title, textAlign = TextAlign.Start, fontWeight = FontWeight.Bold)
        },
        actions =  {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Settings, contentDescription = "Settings"
                )
            }
        },

    )
}
