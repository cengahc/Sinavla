package com.example.lgsapp.ui.exams

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ExamItem(
    val id: Int,
    val title: String,
    val totalQuestions: Int,
    val isCompleted: Boolean,
    val score: String? = null
)

@Composable
fun ExamsScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Çözülmeyenler", "Çözülenler")

    // Örnek Deneme Verileri
    val mockExams = remember {
        listOf(
            ExamItem(1, "LGS Genel Deneme 1", 90, isCompleted = true, score = "412.5 Puan"),
            ExamItem(2, "Sayısal Bölüm Deneme 2", 40, isCompleted = true, score = "38 Net"),
            ExamItem(3, "Sözel Bölüm Deneme 1", 50, isCompleted = false),
            ExamItem(4, "LGS Türkiye Geneli Deneme", 90, isCompleted = false),
            ExamItem(5, "Matematik Özel Deneme", 20, isCompleted = false)
        )
    }

    val uncompletedExams = mockExams.filter { !it.isCompleted }
    val completedExams = mockExams.filter { it.isCompleted }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Deneme Sınavları",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Sekmeler
        TabRow(selectedTabIndex = selectedTab) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = FontWeight.SemiBold) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kategoriye Göre Liste
        when (selectedTab) {
            0 -> ExamList(exams = uncompletedExams, isCompletedTab = false)
            1 -> ExamList(exams = completedExams, isCompletedTab = true)
        }
    }
}

@Composable
fun ExamList(exams: List<ExamItem>, isCompletedTab: Boolean) {
    if (exams.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Bu kategoride henüz deneme bulunmuyor.")
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(exams) { exam ->
                ExamCard(exam = exam, isCompletedTab = isCompletedTab)
            }
        }
    }
}

@Composable
fun ExamCard(exam: ExamItem, isCompletedTab: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exam.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${exam.totalQuestions} Soru",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (isCompletedTab && exam.score != null) {
                    Text(
                        text = "Sonuç: ${exam.score}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Button(
                onClick = { /* Deneme detay veya başlatma eylemi */ }
            ) {
                Text(if (isCompletedTab) "İncele" else "Başla")
            }
        }
    }
}