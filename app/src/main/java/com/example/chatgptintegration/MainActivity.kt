package com.example.chatgptintegration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.activity.viewModels // Required for by viewModels()
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatgptintegration.ui.theme.ChatScreen // Import ChatScreen
// import com.example.chatgptintegration.ui.theme.ChatGPTIntegrationTheme // Assuming a theme might be defined here later

class MainActivity : ComponentActivity() {
    private val chatViewModel: ChatViewModel by viewModels() // Instantiate ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme { // Use the placeholder theme created earlier or a new one
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(viewModel = chatViewModel) // Display ChatScreen
                }
            }
        }
    }
}

// Placeholder Theme (can be moved to a separate Theme.kt file later if needed)
@Composable
fun ChatAppTheme(content: @Composable () -> Unit) {
    MaterialTheme { // Using default MaterialTheme for now, can be customized
        content()
    }
}

// Optional: Preview for MainActivity showing ChatScreen
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChatAppTheme {
        // Previewing ChatScreen directly might be more useful here,
        // but it requires handling ViewModel instantiation in Preview,
        // which can be done with a fake ViewModel or by passing null/default values.
        // For simplicity, we'll keep the Greeting preview for now,
        // or remove it if it causes confusion.
        // For a more robust preview of ChatScreen, it should be in ChatScreen.kt
        // Surface { ChatScreen(viewModel = ChatViewModel()) } // This line would need a parameterless constructor or a fake for preview
        Greeting("Android") // Keeping existing simple preview
    }
}
 @Composable
fun Greeting(name: String, modifier: Modifier = Modifier) { // Keep if DefaultPreview uses it
    androidx.compose.material3.Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
