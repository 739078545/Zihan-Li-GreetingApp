package com.example.greetings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import java.util.Calendar
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview
import com.example.greetings.ui.theme.GreetingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greetings()
                }
            }
        }
    }
}

@Composable
fun Greetings(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .background(Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter your name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Button(
                onClick = {
                    if (name.isNotEmpty()) {
                        coroutineScope.launch {
                            val additionalGreeting = getAdditionalGreetingMessage()
                            snackbarHostState.showSnackbar("$additionalGreeting $name!")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Confirmed",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(top = 200.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

fun getAdditionalGreetingMessage(): Any {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 0..4 -> "It's the middle of the night. Sweet Dreams!"
        in 5..11 -> "Rise and Shine! Good morning!"
        in 12..16 -> "Hope You're Having a Great Afternoon!"
        in 17..20 -> "Have a Pleasant Evening!"
        in  21..23 -> "Good Night and Rest Easy!"
        else -> "Hello!"
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingsTheme {
        Greetings()
    }
}