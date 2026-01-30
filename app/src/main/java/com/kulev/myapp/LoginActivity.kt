package com.kulev.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbAuto = findViewById<CheckBox>(R.id.cbAutoLogin)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val session = SessionManager(this)

        btnLogin.setOnClickListener {

            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

            // Сравниваем введённые данные с сохранёнными
            if (login == session.getLogin() &&
                password == session.getPassword()
            ) {

                // Сохраняем флаг автологина
                session.saveUser(login, password, cbAuto.isChecked)

                // Переход на ContentActivity
                startActivity(Intent(this, ContentActivity::class.java))
                finish()

            } else {
                // Если данные не совпали — показываем ошибку
                Toast.makeText(
                    this,
                    "Неверный логин или пароль",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}