package com.example.chatgptintegration

import androidx.compose.runtime.getValue // Add this import
import androidx.compose.runtime.setValue // Add this import
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf // Ensure this is imported
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception // Keep for safety, though less critical for mock

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<ChatMessage>()
    var isLoading by mutableStateOf(false)

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        messages.add(ChatMessage(text, Sender.USER)) // Add user message

        viewModelScope.launch {
            isLoading = true
            try {
                delay(1000) // Simulate network delay
                val aiResponse = "This is a reverted, mocked AI response to: '$text'"
                messages.add(ChatMessage(aiResponse, Sender.AI))
            } catch (e: Exception) {
                // This is less likely to happen with a simple delay, but good for consistency
                messages.add(ChatMessage("Error generating mock response: ${e.message}", Sender.AI))
            } finally {
                isLoading = false
            }
        }
    }
}
