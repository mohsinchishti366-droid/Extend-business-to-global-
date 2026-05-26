package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class TraderProfile(val name: String, val location: String, val type: String, val verified: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(onBack: () -> Unit) {
    var selectedFilter by remember { mutableIntStateOf(0) }
    val filters = listOf("All", "US Importers", "UK Buyers")
    
    val traders = listOf(
        TraderProfile("Global Brass UK", "London, UK", "Buyer", true),
        TraderProfile("Rustic Interiors", "New York, US", "Importer", true),
        TraderProfile("Birmingham Metalworks", "Birmingham, UK", "Supplier", true),
        TraderProfile("Texas Handle Co.", "Texas, US", "Buyer", false)
    )
    
    val filteredTraders = when(selectedFilter) {
        1 -> traders.filter { it.location.contains("US") }
        2 -> traders.filter { it.location.contains("UK") }
        else -> traders
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Marketplace") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            ScrollableTabRow(
                selectedTabIndex = selectedFilter,
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                filters.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedFilter == index,
                        onClick = { selectedFilter = index },
                        text = { Text(title) }
                    )
                }
            }
            
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredTraders) { trader ->
                    TraderCard(trader)
                }
            }
        }
    }
}

@Composable
fun TraderCard(trader: TraderProfile) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(trader.name, style = MaterialTheme.typography.titleMedium)
                Text("${trader.type} • ${trader.location}", style = MaterialTheme.typography.bodyMedium)
            }
            if (trader.verified) {
                Icon(
                    imageVector = Icons.Default.VerifiedUser,
                    contentDescription = "Verified Importer",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
