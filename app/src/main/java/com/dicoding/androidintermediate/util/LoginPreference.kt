package com.dicoding.androidintermediate.util

import android.content.Context
import com.dicoding.androidintermediate.request.UserSession

class LoginPreference (context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(user: UserSession) {
        val editor = preferences.edit()
        editor.putString(NAME, user.name)
        editor.putString(TOKEN, user.token)
        editor.putString(USER_ID, user.userId)
        editor.putBoolean(LOGIN_STATE, user.isLogin)
        editor.apply()
    }

    fun logout() {
        val editor = preferences.edit()
        editor.remove(NAME)
        editor.remove(TOKEN)
        editor.putBoolean(LOGIN_STATE, false)
        editor.apply()
    }

    fun getUser(): UserSession {
        return UserSession(
            preferences.getString(NAME, "") ?: "",
            preferences.getString(TOKEN, "")?: "",
            preferences.getString(USER_ID, "")?: "",
            preferences.getBoolean(LOGIN_STATE, false)
        )
    }

    fun getString(key: String, default: String = ""): String = preferences.getString(key, "") ?: default
}