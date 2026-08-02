package com.example.lgsapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lgsapp.ui.exams.ExamsScreen

@Composable
fun HomeScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Ana Sayfa", "Denemeler", "Profil")
    val icons = listOf(Icons.Default.Home, Icons.Default.List, Icons.Default.Person)

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedItem) {
                0 -> HomeDashboardContent()
                1 -> ExamsScreen()
                2 -> ProfileContent()
            }
        }
    }
}

@Composable
fun HomeDashboardContent() {
    Text(text = "Ana Sayfa / Sayaç Ekranı", modifier = Modifier.padding(16.dp))
}

@Composable
fun ProfileContent() {
    Text(text = "Profil / Ayarlar Ekranı", modifier = Modifier.padding(16.dp))
}