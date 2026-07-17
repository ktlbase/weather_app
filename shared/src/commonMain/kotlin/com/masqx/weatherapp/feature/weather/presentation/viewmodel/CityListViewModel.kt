package com.masqx.weatherapp.feature.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masqx.weatherapp.core.service.navigation.NavigationService
import com.masqx.weatherapp.feature.weather.domain.CityWeatherSummary
import com.masqx.weatherapp.feature.weather.domain.defaultCities
import com.masqx.weatherapp.feature.weather.domain.repository.WeatherRepository
import com.masqx.weatherapp.feature.weather.presentation.navigation.WeatherRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CityListUiState(
    val cities: List<CityWeatherSummary> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class CityListViewModel(
    private val repository: WeatherRepository,
    private val navigationService: NavigationService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CityListUiState())
    val uiState: StateFlow<CityListUiState> = _uiState.asStateFlow()

    init {
        repository.observeCitiesShort()
            .onEach { cities -> _uiState.update { it.copy(cities = cities) } }
            .launchIn(viewModelScope)

        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.refreshCitiesShort(defaultCities)
                .onFailure { error -> _uiState.update { it.copy(errorMessage = error.message) } }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onCityClick(cityId: String) {
        val city = _uiState.value.cities.firstOrNull { it.city.id.value == cityId }?.city ?: return
        navigationService.navigate(WeatherRoute.CityDetail.createRoute(city))
    }
}
