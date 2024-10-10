package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
//        val navController = rememberNavController()
//        NavHost(navController = navController, startDestination = "firstScreen") {
//            composable("topMenu") { TopMenu(navController) }
//            composable("settingsScreen") { SettingsScreen() }
//        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            TopMenu()
            SettingsScreen()
        }
    }
}

@Composable
fun TopMenu() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text("XTom")
        Text("WebView")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings"
            )
            Text("Settings")
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun SettingsScreen() {
    var selectedTheme by remember { mutableStateOf(Theme.Default) }
    var serverName by remember { mutableStateOf("https://localhost:7199") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column {
            Text("Theme", fontSize = 24.sp)
            Theme.entries.forEach { theme ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedTheme == theme,
                        onClick = { selectedTheme = theme }
                    )
                    Text(text = theme.name)
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text("XTom Server:", fontSize = 24.sp, modifier = Modifier.padding(end = 16.dp))
            TextField(
                value = serverName,
                onValueChange = { serverName = it },
                modifier = Modifier.padding(end = 32.dp)
            )
            Button(onClick = {}) {
                Text("Test")
            }
        }
        Text(
            "About this application",
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 24.sp
        )
        Text("XTom Studio - 1.0.0.0", modifier = Modifier.padding(bottom = 8.dp))
        Text("TODO: add app description", modifier = Modifier.padding(bottom = 8.dp))
    }
}

enum class Theme {
    Light, Dark, Default
}