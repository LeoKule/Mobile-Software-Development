package com.kulev.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
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

        auth = FirebaseAuthProvider.getAuth(requireContext())

        val etLogin = view.findViewById<EditText>(R.id.etLogin)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val cbAuto = view.findViewById<CheckBox>(R.id.cbAutoLogin)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        val session = SessionManager(requireContext())

        session.getLogin()?.let(etLogin::setText)

        btnLogin.setOnClickListener {
            handleLogin(
                login = etLogin.text.toString().trim(),
                password = etPassword.text.toString(),
                autoLogin = cbAuto.isChecked,
                session = session
            )
        }
    }

    private fun handleLogin(
        login: String,
        password: String,
        autoLogin: Boolean,
        session: SessionManager
    ) {
        validateInput(login, password)?.let {
            showShortToast(it)
            return
        }

        auth.signInWithEmailAndPassword(login, password)
            .addOnSuccessListener {
                session.saveUser(login, password, autoLogin)
                findNavController().navigate(R.id.action_loginFragment_to_firstFragment)
    }
            .addOnFailureListener { error ->
                showShortToast(FirebaseAuthErrorMapper.mapLoginError(error))
            }
    }

    private fun validateInput(login: String, password: String): String? {
        if (login.isEmpty() || password.isEmpty()) {
            return "Введите email и пароль"
        }
        return null
    }

}