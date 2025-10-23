package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class RecentFile(
    val name: String,
    val lastModified: String
)

@Composable
fun WizardScreen(onBack: () -> Unit) {
    val recentFiles = listOf(
        RecentFile("Document Name", "3 hours ago"),
        RecentFile("Document Name", "3 hours ago"),
        RecentFile("Document Name", "3 hours ago"),
        RecentFile("Document Name", "3 hours ago"),
        RecentFile("Document Name", "3 hours ago"),
        RecentFile("Document Name", "3 hours ago")
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "X",
                fontSize = 500.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0x1AFFFFFF), // a very light color
                modifier = Modifier.align(Alignment.Center)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Welcome, User Name",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {}) {
                    Text("New File")
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = {}) {
                    Text("Open")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Recent Files",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(recentFiles) { file ->
                        RecentFileCard(file)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "XTom",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }
    }
}

@Composable
fun RecentFileCard(file: RecentFile) {
    Card(
        modifier = Modifier.size(160.dp, 120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder shapes
                Row {
                    Box(
                        modifier = Modifier.size(30.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier.size(30.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    )
                }
            }
            Text(text = file.name, fontSize = 14.sp)
            Text(
                text = file.lastModified,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
