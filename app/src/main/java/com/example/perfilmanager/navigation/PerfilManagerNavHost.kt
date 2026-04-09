package com.example.perfilmanager.navigation

import com.example.perfilmanager.ui.features.criarPerfil.CriarPerfilScreen
import com.example.perfilmanager.ui.features.login.LoginScreen
import com.example.perfilmanager.ui.features.perfilList.PerfilListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object PerfilListRoute

@Serializable
data class CriarPerfilRoute(
    val id: Long? = null
)


@Composable
fun PerfilManagerNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute>{
            LoginScreen(
                onNavigateTo = {
                    navController.navigate(PerfilListRoute) {
                        popUpTo(LoginRoute) {
                            inclusive = true
                        }
                    }
                },
                onTextClick = {
                    navController.navigate(CriarPerfilRoute(null))
                }
            )
        }
        composable<PerfilListRoute>{
            PerfilListScreen(
                onNavigate = { id ->
                    navController.navigate(CriarPerfilRoute(id = id))
                }
            )
        }

        composable<CriarPerfilRoute>{ backStackEntry ->
            val route = backStackEntry.toRoute<CriarPerfilRoute>()
            CriarPerfilScreen(
                route.id
            )
        }
    }
}