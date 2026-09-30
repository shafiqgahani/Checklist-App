package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
  primary = BrandNavy,
  onPrimary = Color.White,
  primaryContainer = BrandNavyLight,
  onPrimaryContainer = Color.White,
  secondary = BrandTeal,
  onSecondary = Color.White,
  secondaryContainer = BrandTealSubtle,
  onSecondaryContainer = BrandNavy,
  background = BrandGrayBg,
  onBackground = Slate900,
  surface = BrandCardBg,
  onSurface = Slate900,
  surfaceVariant = BrandGrayBg,
  onSurfaceVariant = Slate700,
  outline = BrandBorder
)

private val DarkColorScheme = darkColorScheme(
  primary = BrandNavyLight,
  onPrimary = Color.White,
  secondary = BrandTeal,
  onSecondary = Color.White,
  background = BrandNavyDark,
  onBackground = Color.White,
  surface = BrandNavy,
  onSurface = Color.White,
  outline = Slate700
)

@Composable
fun ChecklistOpsTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = BrandNavy.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
      }
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
