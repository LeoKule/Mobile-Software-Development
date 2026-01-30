package com.kulev.myapp

import android.content.Context

class SessionManager(context: Context) {

    // Получаем SharedPreferences с именем user_prefs
    private val prefs =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Сохраняем логин, пароль и флаг автологина
    fun saveUser(login: String, password: String, autoLogin: Boolean) {
        prefs.edit()
            .putString("login", login)
            .putString("password", password)
            .putBoolean("auto_login", autoLogin)
            .apply()
    }

    fun getLogin(): String? = prefs.getString("login", null)
    fun getPassword(): String? = prefs.getString("password", null)
    fun isAutoLogin(): Boolean = prefs.getBoolean("auto_login", false)

    // Проверяем, есть ли сохранённый пользователь
    fun hasUser(): Boolean =
        getLogin() != null && getPassword() != null
}
