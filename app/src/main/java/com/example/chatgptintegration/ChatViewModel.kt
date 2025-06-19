package com.example.chatgptintegration

import androidx.compose.runtime.getValue // Add this import
import androidx.compose.runtime.setValue // Add this import
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf // Ensure this is imported
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.chat.completions.ChatCompletionCreateParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<ChatMessage>()
    var isLoading by mutableStateOf(false)
    // private val openAIService = OpenAIService.create() // Removed Retrofit service

    // Placeholder for API Key - In a real app, manage this securely!
    // This should ideally come from BuildConfig or a secure configuration.
    // For this exercise, it's a simple constant. If you run this,
    // **REPLACE "YOUR_OPENAI_API_KEY_PLACEHOLDER" with your actual API key.**
    private val apiKey: String = "YOUR_OPENAI_API_KEY_PLACEHOLDER"


    private suspend fun getAiResponseFromClient(userInput: String, currentApiKey: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val client = OpenAIOkHttpClient.builder()
                    .apiKey(currentApiKey.removePrefix("Bearer ").trim()) // OpenAI client expects the raw key
                    .build()

                val params = ChatCompletionCreateParams.builder()
                    .addUserMessage(userInput)
                    .model(ChatModel.GPT_3_5_TURBO_0125) // Use a specific model string or ChatModel enum
                    .maxTokens(150L) // Make sure this is Long if the builder expects Long
                    .build()

                val chatCompletion = client.chat().completions().create(params)
                chatCompletion.choices.firstOrNull()?.message?.content?.trim()
            } catch (e: Exception) {
                e.printStackTrace() // Basic error logging
                "Error: ${e.message}" // Return error message to be displayed
            }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMessage = ChatMessage(text, Sender.USER)
        messages.add(userMessage)

        viewModelScope.launch {
            isLoading = true
            try {
                if (apiKey == "YOUR_OPENAI_API_KEY_PLACEHOLDER" || apiKey.isBlank()) {
                    messages.add(ChatMessage("API Key not configured. Please set it in ChatViewModel.kt.", Sender.AI))
                    isLoading = false
                    return@launch
                }

                val aiResponseText = getAiResponseFromClient(text, apiKey)
                if (aiResponseText != null) {
                    messages.add(ChatMessage(aiResponseText, Sender.AI))
                } else {
                    messages.add(ChatMessage("Unknown error from AI client", Sender.AI))
                }

            } catch (e: Exception) { // Catch any unexpected errors from the suspend function call itself
                messages.add(ChatMessage("Error calling AI: ${e.message}", Sender.AI))
            } finally {
                isLoading = false
            }
        }
    }
}
