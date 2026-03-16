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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private var isEmailMode = true
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuthProvider.getAuth(requireContext())

        val tvByPhone = view.findViewById<TextView>(R.id.tvByPhone)
        val tvByEmail = view.findViewById<TextView>(R.id.tvByEmail)

        val etLogin = view.findViewById<EditText>(R.id.etLogin)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etRepeatPassword = view.findViewById<EditText>(R.id.etRepeatPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        val activeColor = Color.parseColor("#7B3FE4")
        val inactiveColor = Color.parseColor("#9E9E9E")

        btnRegister.setOnClickListener {
            handleRegistration(
                login = etLogin.text.toString().trim(),
                password = etPassword.text.toString(),
                repeatPassword = etRepeatPassword.text.toString()
            )
        }

        tvByEmail.setOnClickListener { setEmailMode(tvByEmail, tvByPhone, etLogin, activeColor, inactiveColor) }
        tvByPhone.setOnClickListener { setPhoneMode(tvByPhone, tvByEmail, etLogin, activeColor, inactiveColor) }
    }

        private fun handleRegistration(login: String, password: String, repeatPassword: String) {
            validateInput(login, password, repeatPassword)?.let {
                showShortToast(it)
                return
            }

                auth.createUserWithEmailAndPassword(login, password)
                    .addOnSuccessListener {
                        val session = SessionManager(requireContext())
                        session.saveUser(login, password, false)
                        findNavController().navigate(R.id.action_registerFragment_to_firstFragment)
                    }
                    .addOnFailureListener { error ->
                        showShortToast(FirebaseAuthErrorMapper.mapRegistrationError(error))
                    }
            }

            private fun validateInput(login: String, password: String, repeatPassword: String): String? {
                if (!isEmailMode) return "Для Firebase используйте регистрацию по email"
                if (!EMAIL_REGEX.matches(login)) return "Email должен содержать символ @"
                if (password.length < MIN_PASSWORD_LENGTH) return "Пароль должен содержать минимум 8 символов"
                if (password != repeatPassword) return "Пароль и подтверждение не совпадают"
                return null
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
                editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
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

        companion object {
            private val EMAIL_REGEX = Regex("^.+@.+$")
            private const val MIN_PASSWORD_LENGTH = 8
        }
}