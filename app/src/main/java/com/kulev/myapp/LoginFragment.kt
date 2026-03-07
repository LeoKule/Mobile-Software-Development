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

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etLogin = view.findViewById<EditText>(R.id.etLogin)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val cbAuto = view.findViewById<CheckBox>(R.id.cbAutoLogin)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        val session = SessionManager(requireContext())

        btnLogin.setOnClickListener {
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

            if (login == session.getLogin() && password == session.getPassword()) {
                session.saveUser(login, password, cbAuto.isChecked)
                findNavController().navigate(R.id.action_loginFragment_to_firstFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Неверный логин или пароль",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}