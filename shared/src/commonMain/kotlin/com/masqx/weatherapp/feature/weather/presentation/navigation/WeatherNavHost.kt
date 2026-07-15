package com.masqx.weatherapp.feature.weather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read
import com.masqx.weatherapp.core.service.navigation.NavigationService
import com.masqx.weatherapp.feature.city.presentation.screen.CitySearchScreen
import com.masqx.weatherapp.feature.weather.presentation.screen.CityDetailScreen
import com.masqx.weatherapp.feature.weather.presentation.screen.CityListScreen
import org.koin.compose.koinInject

/**
 * Граф навигации фичи weather: список городов <-> экран деталей города.
 * Биндит [NavigationService] на этот [NavHostController], чтобы навигацию можно было
 * вызывать из любого места (вьюмодель, репозиторий) через koinInject<Navigator>().
 */
@Composable
fun WeatherNavHost(
    navController: NavHostController = rememberNavController(),
    navigationService: NavigationService = koinInject(),
) {
    DisposableEffect(navController) {
        navigationService.bind(navController)
        onDispose { navigationService.unbind() }
    }

    NavHost(
        navController = navController,
        startDestination = WeatherRoute.CityList.route,
    ) {
        composable(WeatherRoute.CityList.route) {
            CityListScreen()
        }

        composable(WeatherRoute.CitySearch.route) {
            CitySearchScreen()
        }

        composable(WeatherRoute.CityDetail.route) { backStackEntry ->
            val cityId = backStackEntry.arguments?.read {
                getStringOrNull(WeatherRoute.CityDetail.ARG_CITY_ID)
            }
            CityDetailScreen(cityId = cityId.orEmpty())
        }
    }
}
