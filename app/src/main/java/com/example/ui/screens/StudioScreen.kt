package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemini.GeminiHelper
import com.example.model.ProductDesign
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioScreen(onBack: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val geminiHelper = remember { GeminiHelper() }
    
    var showDialog by remember { mutableStateOf(false) }
    var material by remember { mutableStateOf("") }
    var itemType by remember { mutableStateOf("") } // Knob or Handle
    
    var generatedIdea by remember { mutableStateOf<String?>(null) }
    var isGenerating by remember { mutableStateOf(false) }
    
    val savedDesigns = remember { mutableStateListOf(
        ProductDesign("1", "Royal Oak Handle", "Wood & Brass", "Vintage", "A hybrid wooden handle with brass accents.", ""),
        ProductDesign("2", "Jade Stone Knob", "Stone", "Minimalist", "A spherical polished stone knob.", "")
    ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Studio") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showDialog = true },
                text = { Text("New Design Idea") },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(savedDesigns) { design ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(design.name, style = MaterialTheme.typography.titleMedium)
                        Text("Material: ${design.material}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(design.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
        
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Generate Concept") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = itemType,
                            onValueChange = { itemType = it },
                            label = { Text("Item (e.g. Knob, Handle)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = material,
                            onValueChange = { material = it },
                            label = { Text("Material (Brass, Bone, Wood, etc.)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (isGenerating) {
                            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                        } else if (generatedIdea != null) {
                            Text("AI Suggestion: $generatedIdea", modifier = Modifier.padding(top = 16.dp))
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (generatedIdea == null) {
                                isGenerating = true
                                coroutineScope.launch {
                                    generatedIdea = geminiHelper.generateDesignConcept(material, itemType)
                                    isGenerating = false
                                }
                            } else {
                                savedDesigns.add(ProductDesign(System.currentTimeMillis().toString(), "Concept $itemType", material, "AI", generatedIdea!!, ""))
                                showDialog = false
                                generatedIdea = null
                                material = ""
                                itemType = ""
                            }
                        }
                    ) {
                        Text(if (generatedIdea == null) "Generate" else "Save Design")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
