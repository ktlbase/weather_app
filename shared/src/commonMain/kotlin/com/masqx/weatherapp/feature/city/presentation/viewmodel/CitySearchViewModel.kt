package com.masqx.weatherapp.feature.city.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masqx.weatherapp.feature.city.domain.repository.CitySearchRepository
import com.masqx.weatherapp.feature.weather.domain.CityCurrentWeather
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private val SEARCH_DEBOUNCE_MS = 400.milliseconds

data class CitySearchUiState(
    val query: String = "",
    val results: List<CityCurrentWeather> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@OptIn(FlowPreview::class)
class CitySearchViewModel(
    private val repository: CitySearchRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CitySearchUiState())
    val uiState: StateFlow<CitySearchUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        queryFlow.debounce(SEARCH_DEBOUNCE_MS).distinctUntilChanged()
            .onEach { query -> search(query) }.launchIn(viewModelScope)
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.value = query
    }

    private fun search(query: String) {
        if (query.isBlank()) {
            _uiState.update {
                it.copy(
                    results = emptyList(), isLoading = false, errorMessage = null
                )
            }
            return
        }

        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.search(query).onSuccess { results ->
                _uiState.update {
                    it.copy(
                        isLoading = false, results = results
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false, errorMessage = error.message
                    )
                }
            }
        }
    }
}
