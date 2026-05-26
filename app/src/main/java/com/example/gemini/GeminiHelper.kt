package com.example.gemini

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiHelper {
    suspend fun generateDesignConcept(material: String, itemType: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        val prompt = "Generate a short, creative product description for a premium handicraft $itemType made of $material. Emphasize global trade appeal and craftsmanship. Max 3 sentences."
        
        val request = GenerateContentRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt))))
        )
        
        try {
            val response = RetrofitClient.service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "Failed to generate design."
        } catch (e: Exception) {
            "Error generating concept: ${e.message}"
        }
    }
    
    suspend fun getLogisticsAdvice(userQuery: String, history: List<com.example.model.GeminiMessage>): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        
        val contents = history.map { 
            Content(parts = listOf(Part(text = it.text)), role = if (it.role == "user") "user" else "model") 
        }.toMutableList()
        
        contents.add(Content(parts = listOf(Part(text = userQuery)), role = "user"))
        
        val sysInstruct = Content(parts = listOf(Part(text = "You are a senior logistics and global trade advisor specializing in handicraft exports (brass, wooden, bone, stone items) between the US and UK. Provide concise, expert advice on shipping, customs documentation, agents, and multi-currency transactions. Keep answers brief (max 3-4 sentences).")))
        
        val request = GenerateContentRequest(
            contents = contents,
            systemInstruction = sysInstruct
        )
        
        try {
            val response = RetrofitClient.service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "I am unable to provide advice at this moment."
        } catch (e: Exception) {
            "Connection Error. Please check your network or API Key. (${e.localizedMessage})"
        }
    }
}
