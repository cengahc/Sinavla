package com.example.lgsapp.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lgsapp.data.UserPrefs
import com.example.lgsapp.util.ExamDates
import java.util.Calendar

@Composable
fun OnboardingScreen(
    initialPrefs: UserPrefs? = null,
    onSave: (UserPrefs) -> Unit
) {
    var name by remember { mutableStateOf(initialPrefs?.name ?: "") }
    var selectedExam by remember {
        mutableStateOf(
            ExamDates.ExamType.entries.find { it.displayName == initialPrefs?.examName }
                ?: ExamDates.ExamType.LGS
        )
    }

    val currentYear = remember { Calendar.getInstance().get(Calendar.YEAR) }
    val yearOptions = remember { listOf(currentYear, currentYear + 1, currentYear + 2) }
    var selectedYear by remember { mutableStateOf(yearOptions[1]) } // varsayılan: gelecek yıl

    var errorText by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Hoş geldin!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Sana özel bir deneyim için birkaç bilgiye ihtiyacımız var.",
            fontSize = 14.sp
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Adın") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(text = "Hangi sınava hazırlanıyorsun?", fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ExamDates.ExamType.entries.forEach { exam ->
                FilterChip(
                    selected = selectedExam == exam,
                    onClick = { selectedExam = exam },
                    label = { Text(exam.displayName) }
                )
            }
        }

        Text(text = "Hangi yıl?", fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            yearOptions.forEach { year ->
                FilterChip(
                    selected = selectedYear == year,
                    onClick = { selectedYear = year },
                    label = { Text(year.toString()) }
                )
            }
        }

        val previewMillis = remember(selectedExam, selectedYear) {
            ExamDates.estimatedDateMillis(selectedExam, selectedYear)
        }
        val isEstimated = remember(selectedExam, selectedYear) {
            ExamDates.isEstimated(selectedExam, selectedYear)
        }
        val previewDateText = remember(previewMillis) {
            java.text.SimpleDateFormat("d MMMM yyyy, EEEE", java.util.Locale("tr")).format(java.util.Date(previewMillis))
        }
        Text(
            text = if (isEstimated) {
                "Tahmini sınav tarihi: $previewDateText (resmi tarih açıklandığında güncelleyebilirsin)"
            } else {
                "Sınav tarihi: $previewDateText"
            },
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        errorText?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (name.isBlank()) {
                    errorText = "Lütfen adını gir."
                    return@Button
                }

                onSave(
                    UserPrefs(
                        name = name.trim(),
                        examName = selectedExam.displayName,
                        examDateMillis = previewMillis
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kaydet ve devam et")
        }
    }
}
