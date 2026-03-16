package com.kulev.myapp

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showShortToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}