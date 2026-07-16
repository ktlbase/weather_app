package com.masqx.weatherapp.feature.city.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.masqx.weatherapp.core.service.navigation.NavigationService
import com.masqx.weatherapp.core.theme.AppTheme
import com.masqx.weatherapp.core.theme.AppThemeTokens
import com.masqx.weatherapp.feature.city.presentation.viewmodel.CitySearchUiState
import com.masqx.weatherapp.feature.city.presentation.viewmodel.CitySearchViewModel
import com.masqx.weatherapp.feature.city.presentation.widget.CityCurrentWeatherCard
import com.masqx.weatherapp.feature.city.presentation.widget.CityPreviewData
import com.masqx.weatherapp.feature.city.presentation.widget.CitySearchTextField
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

/// Поиск городов через геокодинг-API.

@Composable
fun CitySearchScreen(
    viewModel: CitySearchViewModel = koinViewModel(),
    navigationService: NavigationService = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()

    CitySearchScreenContent(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onBack = { navigationService.popBackStack() },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun CitySearchScreenContent(
    uiState: CitySearchUiState,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
) {
    val listState = rememberLazyListState()
    // Живёт на уровне экрана, а не item-composition — LazyColumn выгружает офскрин items,
    // поэтому remember внутри item lambda не пережил бы scroll-out/scroll-in. Без этого стека
    // "уже показанных" id карточка переигрывала бы entrance-анимацию при каждом повторном скролле.
    val animatedCityIds = remember { mutableStateListOf<String>() }
    val focusManager = LocalFocusManager.current

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Поиск") },
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
                .padding(top = padding.calculateTopPadding())
                // Тап вне search-поля (по списку/пустой зоне) убирает фокус — клавиатура закрывается.
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                },
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    bottom = AppThemeTokens.spacing.sm +
                        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // stickyHeader — Compose сам держит поле закреплённым сверху во время скролла,
                // без ручного measurement/z-order трюков.
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(
                                horizontal = AppThemeTokens.spacing.md,
                                vertical = AppThemeTokens.spacing.sm,
                            ),
                    ) {
                        CitySearchTextField(
                            value = uiState.query,
                            onValueChange = onQueryChange,
                            isLoading = uiState.isLoading,
                        )

                        AnimatedVisibility(
                            visible = uiState.errorMessage != null,
                            enter = fadeIn(tween(200)),
                            exit = fadeOut(tween(150)),
                        ) {
                            Text(
                                uiState.errorMessage.orEmpty(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = AppThemeTokens.spacing.sm),
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }

                if (uiState.results.isEmpty()) {
                    item {
                        val showEmptyState = !uiState.isLoading && uiState.errorMessage == null
                        AnimatedVisibility(
                            visible = showEmptyState,
                            enter = fadeIn(tween(200)),
                            exit = fadeOut(tween(150)),
                        ) {
                            CitySearchEmptyState(hasQuery = uiState.query.isNotBlank())
                        }
                    }
                }

                itemsIndexed(
                    uiState.results,
                    key = { _, result -> result.city.id.value },
                ) { index, result ->
                    val cityId = result.city.id.value
                    StaggeredEntrance(
                        index = index,
                        skipAnimation = cityId in animatedCityIds,
                        onAnimationStarted = {
                            if (cityId !in animatedCityIds) animatedCityIds.add(cityId)
                        },
                    ) {
                        CityCurrentWeatherCard(
                            cityWeather = result,
                            modifier = Modifier
                                .padding(horizontal = AppThemeTokens.spacing.md)
                                .animateItem(
                                    fadeInSpec = tween(250),
                                    fadeOutSpec = tween(150),
                                    placementSpec = tween(250),
                                ),
                        )
                    }
                }
            }
        }
    }
}

/**
 * Плавное появление "лесенкой": элементы стартуют с задержкой, растущей по [index], и едут
 * сверху вниз с fade — визуально выстраиваются по очереди, а не всплывают все разом.
 * Стартует только на первой композиции конкретного экземпляра (LazyColumn ключ), поэтому
 * карточка, которая просто переехала на новую позицию в списке, повторно не переигрывает вход.
 */
@Composable
private fun StaggeredEntrance(
    index: Int,
    skipAnimation: Boolean,
    onAnimationStarted: () -> Unit,
    content: @Composable () -> Unit,
) {
    val alpha = remember { Animatable(if (skipAnimation) 1f else 0f) }
    val offsetY = remember { Animatable(if (skipAnimation) 0f else 24f) }

    LaunchedEffect(Unit) {
        if (skipAnimation) return@LaunchedEffect
        onAnimationStarted()
        delay((index * 60L).coerceAtMost(360L))
        launch {
            alpha.animateTo(1f, tween(220))
        }
        offsetY.animateTo(0f, tween(280))
    }

    Box(
        modifier = Modifier.graphicsLayer {
            this.alpha = alpha.value
            translationY = offsetY.value
        },
    ) {
        content()
    }
}

@Composable
private fun CitySearchEmptyState(hasQuery: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(AppThemeTokens.spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = if (hasQuery) Icons.Filled.Search else Icons.Filled.LocationOn,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.height(AppThemeTokens.spacing.md))
        Text(
            text = if (hasQuery) "Ничего не найдено" else "Начните искать",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(AppThemeTokens.spacing.xs))
        Text(
            text = if (hasQuery) {
                "Попробуйте изменить запрос или проверьте написание"
            } else {
                "Введите название города, чтобы увидеть погоду"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun CitySearchScreenPreview() {
    AppTheme {
        CitySearchScreenContent(
            uiState = CitySearchUiState(
                query = "Mos",
                results = listOf(CityPreviewData.defaultCityWeather),
            ),
            onQueryChange = {},
            onBack = {},
        )
    }
}

@Preview
@Composable
private fun CitySearchScreenEmptyPreview() {
    AppTheme {
        CitySearchScreenContent(
            uiState = CitySearchUiState(),
            onQueryChange = {},
            onBack = {},
        )
    }
}

@Preview
@Composable
private fun CitySearchScreenLoadingPreview() {
    AppTheme {
        CitySearchScreenContent(
            uiState = CitySearchUiState(query = "Tok", isLoading = true),
            onQueryChange = {},
            onBack = {},
        )
    }
}
