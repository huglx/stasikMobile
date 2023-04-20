package cz.fit.cvut.stasikmobile.features.canteen.presentetion

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import cz.fit.cvut.stasikmobile.R
import cz.fit.cvut.stasikmobile.features.canteen.domain.CanteenItem
import cz.fit.cvut.stasikmobile.features.canteen.domain.CanteenResponse
import cz.fit.cvut.stasikmobile.features.home.presentetion.LoadingState
import org.koin.androidx.compose.getViewModel

@Composable
fun CanteenScreen(
    viewModel: CanteenViewModel = getViewModel()
) {
    val uiState: CanteenScreenState by viewModel.canteenState.collectAsStateWithLifecycle()

    when(val state = uiState.state) {
        CanteenUIState.Loading -> {
            LoadingState()
        }
        is CanteenUIState.Loaded -> {
            CanteenContent(state.listCanteen)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun CanteenContent(response: CanteenResponse) {
   Scaffold(topBar = {}) { paddingValues ->
       Column(modifier = Modifier
           .fillMaxSize()
           .padding(paddingValues)) {
           OutdatedDataBanner(show = !response.isSuccess)

           LazyVerticalStaggeredGrid(
               columns = StaggeredGridCells.Fixed(2),
               contentPadding = PaddingValues(16.dp),
           ) {
               items(response.data) {
                   CanteenItem(it)
               }
           }
       }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanteenItem(item: CanteenItem) {
    Card(
        onClick = { },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = item.image ?: R.drawable.placeholder,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth()
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = item.price,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun OutdatedDataBanner(show: Boolean) {
    if (show) {
        Text(
            text = stringResource(R.string.outdated_data_message_canteen),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.errorContainer)
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}