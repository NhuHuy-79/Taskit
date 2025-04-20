package badang.android.taskit.feature_setting.domain.repository

import android.content.Context

interface SettingRepository {

    fun changeLanguage()

    fun onDarkMode(isDarkMode: Boolean)
}