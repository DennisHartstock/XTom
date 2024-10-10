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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopMenu()
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
fun SettingsView() {
    var setting1 by remember { mutableStateOf(false) }
    var selectedTheme by remember { mutableStateOf(Theme.Default) }
    var setting2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Text("Theme")
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
        TextField(
            value = setting2,
            onValueChange = { setting2 = it },
            label = { Text("Setting 2") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("Setting 3", modifier = Modifier.padding(bottom = 8.dp))
    }
}

enum class Theme {
    Light, Dark, Default
}