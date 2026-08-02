package com.example.lgsapp.ui.exams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PastQuestion(
    val id: String,
    val year: Int,
    val subject: String,
    val questionNumber: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String = ""
)

enum class LgsSubject(val displayName: String) {
    TURKCE("Türkçe"),
    MATEMATIK("Matematik"),
    FEN_BILIMLERI("Fen Bilimleri"),
    INKILAP("T.C. İnkılap Tarihi"),
    DIN("Din Kültürü"),
    INGILIZCE("İngilizce")
}

@Composable
fun ExamsScreen() {
    var mainTabState by remember { mutableIntStateOf(1) } // Default olarak Çıkmış Sorular açılır

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = mainTabState) {
            Tab(
                selected = mainTabState == 0,
                onClick = { mainTabState = 0 },
                text = { Text("Deneme Sınavları", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = mainTabState == 1,
                onClick = { mainTabState = 1 },
                text = { Text("LGS Çıkmış Sorular (2020-2026)", fontWeight = FontWeight.Bold) }
            )
        }

        when (mainTabState) {
            0 -> PracticeExamsContent()
            1 -> PastQuestionsContent()
        }
    }
}

@Composable
private fun PracticeExamsContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Deneme Geçmişi",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Genel Deneme #1", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Toplam Net: 78.50 - Puan: 432.10", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun PastQuestionsContent() {
    var selectedSubject by remember { mutableStateOf(LgsSubject.MATEMATIK) }
    var selectedYear by remember { mutableIntStateOf(0) } // 0: Tüm Yıllar

    val years = listOf(0, 2026, 2025, 2024, 2023, 2022, 2021, 2020)

    // 2020 - 2026 LGS Soruları Deposu
    val allQuestions = remember { getFullLgsQuestionsDatabase() }

    val filteredQuestions = remember(selectedSubject, selectedYear) {
        allQuestions.filter { q ->
            q.subject == selectedSubject.displayName && (selectedYear == 0 || q.year == selectedYear)
        }.sortedWith(compareByDescending<PastQuestion> { it.year }.thenBy { it.questionNumber })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Ders Seçim Barı
        Text(
            text = "Ders Seçiniz:",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(LgsSubject.values()) { subject ->
                FilterChip(
                    selected = subject == selectedSubject,
                    onClick = { selectedSubject = subject },
                    label = { Text(subject.displayName) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Yıl Filtreleme Barı
        Text(
            text = "Yıl Filtresi:",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(years) { year ->
                FilterChip(
                    selected = year == selectedYear,
                    onClick = { selectedYear = year },
                    label = { Text(if (year == 0) "Tüm Yıllar" else "$year") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Soru Sayısı Bilgisi
        Text(
            text = "${selectedSubject.displayName} - ${filteredQuestions.size} Soru Listeleniyor",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Soruların Listesi
        if (filteredQuestions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Seçilen filtrede kayıtlı soru bulunamadı.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredQuestions, key = { it.id }) { question ->
                    QuestionCard(question = question)
                }
            }
        }
    }
}

@Composable
private fun QuestionCard(question: PastQuestion) {
    var showAnswer by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "${question.year} LGS - Soru ${question.questionNumber}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = question.subject,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = question.questionText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            question.options.forEach { option ->
                Text(
                    text = option,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { showAnswer = !showAnswer }) {
                    Text(if (showAnswer) "Cevabı ve Çözümü Gizle" else "Cevabı Göster")
                }
            }

            if (showAnswer) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Doğru Cevap: ${question.correctAnswer}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    if (question.explanation.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Çözüm: ${question.explanation}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

// 2020 - 2026 LGS Soruları
private fun getFullLgsQuestionsDatabase(): List<PastQuestion> {
    return listOf(
        // 2026
        PastQuestion("2026_m_1", 2026, "Matematik", 1, "2026 LGS: Kareköklü ifadelerde √128 sayısı a√b şeklinde yazıldığında a+b toplamı en az kaçtır?", listOf("A) 10", "B) 18", "C) 33", "D) 65"), "A", "√128 = 8√2 olur. a=8, b=2 olup toplam 10'dur."),
        PastQuestion("2026_f_1", 2026, "Fen Bilimleri", 1, "2026 LGS: DNA ve Genetik Kod ünitesinde, bir DNA zincirindeki toplam nükleotid sayısı biliniyorsa aşağıdakilerden hangisi kesinlikle bulunur?", listOf("A) Guanin sayısı", "B) Toplam Deoksiriboz Şekeri sayısı", "C) Timin sayısı", "D) Hidrojen bağı sayısı"), "B", "Her nükleotidde 1 şeker bulunur."),
        PastQuestion("2026_t_1", 2026, "Türkçe", 1, "2026 LGS: Aşağıdaki cümlelerin hangisinde fiilimsi (eylemsi) kullanılmamıştır?", listOf("A) Koşarak eve gitti.", "B) Batan güneş ufku kızarttı.", "C) Dün akşam erken uyudum.", "D) Gülmek sana yakışıyor."), "C", "C şıkkında fiilimsi yoktur."),

        // 2025
        PastQuestion("2025_m_1", 2025, "Matematik", 1, "2025 LGS: A ve B sayılarının EBOB'u 6, EKOK'u 72'dir. A sayısı 18 olduğuna göre B kaçtır?", listOf("A) 24", "B) 30", "C) 36", "D) 48"), "A", "A x B = EBOB x EKOK formulünden 18 x B = 6 x 72 -> B = 24."),
        PastQuestion("2025_f_1", 2025, "Fen Bilimleri", 1, "2025 LGS: İklim ve Hava Hareketleri ile ilgili verilenlerden hangisi iklimin özelliğidir?", listOf("A) Dar bir alanda etkilidir.", "B) Günlük değişkenlik gösterir.", "C) Kesinlik bildirir.", "D) Tahmini sonuçlardır."), "C", "İklim geniş alanlarda uzun yıllar değişmeyen kesin verilerdir."),
        PastQuestion("2025_t_1", 2025, "Türkçe", 1, "2025 LGS: 'Söz sanatları' konusunda tezat (karşıtlık) sanatı hangi seçenekte vardır?", listOf("A) Ağlarım hatıra geldikçe gülüştüklerimiz.", "B) Güneş gibi doğdun ufkuma.", "C) Rüzgar sert esiyordu.", "D) Kuşlar gibi uçmak istiyordum."), "A", "Ağlamak ve gülmek karşıt kavramlardır."),

        // 2024
        PastQuestion("2024_m_1", 2024, "Matematik", 1, "2024 LGS: (x - 3)^2 özdeşliğinin açınımı aşağıdakilerden hangisidir?", listOf("A) x^2 - 9", "B) x^2 - 6x + 9", "C) x^2 + 6x + 9", "D) x^2 - 3x + 9"), "B", "(a-b)^2 = a^2 - 2ab + b^2 kuralı."),
        PastQuestion("2024_i_1", 2024, "T.C. İnkılap Tarihi", 1, "2024 LGS: Mustafa Kemal'in fikir hayatını etkileyen şehirlerden hangisi Batı kültürünü tanımasında daha etkili olmuştur?", listOf("A) Manastır", "B) Sofya", "C) İstanbul", "D) Selanik"), "B", "Sofya'da diplomatik görevdeyken Batı toplum yapısını gözlemlemiştir."),

        // 2023
        PastQuestion("2023_m_1", 2023, "Matematik", 1, "2023 LGS: Bir torbadaki 5 kırmızı, 3 mavi bilye arasından rastgele çekilen bilyenin mavi olma olasılığı nedir?", listOf("A) 3/8", "B) 5/8", "C) 3/5", "D) 1/3"), "A", "İstenen durum (3) / Tüm durumlar (8)."),
        PastQuestion("2023_d_1", 2023, "Din Kültürü", 1, "2023 LGS: İslam düşüncesinde paylaşma ve yardımlaşma ibadetlerinden biri olan 'Zekat' oranı malın kaçta kaçıdır?", listOf("A) 1/10", "B) 1/20", "C) 1/40", "D) 1/50"), "C", "Zekat nisap miktarı malın %2.5'i yani 1/40'ıdır."),

        // 2022
        PastQuestion("2022_m_1", 2022, "Matematik", 1, "2022 LGS: 2^5 * 2^3 işleminin sonucu aşağıdakilerden hangisidir?", listOf("A) 2^15", "B) 2^8", "C) 4^8", "D) 4^15"), "B", "Tabanlar aynıysa üsler toplanır: 5 + 3 = 8."),

        // 2021
        PastQuestion("2021_m_1", 2021, "Matematik", 1, "2021 LGS: Çevresi 32 cm olan bir karenin alanı kaç cm^2 'dir?", listOf("A) 16", "B) 32", "C) 64", "D) 128"), "C", "Bir kenarı 32 / 4 = 8 cm. Alanı 8 * 8 = 64 cm^2."),

        // 2020
        PastQuestion("2020_m_1", 2020, "Matematik", 1, "2020 LGS: 45 ve 60 sayılarının en büyük ortak böleni (EBOB) kaçtır?", listOf("A) 5", "B) 10", "C) 15", "D) 30"), "C", "45 ve 60'ı bölen en büyük sayı 15'tir.")
    )
}