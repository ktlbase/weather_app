package com.masqx.weatherapp.feature.weather.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.core.service.navigation.NavigationService
import com.masqx.weatherapp.core.theme.AppTheme
import com.masqx.weatherapp.core.theme.AppThemeTokens
import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.city.domain.entity.CityId
import com.masqx.weatherapp.feature.city.domain.entity.Location
import com.masqx.weatherapp.feature.city.domain.entity.Timezone
import com.masqx.weatherapp.feature.weather.domain.CityWeatherDetail
import com.masqx.weatherapp.feature.weather.presentation.viewmodel.CityDetailUiState
import com.masqx.weatherapp.feature.weather.presentation.viewmodel.CityDetailViewModel
import com.masqx.weatherapp.feature.weather.presentation.widget.CurrentWeatherSection
import com.masqx.weatherapp.feature.weather.presentation.widget.DailyForecastCard
import com.masqx.weatherapp.feature.weather.presentation.widget.HourlyForecastCard
import com.masqx.weatherapp.feature.weather.presentation.widget.MetricTilesGrid
import com.masqx.weatherapp.feature.weather.presentation.widget.WeatherDetailPreviewData
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/// Детали погоды по конкретному городу: текущая, почасовой и недельный прогноз, метрики.

@Composable
fun CityDetailScreen(
    city: City,
    viewModel: CityDetailViewModel = koinViewModel(key = city.id.value) { parametersOf(city) },
    navigationService: NavigationService = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()

    CityDetailScreenContent(
        uiState = uiState,
        onRetry = viewModel::retry,
        onBack = { navigationService.popBackStack() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityDetailScreenContent(
    uiState: CityDetailUiState,
    onRetry: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(uiState.city.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = padding.calculateTopPadding()),
        ) {
            val detail = uiState.detail

            when {
                detail == null && uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                detail == null -> DetailErrorState(
                    message = uiState.errorMessage,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center),
                )

                else -> CityDetailContent(detail = detail)
            }
        }
    }
}

@Composable
private fun CityDetailContent(detail: CityWeatherDetail) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = AppThemeTokens.spacing.lg +
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
        ),
        verticalArrangement = Arrangement.spacedBy(AppThemeTokens.spacing.lg),
    ) {
        item {
            CurrentWeatherSection(
                current = detail.current,
                today = detail.daily.firstOrNull(),
            )
        }

        if (detail.hourly.isNotEmpty()) {
            item {
                Column {
                    SectionTitle("Почасовой прогноз")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = AppThemeTokens.spacing.md),
                        horizontalArrangement = Arrangement.spacedBy(AppThemeTokens.spacing.sm),
                    ) {
                        itemsIndexed(detail.hourly) { index, hour ->
                            HourlyForecastCard(hour = hour, isNow = index == 0)
                        }
                    }
                }
            }
        }

        if (detail.daily.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(horizontal = AppThemeTokens.spacing.md)) {
                    SectionTitle("Прогноз на 7 дней", horizontalPadding = 0.dp)
                    DailyForecastCard(daily = detail.daily)
                }
            }
        }

        item {
            MetricTilesGrid(detail = detail)
        }
    }
}

@Composable
private fun SectionTitle(
    text: String,
    horizontalPadding: Dp = AppThemeTokens.spacing.md,
) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(
            start = horizontalPadding,
            end = horizontalPadding,
            bottom = AppThemeTokens.spacing.sm,
        ),
    )
}

@Composable
private fun DetailErrorState(
    message: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(AppThemeTokens.spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Не удалось загрузить погоду",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        if (message != null) {
            Spacer(Modifier.height(AppThemeTokens.spacing.xs))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(Modifier.height(AppThemeTokens.spacing.md))
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}

private val previewCity = City(
    id = CityId("1"),
    name = "San Francisco",
    location = Location(latitude = 37.77, longitude = -122.42),
    country = "USA",
    timezone = Timezone("America/Los_Angeles"),
)

@Preview
@Composable
private fun CityDetailScreenPreview() {
    AppTheme {
        CityDetailScreenContent(
            uiState = CityDetailUiState(city = previewCity, detail = WeatherDetailPreviewData.detail),
            onRetry = {},
            onBack = {},
        )
    }
}

@Preview
@Composable
private fun CityDetailScreenLoadingPreview() {
    AppTheme {
        CityDetailScreenContent(
            uiState = CityDetailUiState(city = previewCity, isLoading = true),
            onRetry = {},
            onBack = {},
        )
    }
}

@Preview
@Composable
private fun CityDetailScreenErrorPreview() {
    AppTheme {
        CityDetailScreenContent(
            uiState = CityDetailUiState(city = previewCity, errorMessage = "Нет сети"),
            onRetry = {},
            onBack = {},
        )
    }
}
