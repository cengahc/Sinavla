package com.example.lgsapp.data

import android.content.Context

data class UserPrefs(
    val name: String,
    val examName: String,
    val examDateMillis: Long
)

class PreferencesManager(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    fun save(userPrefs: UserPrefs) {
        prefs.edit()
            .putString(KEY_NAME, userPrefs.name)
            .putString(KEY_EXAM_NAME, userPrefs.examName)
            .putLong(KEY_EXAM_DATE, userPrefs.examDateMillis)
            .apply()
    }

    fun load(): UserPrefs? {
        val name = prefs.getString(KEY_NAME, null) ?: return null
        val examName = prefs.getString(KEY_EXAM_NAME, null) ?: return null
        val examDate = prefs.getLong(KEY_EXAM_DATE, -1L)
        if (examDate < 0L) return null
        return UserPrefs(name, examName, examDate)
    }

    companion object {
        private const val PREFS_FILE = "lgs_app_prefs"
        private const val KEY_NAME = "user_name"
        private const val KEY_EXAM_NAME = "exam_name"
        private const val KEY_EXAM_DATE = "exam_date_millis"
    }
}
