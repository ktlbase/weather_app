package com.masqx.weatherapp.feature.city.presentation.widget

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.core.ui.AppTextField

@Composable
fun CitySearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = "Город, место",
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp),
            )
        },
        trailingContent = if (isLoading) {
            {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        } else {
            null
        },
    )
}

@Preview
@Composable
fun CitySearchTextFieldPreview() {
    CitySearchTextField(value = "Moscow", onValueChange = {})
}

@Preview
@Composable
fun CitySearchTextFieldLoadingPreview() {
    CitySearchTextField(value = "Mos", onValueChange = {}, isLoading = true)
}
