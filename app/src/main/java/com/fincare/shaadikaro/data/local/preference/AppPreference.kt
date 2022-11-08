package com.fincare.shaadikaro.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val KEY_SAVED_AT = "key_saved_at"
private const val KEY_IS_FETCH_NEEDED = "key_is_fetch_needed"

class AppPreference @Inject constructor(@ApplicationContext context: Context) {

    private val appContext = context.applicationContext //Prevent memmory leaks

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setLastSavedAt(savedAt: String) {
        preference.edit().putString(
            KEY_SAVED_AT,
            savedAt
        ).apply()
    }

    fun getLastSavedAt(): String? {
        return preference.getString(KEY_SAVED_AT, null)
    }

    fun setIsFetchNeeded(isDone: Boolean) {
        preference.edit().putBoolean(
            KEY_IS_FETCH_NEEDED,
            isDone
        ).apply()
    }

    fun isFetchNeeded(): Boolean {
        return preference.getBoolean(KEY_IS_FETCH_NEEDED, true)
    }
    
}