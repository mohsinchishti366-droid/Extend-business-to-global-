package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.Shipment

@Composable
fun DashboardScreen(
    onNavigateToMarket: () -> Unit,
    onNavigateToStudio: () -> Unit,
    onNavigateToLogistics: () -> Unit,
    onNavigateToMessages: () -> Unit
) {
    val mockShipments = listOf(
        Shipment("SH100", "TRK-90210A", "London Brass Artisans", "UK", "US", "In Transit", 4500.00, "$", "2026-05-24"),
        Shipment("SH101", "TRK-4412B", "New York Woodcraft", "US", "UK", "Customs Clearance", 1200.00, "£", "2026-05-25"),
        Shipment("SH102", "TRK-7711C", "Agra Stone Exports", "India", "US", "Delivered", 8900.00, "$", "2026-05-20")
    )

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("CraftLink Dashboard", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            item {
                Text("Quick Actions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }
            
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ActionCard(
                        title = "Marketplace",
                        icon = Icons.Default.Storefront,
                        onClick = onNavigateToMarket,
                        modifier = Modifier.weight(1f)
                    )
                    ActionCard(
                        title = "Studio",
                        icon = Icons.Default.Palette,
                        onClick = onNavigateToStudio,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ActionCard(
                        title = "Logistics AI",
                        icon = Icons.Default.LocalShipping,
                        onClick = onNavigateToLogistics,
                        modifier = Modifier.weight(1f)
                    )
                    ActionCard(
                        title = "Messages",
                        icon = Icons.Default.Chat,
                        onClick = onNavigateToMessages,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            item {
                Text("Recent Shipments", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }
            
            items(mockShipments) { shipment ->
                ShipmentCard(shipment)
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun ActionCard(title: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun ShipmentCard(shipment: Shipment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(shipment.trackingNumber, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text("${shipment.origin} -> ${shipment.destination}", style = MaterialTheme.typography.bodyMedium)
                Text(shipment.status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.tertiary)
            }
            Text("${shipment.currency}${shipment.totalCost}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}
