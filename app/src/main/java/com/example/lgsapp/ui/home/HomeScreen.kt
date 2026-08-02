package com.example.lgsapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

// ----------------------------------------------------------------
// Renk Paleti
// ----------------------------------------------------------------
object LgsColors {
    val Background = Color(0xFFF5F0E8)
    val CardBackground = Color(0xFFFFFFFF)
    val NavyDark = Color(0xFF1F3A5F)
    val NavyDarker = Color(0xFF17304F)
    val TextPrimary = Color(0xFF1F3A5F)
    val TextSecondary = Color(0xFF8A8A8A)
    val Green = Color(0xFF2E9E6D)
    val GreenTrack = Color(0xFFE7E1D4)
    val Red = Color(0xFFE05263)
    val Teal = Color(0xFF1B7A6E)
    val IconBgBlue = Color(0xFFDCE7F0)
}

// ----------------------------------------------------------------
// Ana Sayfa
// ----------------------------------------------------------------
@Composable
fun LgsHomeScreen() {
    Scaffold(
        containerColor = LgsColors.Background,
        bottomBar = { LgsBottomBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScrollWorkaround(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            GreetingHeader(userName = "Elif", dateText = "24 Mart, Pazartesi")
            CountdownCard(examName = "LGS 2026", daysLeft = 87)
            ContinueTestCard(
                subjectName = "Fen Bilimleri Deneme 12",
                percentComplete = 65
            )
            StudySummarySection()
            SubjectProgressSection()
            Spacer(Modifier.height(12.dp))
        }
    }
}

// Basit bir scroll uzantısı (gerçek projede Modifier.verticalScroll(rememberScrollState()) kullanın)
@Composable
private fun Modifier.verticalScrollWorkaround(): Modifier {
    val scrollState = androidx.compose.foundation.rememberScrollState()
    return this.then(androidx.compose.foundation.verticalScroll(scrollState))
}

// ----------------------------------------------------------------
// Üst Karşılama Alanı
// ----------------------------------------------------------------
@Composable
fun GreetingHeader(userName: String, dateText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = dateText,
                fontSize = 14.sp,
                color = LgsColors.TextSecondary
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Günaydın, $userName! 👋",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = LgsColors.TextPrimary
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Sağ üstteki kırmızı ikon çipi (bildirim/kalp vb.)
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = LgsColors.Red
                )
            }
            Spacer(Modifier.width(8.dp))
            // Genişlet / profil butonu
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8A8A8A).copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.OpenInFull,
                    contentDescription = "Genişlet",
                    tint = Color.White
                )
            }
        }
    }
}

// ----------------------------------------------------------------
// Geri Sayım Kartı (LGS 2026 - 87 gün)
// ----------------------------------------------------------------
@Composable
fun CountdownCard(examName: String, daysLeft: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(LgsColors.NavyDark, LgsColors.NavyDarker)
                )
            )
            .padding(20.dp)
    ) {
        // Dekoratif arka plan halkası
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-30).dp)
                .size(160.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.05f))
        )

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = examName,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "$daysLeft gün",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Hedefine her gün biraz daha yakınsın.",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 14.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.TrackChanges,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------------------
// "Denemeye Dön" Kartı
// ----------------------------------------------------------------
@Composable
fun ContinueTestCard(subjectName: String, percentComplete: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(LgsColors.CardBackground)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(LgsColors.IconBgBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Science,
                    contentDescription = null,
                    tint = LgsColors.NavyDark
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "DEVAM ET",
                        fontSize = 12.sp,
                        letterSpacing = 1.sp,
                        color = LgsColors.TextSecondary
                    )
                    Text(
                        text = "%$percentComplete tamamlandı",
                        fontSize = 12.sp,
                        color = LgsColors.Green,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.height(2.dp))
                Text(
                    text = subjectName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LgsColors.TextPrimary
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        // İlerleme çubuğu
        LinearProgressIndicator(
            progress = { percentComplete / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50)),
            color = LgsColors.Green,
            trackColor = LgsColors.GreenTrack,
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: denemeye devam et */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LgsColors.NavyDark)
        ) {
            Text("Denemeye Dön", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.width(6.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
        }
    }
}

// ----------------------------------------------------------------
// Çalışma Özeti
// ----------------------------------------------------------------
data class SummaryStat(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color,
    val label: String,
    val value: String,
    val unit: String? = null
)

