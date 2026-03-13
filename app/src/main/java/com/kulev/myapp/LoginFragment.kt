package com.kulev.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val etLogin = view.findViewById<EditText>(R.id.etLogin)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val cbAuto = view.findViewById<CheckBox>(R.id.cbAutoLogin)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        val session = SessionManager(requireContext())

        session.getLogin()?.let { etLogin.setText(it) }

        btnLogin.setOnClickListener {
            val login = etLogin.text.toString().trim()
            val password = etPassword.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Введите email и пароль", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(login, password)
                .addOnSuccessListener {
                    session.saveUser(login, password, cbAuto.isChecked)
                    findNavController().navigate(R.id.action_loginFragment_to_firstFragment)
                }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        it.localizedMessage ?: "Ошибка входа",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}