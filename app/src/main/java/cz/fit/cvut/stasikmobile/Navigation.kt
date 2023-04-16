package cz.fit.cvut.stasikmobile

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.fit.cvut.stasikmobile.features.home.presentetion.HomeScreen
import cz.fit.cvut.stasikmobile.features.profile.ProfileScreen



sealed class Screens(val route: String) {

    sealed class TopLevel(val route: String) {
        abstract val icon: ImageVector
        abstract val name: String

        object ProfileRoute: TopLevel("profile") {
            override val icon = Icons.Default.Person
            override val name = "Profile"
        }

        object HomeRoute: TopLevel("home") {
            override val icon = Icons.Default.Home
            override val name = "Home"
        }

        object SplashRoute: Screens("splash")
    }

    companion object {
        val all get() = listOf(TopLevel.ProfileRoute)
    }
}
@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {

        }
    ) { paddingValues ->

        NavHost(navController = navController,
                modifier = Modifier.padding(paddingValues),
                startDestination = Screens.TopLevel.HomeRoute.route
            ) {
            composable(route = Screens.TopLevel.HomeRoute.route) {
                HomeScreen(navigateToProfile = { navController.navigate(Screens.TopLevel.ProfileRoute.route) })
            }
            composable(route = Screens.TopLevel.ProfileRoute.route) {
                ProfileScreen(navigateToHome = { navController.navigate(Screens.TopLevel.HomeRoute.route) {
                    popUpTo(Screens.TopLevel.ProfileRoute.route) {inclusive = true}
                } })
            }
        }
    }
}