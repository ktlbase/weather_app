package com.masqx.weatherapp.feature.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.weather.domain.CityWeatherDetail
import com.masqx.weatherapp.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CityDetailUiState(
    val city: City,
    val detail: CityWeatherDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class CityDetailViewModel(
    city: City,
    private val repository: WeatherRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CityDetailUiState(city = city))
    val uiState: StateFlow<CityDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun retry() = load()

    private fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.getWeatherDetail(_uiState.value.city)
                .onSuccess { detail ->
                    _uiState.update { it.copy(isLoading = false, detail = detail) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }
}
