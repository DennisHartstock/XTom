package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.project.ui.ChooseModeScreen
import org.example.project.ui.ClassicScreen
import org.example.project.ui.RnDScreen
import org.example.project.ui.WizardScreen
import org.example.project.ui.theme.XTomTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class Screen {
    data object ChooseMode : Screen()
    data object Wizard : Screen()
    data object Classic : Screen()
    data object RnD : Screen()
}

@Composable
@Preview
fun App() {
    var themeMode by remember { mutableStateOf(ThemeMode.System) }

    XTomTheme(themeMode) {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.ChooseMode) }

        LaunchedEffect(Unit) {
            val client = GrpcClient()
            val isServerAlive = client.checkHeartbeat()
            println(">>>> Server is alive: $isServerAlive")
            client.shutdown()
        }

        when (val screen = currentScreen) {
            is Screen.ChooseMode -> {
                ChooseModeScreen(
                    onModeSelected = { mode ->
                        currentScreen = when (mode) {
                            "Wizard" -> Screen.Wizard
                            "Classic" -> Screen.Classic
                            "R & D" -> Screen.RnD
                            else -> screen
                        }
                    },
                    currentTheme = themeMode,
                    onThemeChange = { themeMode = it }
                )
            }

            is Screen.Wizard -> {
                WizardScreen(onBack = { currentScreen = Screen.ChooseMode })
            }

            is Screen.Classic -> {
                ClassicScreen(onBack = { currentScreen = Screen.ChooseMode })
            }

            is Screen.RnD -> {
                RnDScreen(onBack = { currentScreen = Screen.ChooseMode })
            }
        }
    }
}
