package com.kulev.myapp

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment() {
    private var isEmailMode = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvByPhone = view.findViewById<TextView>(R.id.tvByPhone)
        val tvByEmail = view.findViewById<TextView>(R.id.tvByEmail)

        val etLogin = view.findViewById<EditText>(R.id.etLogin)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etRepeatPassword = view.findViewById<EditText>(R.id.etRepeatPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        val activeColor = Color.parseColor("#7B3FE4")
        val inactiveColor = Color.parseColor("#9E9E9E")

        btnRegister.setOnClickListener {
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()

            if (isEmailMode && !Regex("^.+@.+$").matches(login)) {
                Toast.makeText(
                    requireContext(),
                    "Email должен содержать символ @",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!isEmailMode && !Regex("^\\+\\d{11}$").matches(login)) {
                Toast.makeText(
                    requireContext(),
                    "Номер телефона должен начинаться с + и содержать 11 цифр",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password.length < 8) {
                Toast.makeText(
                    requireContext(),
                    "Пароль должен содержать минимум 8 символов",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(
                    requireContext(),
                    "Пароль и подтверждение не совпадают",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val session = SessionManager(requireContext())
            session.saveUser(login, password, false)
            findNavController().navigate(R.id.action_registerFragment_to_firstFragment)
        }

        setEmailMode(tvByEmail, tvByPhone, etLogin, activeColor, inactiveColor)

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