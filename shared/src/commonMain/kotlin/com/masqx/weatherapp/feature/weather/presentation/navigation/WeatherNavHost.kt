package com.masqx.weatherapp.feature.weather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.masqx.weatherapp.core.navigation.Navigator
import com.masqx.weatherapp.feature.weather.presentation.screen.CityDetailScreen
import com.masqx.weatherapp.feature.weather.presentation.screen.CityListScreen
import org.koin.compose.koinInject

/**
 * Граф навигации фичи weather: список городов <-> экран деталей города.
 * Биндит [Navigator] на этот [NavHostController], чтобы навигацию можно было
 * вызывать из любого места (вьюмодель, репозиторий) через koinInject<Navigator>().
 */
@Composable
fun WeatherNavHost(
    navController: NavHostController = rememberNavController(),
    navigator: Navigator = koinInject(),
) {
    DisposableEffect(navController) {
        navigator.bind(navController)
        onDispose { navigator.unbind() }
    }

    NavHost(
        navController = navController,
        startDestination = WeatherRoute.CityList.route,
    ) {
        composable(WeatherRoute.CityList.route) {
            CityListScreen()
        }

        composable(WeatherRoute.CityDetail.route) { backStackEntry ->
            val cityId = backStackEntry.arguments?.read {
                getStringOrNull(WeatherRoute.CityDetail.ARG_CITY_ID)
            }
            CityDetailScreen(cityId = cityId.orEmpty())
        }
    }
}
