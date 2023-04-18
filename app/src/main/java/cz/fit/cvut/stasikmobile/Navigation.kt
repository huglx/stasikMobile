package cz.fit.cvut.stasikmobile

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cz.fit.cvut.stasikmobile.features.canteen.presentetion.CanteenScreen
import cz.fit.cvut.stasikmobile.features.home.presentetion.HomeScreen
import cz.fit.cvut.stasikmobile.features.profile.ProfileScreen



sealed class Screens(val route: String) {

    sealed class TopLevel(val route: String) {
        abstract val icon: ImageVector
        @get:StringRes
        abstract val name: Int

        object HomeRoute: TopLevel("home") {
            override val icon = Icons.Default.Home
            override val name = R.string.bottom_nav_title_home
        }

        object CanteenRoute: TopLevel("canteen") {
            override val icon = Icons.Default.Person
            override val name = R.string.bottom_nav_title_canteen
        }
        companion object {
            val all get() = listOf(HomeRoute, CanteenRoute)
        }
    }
    object ProfileRoute: Screens("profile")

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->

        NavHost(navController = navController,
                modifier = Modifier.padding(paddingValues),
                startDestination = Screens.TopLevel.HomeRoute.route
            ) {
            composable(route = Screens.TopLevel.HomeRoute.route) {
                HomeScreen(navigateToProfile = { navController.navigate(Screens.ProfileRoute.route) })
            }
            composable(route = Screens.TopLevel.CanteenRoute.route) {
                CanteenScreen()
            }
            composable(route = Screens.ProfileRoute.route) {
                ProfileScreen(navigateToHome = { navController.navigate(Screens.TopLevel.HomeRoute.route) {
                    popUpTo(Screens.ProfileRoute.route) {inclusive = true}
                } })
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination == null || currentDestination.route == Screens.ProfileRoute.route) {
        return
    }
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Screens.TopLevel.all.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.name)) },
                selected = currentDestination.hierarchy.any { it.route == screen.route },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
