package theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    background = Color.Black,
    surface = Color.Gray,
    onBackground = Color.White,
)

private val LightColorPalette = lightColors(
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    primary = Color.Red,
    secondary = Color(0xfffc5f2a)
)

@Composable
fun YTDownloaderTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}