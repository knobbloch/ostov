package com.knobblochsapplication.app.appcomponents.utility

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp

/**
 * class which used to manage application shared preference
 */
class PreferenceHelper() {
    private val masterKeyAlias: String = createGetMasterKey()

    private val sharedPreference: SharedPreferences = EncryptedSharedPreferences.create(
        MyApp.getInstance().resources.getString(R.string.app_name),
        masterKeyAlias,
        MyApp.getInstance().applicationContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Creates or gets the master key provided,
     * The encryption scheme is required fields to ensure that the type of encryption used is clear to developers.
     *
     * @return the string value of encrypted key
     */
    private fun createGetMasterKey(): String {
        return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    private val DARK_THEME = "dark_theme"

    fun isDarkTheme() =
        sharedPreference.getBoolean(DARK_THEME, false)

    fun setDarkTheme(isDark: Boolean) {
        sharedPreference
            .edit()
            .putBoolean(DARK_THEME, isDark)
            .apply()
    }

    private val IS_HELP_PAGE_SHOWED = "help_page_is_showed"

    fun isHelpPageShowed() =
        sharedPreference.getBoolean(IS_HELP_PAGE_SHOWED, false)

    fun setHelpPageShowed(isShowed: Boolean) {
        sharedPreference
            .edit()
            .putBoolean(IS_HELP_PAGE_SHOWED, isShowed)
            .apply()
    }

    private val LAST_SELECTED_GOAL = "last_selected_goal"

    fun getLastSelectedGoal() =
        sharedPreference.getString(LAST_SELECTED_GOAL, null)

    fun setLastSelectedGoal(lastSelectedGoal: String?) {
        sharedPreference
            .edit()
            .putString(LAST_SELECTED_GOAL, lastSelectedGoal)
            .apply()
    }

    private val DIAGRAM_VIEW_TYPE = "diagram_view_type"

    fun isDiagramSelected() =
        sharedPreference.getBoolean(DIAGRAM_VIEW_TYPE, true)

    fun setDiagramSelected(isDiagramSelected: Boolean) {
        sharedPreference
            .edit()
            .putBoolean(DIAGRAM_VIEW_TYPE, isDiagramSelected)
            .apply()
    }

    private val SORT_TYPE = "sort_type"

    fun getSortType() =
        sharedPreference.getString(SORT_TYPE, SortType.BY_POSITION)

    fun setSortType(sortType: String) {
        sharedPreference
            .edit()
            .putString(SORT_TYPE, sortType)
            .apply()
    }

    private val NOTIFICATIONS = "notifications"

    fun getIsNotificationEnabled() =
        sharedPreference.getBoolean(NOTIFICATIONS, false)

    fun setIsNotificationEnabled(notifications: Boolean) {
        sharedPreference
            .edit()
            .putBoolean(NOTIFICATIONS, notifications)
            .apply()
    }
}