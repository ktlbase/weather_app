package com.masqx.weatherapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masqx.weatherapp.core.theme.AppThemeTokens

/**
 * Base text field for the app's visual language: shadowed floating card,
 * transparent underlying border, custom placeholder rendering.
 * Not meant to be used bare on most screens — compose specific fields
 * (e.g. SearchCityTextField) on top of it with their own leading/trailing content.
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    textStyle: TextStyle = LocalTextStyle.current.copy(
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.onSurface,
    ),
    shape: Shape = RoundedCornerShape(AppThemeTokens.shapes.lg.topStart), // 16dp per AppShapes.lg
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Search,
    ),
    keyboardActions: KeyboardActions? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val resolvedKeyboardActions = keyboardActions ?: KeyboardActions(
        onSearch = { keyboardController?.hide() },
    )

    Box(
        modifier = modifier.fillMaxWidth().shadow(elevation = 3.dp, shape = shape)
            .background(color = MaterialTheme.colorScheme.surface, shape = shape)
            .border(
                width = if (isFocused) 2.dp else 0.dp,
                color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = shape,
            )
            .padding(horizontal = AppThemeTokens.spacing.sm + 6.dp, vertical = 4.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            leadingContent?.let {
                it()
                Spacer(Modifier.width(AppThemeTokens.spacing.sm + 2.dp))
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = AppThemeTokens.spacing.sm + 4.dp),
                    textStyle = textStyle,
                    singleLine = singleLine,
                    enabled = enabled,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    keyboardOptions = keyboardOptions,
                    keyboardActions = resolvedKeyboardActions,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                )
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = textStyle.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        ),
                        modifier = Modifier.padding(vertical = AppThemeTokens.spacing.sm + 4.dp),
                    )
                }
            }

            trailingContent?.let {
                Spacer(Modifier.width(AppThemeTokens.spacing.sm + 2.dp))
                it()
            }
        }
    }
}


@Preview
@Composable
private fun AppTextFieldExample() {
    return AppTextField(
        value = "",
        placeholder = "Введите значение",
        onValueChange = { e -> println(e) }
    )
}