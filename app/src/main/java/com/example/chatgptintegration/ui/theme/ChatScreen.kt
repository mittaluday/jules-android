package com.example.chatgptintegration.ui.theme // Adjust if you change the package

package com.example.chatgptintegration.ui.theme

// ... other imports ...
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatgptintegration.ChatMessage
import com.example.chatgptintegration.ChatViewModel
import com.example.chatgptintegration.Sender
import androidx.compose.material3.CircularProgressIndicator // Add this
import androidx.compose.foundation.shape.RoundedCornerShape // For rounded corners
import androidx.compose.ui.graphics.Color // For custom colors
import androidx.compose.ui.unit.sp // For font sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    var inputText by remember { mutableStateOf("") }
    val messages = viewModel.messages // Observe messages
    val isLoading = viewModel.isLoading // Observe loading state

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(message)
            }
        }

        if (isLoading) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") },
                shape = RoundedCornerShape(20.dp), // Rounded corners for TextField
                colors = TextFieldDefaults.textFieldColors( // Minor styling
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                },
                enabled = !isLoading && inputText.isNotBlank(), // Disable button when loading or input is blank
                shape = RoundedCornerShape(20.dp) // Rounded corners for Button
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp), // Added horizontal padding
        horizontalArrangement = if (message.sender == Sender.USER) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 300.dp),
            shape = RoundedCornerShape( // Rounded corners for bubbles
                topStart = if (message.sender == Sender.USER) 16.dp else 4.dp,
                topEnd = if (message.sender == Sender.USER) 4.dp else 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (message.sender == Sender.USER) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(10.dp), // Increased padding
                fontSize = 16.sp // Slightly larger font
            )
        }
    }
}
