package com.kulev.myapp

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1500)

            val session = SessionManager(requireContext())
            val auth = FirebaseAuth.getInstance()

            val destinationId = when {
                auth.currentUser != null && session.isAutoLogin() -> R.id.firstFragment
                session.hasUser() -> {
                    if (auth.currentUser != null) auth.signOut()
                    R.id.loginFragment
                }
                else -> R.id.registerFragment
            }

            findNavController().navigate(
                destinationId,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true)
                    .build()
            )
        }


    }
}