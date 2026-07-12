package com.masqx.weatherapp.feature.weather.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.masqx.weatherapp.feature.weather.presentation.viewmodel.CityListViewModel
import org.koin.compose.viewmodel.koinViewModel

/// Список городов со сводкой на сегодня.

@Composable
fun CityListScreen(
    viewModel: CityListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { padding ->
        if (uiState.isLoading && uiState.cities.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                if (uiState.errorMessage != null) {
                    item {
                        Text(uiState.errorMessage.orEmpty())
                    }
                }
                items(uiState.cities, key = { it.city.id }) { item ->
                    ListItem(
                        headlineContent = { Text(item.city.name) },
                        supportingContent = { Text("${item.weatherDaily.temperature.celsius}°") },
                        modifier = Modifier.clickable {
                            viewModel.onCityClick(item.city.id)
                        },
                    )
                }
            }
        }
    }
}
