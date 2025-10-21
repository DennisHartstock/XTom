package org.example.project

import androidx.compose.runtime.Composable
import org.example.project.ui.ChooseModeScreen
import org.example.project.ui.theme.XTomTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    XTomTheme {
        ChooseModeScreen()
    }
}
