package com.capstone.fresheats

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.capstone.fresheats.network.ApiConfig

class Preferences : MultiDexApplication() {
    companion object {
        lateinit var instance: Preferences

        fun getApp(): Preferences {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun getPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(this)
    }

    fun setToken(token: String) {
        getPreferences().edit().putString("PREFERENCES_TOKEN", token).apply()
        ApiConfig.getInstance().buildRetrofitClient(token)
    }

    fun getToken(): String? {
        return getPreferences().getString("PREFERENCES_TOKEN", null)
    }

    fun setUser(user: String) {
        getPreferences().edit().putString("PREFERENCES_USER", user).apply()
    }

    fun getUser(): String? {
        return getPreferences().getString("PREFERENCES_USER", null)
    }
}