@Composable
fun StudySummarySection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Çalışma Özeti",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = LgsColors.TextPrimary
            )
            Text(
                text = "Bu dönem",
                fontSize = 14.sp,
                color = LgsColors.TextSecondary
            )
        }
        Spacer(Modifier.height(12.dp))

        val stats = listOf(
            SummaryStat(Icons.Outlined.ShowChart, LgsColors.NavyDark, "NET ORTALAMASI", "62.4", "/90"),
            SummaryStat(Icons.Outlined.CheckCircle, LgsColors.Green, "ÇÖZÜLEN SORU", "1,240"),
            SummaryStat(Icons.Outlined.Timer, LgsColors.Red, "BU HAFTA", "340", "dk")
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stats.forEach { stat ->
                StatCard(stat = stat, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun StatCard(stat: SummaryStat, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(LgsColors.CardBackground)
            .padding(14.dp)
    ) {
        Icon(
            imageVector = stat.icon,
            contentDescription = null,
            tint = stat.iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = stat.label,
            fontSize = 10.sp,
            color = LgsColors.TextSecondary,
            letterSpacing = 0.5.sp
        )
        Spacer(Modifier.height(2.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = stat.value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = LgsColors.TextPrimary
            )
            stat.unit?.let {
                Text(
                    text = it,
                    fontSize = 13.sp,
                    color = LgsColors.TextSecondary,
                    modifier = Modifier.padding(start = 2.dp, bottom = 1.dp)
                )
            }
        }
    }
}

// ----------------------------------------------------------------
// Derslerde İlerlemen (dairesel ilerleme göstergeleri)
// ----------------------------------------------------------------
data class SubjectProgress(
    val name: String,
    val percent: Int,
    val color: Color
)

@Composable
fun SubjectProgressSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Derslerde İlerlemen",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = LgsColors.TextPrimary
            )
            Text(
                text = "Tümü",
                fontSize = 14.sp,
                color = LgsColors.TextSecondary
            )
        }
        Spacer(Modifier.height(12.dp))

        val subjects = listOf(
            SubjectProgress("Türkçe", 78, LgsColors.NavyDark),
            SubjectProgress("Matematik", 54, LgsColors.Red),
            SubjectProgress("Fen Bilimleri", 61, LgsColors.Teal)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            subjects.forEach { subject ->
                SubjectProgressCard(subject = subject, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SubjectProgressCard(subject: SubjectProgress, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(LgsColors.CardBackground)
            .padding(vertical = 20.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(56.dp),
                color = LgsColors.GreenTrack,
                strokeWidth = 5.dp,
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
            CircularProgressIndicator(
                progress = { subject.percent / 100f },
                modifier = Modifier.size(56.dp),
                color = subject.color,
                strokeWidth = 5.dp,
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = subject.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = LgsColors.TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = "%${subject.percent}",
            fontSize = 13.sp,
            color = LgsColors.TextSecondary
        )
    }
}

// ----------------------------------------------------------------
// Alt Navigasyon Çubuğu
// ----------------------------------------------------------------
@Composable
fun LgsBottomBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { /* TODO */ },
            icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
            label = { Text("Ana Sayfa") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LgsColors.NavyDark,
                selectedTextColor = LgsColors.NavyDark,
                indicatorColor = LgsColors.IconBgBlue,
                unselectedIconColor = LgsColors.TextSecondary,
                unselectedTextColor = LgsColors.TextSecondary
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO */ },
            icon = { Icon(Icons.AutoMirrored.Outlined.Assignment, contentDescription = null) },
            label = { Text("Denemeler") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LgsColors.NavyDark,
                selectedTextColor = LgsColors.NavyDark,
                indicatorColor = LgsColors.IconBgBlue,
                unselectedIconColor = LgsColors.TextSecondary,
                unselectedTextColor = LgsColors.TextSecondary
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO */ },
            icon = { Icon(Icons.Outlined.BarChart, contentDescription = null) },
            label = { Text("Performansım") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LgsColors.NavyDark,
                selectedTextColor = LgsColors.NavyDark,
                indicatorColor = LgsColors.IconBgBlue,
                unselectedIconColor = LgsColors.TextSecondary,
                unselectedTextColor = LgsColors.TextSecondary
            )
        )
    }
}

// ----------------------------------------------------------------
// Önizleme
// ----------------------------------------------------------------
@Preview(showBackground = true, widthDp = 380, heightDp = 850)
@Composable
fun LgsHomeScreenPreview() {
    MaterialTheme {
        LgsHomeScreen()
    }
}
