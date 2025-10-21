package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.project.ui.ChooseModeScreen
import org.example.project.ui.WizardScreen
import org.example.project.ui.theme.XTomTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class Screen {
    data object ChooseMode : Screen()
    data object Wizard : Screen()
}

@Composable
@Preview
fun App() {
    XTomTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.ChooseMode) }

        when (currentScreen) {
            is Screen.ChooseMode -> {
                ChooseModeScreen(
                    onModeSelected = { mode ->
                        if (mode == "Wizard") {
                            currentScreen = Screen.Wizard
                        }
                    }
                )
            }

            is Screen.Wizard -> {
                WizardScreen()
            }
        }
    }
}
