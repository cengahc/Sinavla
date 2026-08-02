package com.example.lgsapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lgsapp.ui.exams.ExamsScreen
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month

@Composable
fun LgsHomeScreen(
    targetSchool: String = "",
    targetScore: String = "",
    onNavigateToOnboarding: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Ana Sayfa") },
                    label = { Text("Ana Sayfa") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.List, contentDescription = "Denemeler") },
                    label = { Text("Denemeler") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
                    label = { Text("Profil") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> MainHomeContent(
                    targetSchool = targetSchool,
                    targetScore = targetScore,
                    onNavigateToOnboarding = onNavigateToOnboarding
                )
                1 -> ExamsScreen()
                2 -> ProfileContent(
                    targetSchool = targetSchool,
                    targetScore = targetScore,
                    onNavigateToOnboarding = onNavigateToOnboarding
                )
            }
        }
    }
}

@Composable
private fun MainHomeContent(
    targetSchool: String,
    targetScore: String,
    onNavigateToOnboarding: () -> Unit
) {
    var timeLeft by remember { mutableStateOf(calculateTimeLeft()) }

    LaunchedEffect(Unit) {
        while (true) {
            timeLeft = calculateTimeLeft()
            delay(1000)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Hedefin",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    Text(
                        text = targetSchool.ifEmpty { "Hedef Okul Belirtilmedi" },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    if (targetScore.isNotEmpty()) {
                        Text(
                            text = "Hedef Puan: $targetScore",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "LGS'ye Kalan Süre",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TimeUnit(value = timeLeft.days, label = "Gün")
                        TimeUnit(value = timeLeft.hours, label = "Saat")
                        TimeUnit(value = timeLeft.minutes, label = "Dakika")
                        TimeUnit(value = timeLeft.seconds, label = "Saniye")
                    }
                }
            }
        }

        item {
            Text(
                text = "Günlük İlerleme",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricCard(
                    title = "Çözülen Soru",
                    value = "120",
                    target = "200",
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Çalışma Süresi",
                    value = "2.5s",
                    target = "4s",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Text(
                text = "Ders İlerlemeleri",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item { SubjectProgressCard("Matematik", 0.65f, "13/20 Konu Tamamlandı") }
        item { SubjectProgressCard("Fen Bilimleri", 0.80f, "16/20 Konu Tamamlandı") }
        item { SubjectProgressCard("Türkçe", 0.75f, "15/20 Konu Tamamlandı") }
    }
}

@Composable
private fun ProfileContent(
    targetSchool: String,
    targetScore: String,
    onNavigateToOnboarding: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Profilim",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Hedef Okul: ${targetSchool.ifEmpty { "Belirtilmedi" }}")
                Text(text = "Hedef Puan: ${targetScore.ifEmpty { "Belirtilmedi" }}")
            }
        }

        Button(
            onClick = onNavigateToOnboarding,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hedefleri Güncelle")
        }
    }
}

@Composable
private fun TimeUnit(value: Long, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.toString().padStart(2, '0'),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    target: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "/$target",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun SubjectProgressCard(
    subject: String,
    progress: Float,
    statusText: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = subject, fontWeight = FontWeight.Medium)
                Text(
                    text = "%${(progress * 100).toInt()}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = statusText,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private data class TimeLeft(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
)

private fun calculateTimeLeft(): TimeLeft {
    val now = LocalDateTime.now()
    val examDate = LocalDateTime.of(2025, Month.JUNE, 15, 9, 30)

    if (now.isAfter(examDate)) {
        return TimeLeft(0, 0, 0, 0)
    }

    val duration = Duration.between(now, examDate)
    val days = duration.toDays()
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    return TimeLeft(days, hours, minutes, seconds)
}