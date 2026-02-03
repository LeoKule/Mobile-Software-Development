package com.kulev.myapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    // Флаг текущего режима регистрации
    private var isEmailMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        // Привязка элементов интерфейса
        val tvByPhone = findViewById<TextView>(R.id.tvByPhone)
        val tvByEmail = findViewById<TextView>(R.id.tvByEmail)

        // Получаем элементы интерфейса
        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etRepeatPassword = findViewById<EditText>(R.id.etRepeatPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Цвета
        val activeColor = Color.parseColor("#7B3FE4")
        val inactiveColor = Color.parseColor("#9E9E9E")

        // При нажатии кнопки регистрации выполняем проверки:
        btnRegister.setOnClickListener {

            val login = etLogin.text.toString()
            val password = etPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()

            // Проверка email
            if (isEmailMode && !Regex("^.+@.+$").matches(login)) {
                Toast.makeText(
                    this,
                    "Email должен содержать символ @",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Проверка телефона
            if (!isEmailMode && !Regex("^\\+\\d{11}$").matches(login)) {
                Toast.makeText(
                    this,
                    "Номер телефона должен начинаться с + и содержать 11 цифр",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Проверка длины пароля
            if (password.length < 8) {
                Toast.makeText(
                    this,
                    "Пароль должен содержать минимум 8 символов",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Проверка совпадения паролей
            if (password != repeatPassword) {
                Toast.makeText(
                    this,
                    "Пароль и подтверждение не совпадают",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Если все проверки пройдены — сохраняем данные
            val session = SessionManager(this)
            session.saveUser(login, password, false)

            // Переходим на ContentActivity
            startActivity(Intent(this, ContentActivity::class.java))
            finish()

        }


        // Режим По email по умолчанию
        setEmailMode(tvByEmail, tvByPhone, etLogin, activeColor, inactiveColor)

        // переключение режимов
        tvByEmail.setOnClickListener {
            setEmailMode(tvByEmail, tvByPhone, etLogin, activeColor, inactiveColor)
        }

        tvByPhone.setOnClickListener {
            setPhoneMode(tvByPhone, tvByEmail, etLogin, activeColor, inactiveColor)
        }
    }



    private fun setEmailMode(
        active: TextView,
        inactive: TextView,
        editText: EditText,
        activeColor: Int,
        inactiveColor: Int
    ) {
        isEmailMode = true

        active.setTextColor(activeColor)
        inactive.setTextColor(inactiveColor)

        editText.hint = "Введите Email"
        editText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        editText.text.clear()
    }

    private fun setPhoneMode(
        active: TextView,
        inactive: TextView,
        editText: EditText,
        activeColor: Int,
        inactiveColor: Int
    ) {
        isEmailMode = false

        active.setTextColor(activeColor)
        inactive.setTextColor(inactiveColor)

        editText.hint = "Введите номер телефона"
        editText.inputType = InputType.TYPE_CLASS_PHONE
        editText.text.clear()
    }
}