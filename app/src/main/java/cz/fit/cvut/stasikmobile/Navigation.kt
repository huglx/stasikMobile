package cz.fit.cvut.stasikmobile

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import cz.fit.cvut.stasikmobile.features.profile.presentetion.login.ProfileScreen
import cz.fit.cvut.stasikmobile.features.profile.presentetion.settings.SettingsScreen


sealed class Screens(val route: String) {

    sealed class TopLevel(val route: String) {
        abstract val icon: Int
        @get:StringRes
        abstract val name: Int

        object HomeRoute: TopLevel("home") {
            override val icon = R.drawable.baseline_home_24
            override val name = R.string.bottom_nav_title_home
        }

        object CanteenRoute: TopLevel("canteen") {
            override val icon = R.drawable.baseline_fastfood_24
            override val name = R.string.bottom_nav_title_canteen
        }
        companion object {
            val all get() = listOf(HomeRoute, CanteenRoute)
        }
    }
    object LoginRoute: Screens("login")

    object SettingsRoute: Screens("settings")

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
                HomeScreen(
                    navigateToLogin = { navController.navigate(Screens.LoginRoute.route)},
                    navigateToSettings = { navController.navigate(Screens.SettingsRoute.route)}
                )
            }
            composable(route = Screens.TopLevel.CanteenRoute.route) {
                CanteenScreen()
            }
            composable(route = Screens.LoginRoute.route) {
                ProfileScreen(navigateToHome = { navController.navigate(Screens.TopLevel.HomeRoute.route) {
                    popUpTo(Screens.LoginRoute.route) {inclusive = true}
                } })
            }
            composable(route = Screens.SettingsRoute.route) {
                SettingsScreen(navigateToHome = { navController.navigate(Screens.TopLevel.HomeRoute.route) })
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination == null || currentDestination.route == Screens.LoginRoute.route
        || currentDestination.route == Screens.SettingsRoute.route) {
        return
    }
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        Screens.TopLevel.all.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = null) },
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
