package com.kulev.myapp

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

object FirebaseAuthErrorMapper {

    fun mapLoginError(error: Exception): String {
        val errorCode = (error as? FirebaseAuthException)?.errorCode.orEmpty()
        val errorText = "$errorCode ${error.localizedMessage.orEmpty()}".uppercase()

        return when {
            error is FirebaseAuthInvalidUserException -> "Пользователь не найден"
            error is FirebaseAuthInvalidCredentialsException -> "Неверный email или пароль"
            errorText.contains(INVALID_LOGIN_CREDENTIALS) -> "Неверный email или пароль"
            errorText.contains(WRONG_PASSWORD) -> "Неверный email или пароль"
            errorText.contains(USER_NOT_FOUND) -> "Пользователь не найден"
            errorText.contains(TOO_MANY_REQUESTS) -> "Слишком много попыток. Попробуйте позже"
            else -> error.localizedMessage ?: "Ошибка входа"
        }
    }

    fun mapRegistrationError(error: Exception): String {
        val errorCode = (error as? FirebaseAuthException)?.errorCode.orEmpty()
        val errorText = "$errorCode ${error.localizedMessage.orEmpty()}".uppercase()

        return when {
            error is FirebaseAuthUserCollisionException -> "Пользователь с таким email уже существует"
            error is FirebaseAuthWeakPasswordException -> "Слишком слабый пароль"
            error is FirebaseAuthInvalidCredentialsException -> "Некорректный email"
            errorText.contains(INVALID_EMAIL) -> "Некорректный email"
            else -> error.localizedMessage ?: "Ошибка регистрации"
        }
    }

    private const val INVALID_LOGIN_CREDENTIALS = "INVALID_LOGIN_CREDENTIALS"
    private const val WRONG_PASSWORD = "WRONG_PASSWORD"
    private const val USER_NOT_FOUND = "USER_NOT_FOUND"
    private const val TOO_MANY_REQUESTS = "TOO_MANY_REQUESTS"
    private const val INVALID_EMAIL = "INVALID_EMAIL"
}