package com.kulev.myapp

import android.content.Context

class SessionManager(context: Context) {
    private val prefs =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Сохраняем логин, пароль и флаг автологина
    fun saveUser(login: String, password: String, autoLogin: Boolean) {
        prefs.edit()
            .putString(KEY_LOGIN, login)
            .putString(KEY_PASSWORD, password)
            .putBoolean(KEY_AUTO_LOGIN, autoLogin)
            .apply()
    }

    fun getLogin(): String? = prefs.getString(KEY_LOGIN, null)
    fun getPassword(): String? = prefs.getString(KEY_PASSWORD, null)
    fun isAutoLogin(): Boolean = prefs.getBoolean(KEY_AUTO_LOGIN, false)

    // Проверяем, есть ли сохранённый пользователь
    fun hasUser(): Boolean =
        getLogin() != null && getPassword() != null

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_LOGIN = "login"
        private const val KEY_PASSWORD = "password"
        private const val KEY_AUTO_LOGIN = "auto_login"
    }
}

