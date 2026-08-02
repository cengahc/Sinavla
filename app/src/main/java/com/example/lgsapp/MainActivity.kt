package com.example.lgsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.lgsapp.data.PreferencesManager
import com.example.lgsapp.ui.home.LgsHomeScreen
import com.example.lgsapp.ui.onboarding.OnboardingScreen
import com.example.lgsapp.ui.theme.LgsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LgsAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
private fun AppRoot() {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var userPrefs by remember { mutableStateOf(preferencesManager.load()) }
    var isEditing by remember { mutableStateOf(userPrefs == null) }

    if (isEditing) {
        OnboardingScreen(
            initialPrefs = userPrefs,
            onSave = { newPrefs ->
                preferencesManager.save(newPrefs)
                userPrefs = newPrefs
                isEditing = false
            }
        )
    } else {
        val prefs = userPrefs
        if (prefs != null) {
            LgsHomeScreen(
                userName = prefs.name,
                examName = prefs.examName,
                examDateMillis = prefs.examDateMillis,
                onEditProfile = { isEditing = true }
            )
        }
    }
}
