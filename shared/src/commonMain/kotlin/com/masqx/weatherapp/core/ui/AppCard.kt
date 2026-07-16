package com.masqx.weatherapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.core.theme.AppThemeTokens


@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppThemeTokens.spacing.md, vertical = AppThemeTokens.spacing.sm + 6.dp
    ),
    content: @Composable () -> Unit,
) {

    val shape = AppThemeTokens.shapes.lg

    Box(
        modifier = modifier.fillMaxWidth().shadow(elevation = 2.dp, shape = shape).clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(contentPadding)
    ) {
        content()

    }

}


@Composable
@Preview
private fun AppCardPreview() {
    AppCard(
        contentPadding = PaddingValues(20.dp)
    ) {
        Text("Hello, AppCard!")

    }
}
