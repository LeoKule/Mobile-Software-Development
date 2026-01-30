package com.kulev.myapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Создаём менеджер сессии

        val session = SessionManager(this)

        // Задержка, чтобы показать ProgressBar

        Handler(Looper.getMainLooper()).postDelayed({

            when {
                // Если есть данные и включён автологин

                session.hasUser() && session.isAutoLogin() -> {
                    startActivity(Intent(this, ContentActivity::class.java))
                }

                // Если данные есть, но автологин выключен

                session.hasUser() -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }

                // Если данных нет — регистрация

                else -> {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }
            }

            finish()
        }, 1500)
    }
}
