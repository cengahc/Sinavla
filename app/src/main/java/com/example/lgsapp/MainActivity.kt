package com.example.lgsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.lgsapp.data.UserPrefs
import com.example.lgsapp.ui.home.LgsHomeScreen
import com.example.lgsapp.ui.onboarding.OnboardingScreen
import com.example.lgsapp.ui.theme.LgsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LgsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var userPrefsState by remember { mutableStateOf<UserPrefs?>(null) }

                    if (userPrefsState != null) {
                        LgsHomeScreen(
                            onNavigateToOnboarding = {
                                userPrefsState = null
                            }
                        )
                    } else {
                        OnboardingScreen(
                            onSave = { userPrefs ->
                                userPrefsState = userPrefs
                            }
                        )
                    }
                }
            }
        }
    }
}