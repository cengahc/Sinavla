package com.example.lgsapp.util

import java.util.Calendar

/**
 * LGS ve YKS için bilinen / tahmini sınav tarihlerini hesaplar.
 * 2026 ve 2027 için MEB / ÖSYM duyurularına dayanan tarihler kullanılır.
 * Daha ileri yıllar için geçmiş yılların takvim düzenine göre (LGS: Haziran'ın
 * 2. Pazar günü, YKS: Haziran'ın 3. Cumartesi günü) yaklaşık bir tarih hesaplanır.
 */
object ExamDates {

    enum class ExamType(val displayName: String) {
        LGS("LGS"),
        YKS("YKS")
    }

    /** Belirtilen sınav ve yıl için tarihin gün başlangıcı (00:00) milis cinsinden döner. */
    fun estimatedDateMillis(examType: ExamType, year: Int): Long {
        val (month, day) = knownDate(examType, year) ?: heuristicDate(examType, year)
        return Calendar.getInstance().apply {
            clear()
            set(year, month - 1, day, 0, 0, 0)
        }.timeInMillis
    }

    /** Bu tarihin kesin mi yoksa tahmini mi olduğunu belirtir (kullanıcıya not göstermek için). */
    fun isEstimated(examType: ExamType, year: Int): Boolean {
        // Yalnızca resmi olarak duyurulmuş ve geçmişte kalan sınavlar "kesin" kabul edilir.
        return !(examType == ExamType.LGS && year <= 2026)
    }

    private fun knownDate(examType: ExamType, year: Int): Pair<Int, Int>? {
        return when (examType) {
            ExamType.LGS -> when (year) {
                2026 -> 6 to 13 // 13 Haziran 2026 (gerçekleşti)
                2027 -> 6 to 13 // 13 Haziran 2027 (tahmini)
                else -> null
            }
            ExamType.YKS -> when (year) {
                2026 -> 6 to 20 // 20 Haziran 2026 (gerçekleşti, TYT)
                2027 -> 6 to 19 // 19 Haziran 2027 (tahmini, TYT)
                else -> null
            }
        }
    }

    /** Bilinen bir tarih yoksa geçmiş yılların düzenine göre yaklaşık bir tarih üretir. */
    private fun heuristicDate(examType: ExamType, year: Int): Pair<Int, Int> {
        val targetWeekday = when (examType) {
            ExamType.LGS -> Calendar.SUNDAY
            ExamType.YKS -> Calendar.SATURDAY
        }
        val occurrence = when (examType) {
            ExamType.LGS -> 2
            ExamType.YKS -> 3
        }
        val cal = Calendar.getInstance().apply {
            clear()
            set(year, Calendar.JUNE, 1)
        }
        var count = 0
        while (true) {
            if (cal.get(Calendar.DAY_OF_WEEK) == targetWeekday) {
                count++
                if (count == occurrence) break
            }
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        return (cal.get(Calendar.MONTH) + 1) to cal.get(Calendar.DAY_OF_MONTH)
    }
}
