package com.fincare.shaadikaro.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.fincare.shaadikaro.data.network.models.collection.matches.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val KEY_CITY = "city"
private const val KEY_STATE = "state"

class AppPreference @Inject constructor(@ApplicationContext context: Context) {

    private val appContext = context.applicationContext //Prevent memmory leaks

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setLocation(location: Location){
        val editor = preference.edit()
        editor.putString(KEY_CITY,location.city!!.toString())
        editor.putString(KEY_STATE,location.state!!.toString())
        editor.apply()
    }

    fun getLocation() : Location {
        val location = Location()
        location.city = preference.getString(KEY_CITY,null)
        location.state = preference.getString(KEY_STATE,null)
        return location
    }

    fun clearLocation(){
        val editor = preference.edit()
        editor.clear()
        editor.apply()
    }
}