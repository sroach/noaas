package gy.roach.no.noaas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

import noaas.composeapp.generated.resources.Res
import noaas.composeapp.generated.resources.compose_multiplatform

// Enum to represent different screens in the app
enum class Screen {
    MAIN, ABOUT
}

@Composable
fun App() {
    // State to track the current screen
    var currentScreen by remember { mutableStateOf(Screen.MAIN) }

    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (currentScreen == Screen.ABOUT) {
                            // Show back button when on About screen
                            TextButton(onClick = { currentScreen = Screen.MAIN }) {
                                Text("Back to Main")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        } else {
                            // Show About button when on Main screen
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(onClick = { currentScreen = Screen.ABOUT }) {
                                Text("About")
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentScreen) {
                    Screen.MAIN -> MainScreen()
                    Screen.ABOUT -> AboutScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // State for the current reason
    var reason by remember { mutableStateOf("") }
    // Create a coroutine scope that follows the lifecycle of the composable
    val coroutineScope = rememberCoroutineScope()

    // Load a random reason when the screen is first composed
    LaunchedEffect(Unit) {
        reason = ReasonsProvider().getRandomReason()
    }

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Title section
        Card(
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No As A Service",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "A simple service that provides you with creative ways to say 'No'.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Display the random reason in a stylish card
        Card(
            modifier = Modifier.padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = reason,
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Button to get a new random reason
        Button(onClick = {
            // Launch a coroutine to get a new random reason
            coroutineScope.launch {
                reason = ReasonsProvider().getRandomReason()
            }
        }) {
            Text("Get Another Reason")
        }
    }
}

@Composable
fun AboutScreen() {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // About title
        Text(
            text = "About No As A Service",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // App description
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "App Capabilities",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "No As A Service provides creative and humorous ways to say 'No' in various situations. The app displays a random 'No' phrase each time you open it, and you can get a new phrase with a simple button click.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Perfect for when you need to decline requests, invitations, or obligations with style and humor!",
                    fontSize = 16.sp
                )
            }
        }

        // Attribution section
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Attribution",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Link to the reasons source
                Text(
                    text = "Reasons sourced from:",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "github.com/hotheadhacker/no-as-a-service",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clickable {
                            uriHandler.openUri("https://github.com/hotheadhacker/no-as-a-service")
                        }
                )

                // Link to this project's repo
                Text(
                    text = "This project's repository:",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "github.com/steveroach/noaas",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        uriHandler.openUri("https://github.com/steveroach/noaas")
                    }
                )
            }
        }
    }
}
