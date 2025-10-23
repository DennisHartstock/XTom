package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private enum class ClassicPanelType {
    GENERATOR,
    DETECTOR,
    SPECIMEN_MANIPULATOR,
    CT_SCAN
}

@Composable
fun ClassicScreen(onBack: () -> Unit) {
    var selectedPanel by remember { mutableStateOf(ClassicPanelType.GENERATOR) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopStatusBar(
                onBack = onBack,
                selectedPanel = selectedPanel,
                onPanelSelected = { selectedPanel = it }
            )
            ControlBar()
            Row(modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp)) {
                Column(
                    modifier = Modifier.weight(2f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Black) // Placeholder for image/video
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(modifier = Modifier.weight(1f).fillMaxHeight()) {
                            Text(
                                "HISTOGRAM",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Card(modifier = Modifier.weight(1f).fillMaxHeight()) {
                            Text(
                                "LINE PROFILE",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                when (selectedPanel) {
                    ClassicPanelType.GENERATOR -> GeneratorPanel(modifier = Modifier.weight(1f))
                    ClassicPanelType.DETECTOR -> DetectorPanel(modifier = Modifier.weight(1f))
                    ClassicPanelType.SPECIMEN_MANIPULATOR -> SpecimenManipulatorPanel(
                        modifier = Modifier.weight(
                            1f
                        )
                    )

                    ClassicPanelType.CT_SCAN -> CTScanPanel(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TopStatusBar(
    onBack: () -> Unit,
    selectedPanel: ClassicPanelType,
    onPanelSelected: (ClassicPanelType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        StatusCard(
            "GENERATOR",
            isSelected = selectedPanel == ClassicPanelType.GENERATOR,
            onClick = { onPanelSelected(ClassicPanelType.GENERATOR) },
            modifier = Modifier.weight(1f)
        )
        StatusCard(
            "DETECTOR",
            isSelected = selectedPanel == ClassicPanelType.DETECTOR,
            onClick = { onPanelSelected(ClassicPanelType.DETECTOR) },
            modifier = Modifier.weight(1f)
        )
        StatusCard(
            "SPECIMEN MANIPULATOR",
            isSelected = selectedPanel == ClassicPanelType.SPECIMEN_MANIPULATOR,
            onClick = { onPanelSelected(ClassicPanelType.SPECIMEN_MANIPULATOR) },
            modifier = Modifier.weight(1.5f)
        )
        StatusCard(
            "CT SCAN",
            isSelected = selectedPanel == ClassicPanelType.CT_SCAN,
            onClick = { onPanelSelected(ClassicPanelType.CT_SCAN) },
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("STOP")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatusCard(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Value: 0.0", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun ControlBar() {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {}) { Text("Save") }
            OutlinedButton(onClick = {}) { Text("Snap") }
            OutlinedButton(onClick = {}) { Text("Live on") }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(onClick = {}) { Text("Views") }
        }
    }
}

@Composable
private fun GeneratorPanel(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxHeight()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "GENERATOR",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Source Selection")
            OutlinedTextField(
                value = "XRaySource1",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Generator Functions")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {}) { Text("HV on") }
                OutlinedButton(onClick = {}) { Text("Warmup") }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Generator Settings")
            Text("Voltage in kV: 20.0")
            Text("Amperage in µA: 10.0")
        }
    }
}

@Composable
private fun DetectorPanel(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxHeight()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "DETECTOR",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Camera Mode Selection")
            OutlinedTextField(
                value = "Text",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Detector Settings")
            // Add your detector settings here
            Spacer(modifier = Modifier.height(16.dp))
            Text("Detector Calibration")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Optimize exposure")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = false, onCheckedChange = {})
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Dark correction")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = true, onCheckedChange = {})
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Bright correction")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = true, onCheckedChange = {})
            }
            Button(onClick = {}) { Text("Start calibration") }
            OutlinedButton(onClick = {}) { Text("Details") }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Bad Pixel Mapping")
            Button(onClick = {}) { Text("Start bad pixel mapping") }
        }
    }
}

@Composable
fun SpecimenManipulatorPanel(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxHeight()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "TRAJECTORIES",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TrajectoryCard("Add CT", modifier = Modifier.weight(1f))
                    TrajectoryCard("Add CT", modifier = Modifier.weight(1f))
                    TrajectoryCard("Add CT", modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TrajectoryCard("Add CT", modifier = Modifier.weight(1f))
                    TrajectoryCard("Add CT", modifier = Modifier.weight(1f))
                    TrajectoryCard("Add CT", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun TrajectoryCard(name: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Refresh, // Placeholder icon
                contentDescription = name,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(name, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun CTScanPanel(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxHeight()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "CT SCAN",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
