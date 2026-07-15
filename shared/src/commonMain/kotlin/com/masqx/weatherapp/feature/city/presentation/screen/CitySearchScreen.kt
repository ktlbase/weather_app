package com.masqx.weatherapp.feature.city.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.city.presentation.viewmodel.CitySearchViewModel
import org.koin.compose.viewmodel.koinViewModel

/// Поиск городов через геокодинг-API.

@Composable
fun CitySearchScreen(
    viewModel: CitySearchViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    OutlinedTextField(
                        value = uiState.query,
                        onValueChange = viewModel::onQueryChange,
                        label = { Text("Поиск города") },
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        singleLine = true,
                    )
                }

                if (uiState.errorMessage != null) {
                    item {
                        Text(
                            uiState.errorMessage.orEmpty(),
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }

                if (uiState.isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                items(uiState.results, key = { it.id.value }) { city ->
                    ListItem(
                        headlineContent = { Text(city.name) },
                        supportingContent = {
                            Text(
                                listOfNotNull(
                                    city.region,
                                    city.country
                                ).joinToString(", ")
                            )
                        },
                    )
                }
            }
        }
    }
}
