package com.example.study_hub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {

        if (name.isBlank()) {
            _message.value = "Please enter your full name."
            return
        }

        if (email.isBlank()) {
            _message.value = "Please enter your email."
            return
        }

        if (password.length < 6) {
            _message.value = "Password must be at least 6 characters."
            return
        }

        if (password != confirmPassword) {
            _message.value = "Passwords do not match."
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _message.value = ""

            try {

                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }

                _message.value = "Account created successfully!"

            } catch (e: Exception) {

                _message.value = e.message ?: "Registration failed."

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        if (email.isBlank()) {
            _message.value = "Please enter your email."
            return
        }

        if (password.isBlank()) {
            _message.value = "Please enter your password."
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _message.value = ""

            try {

                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }

                _message.value = "Login successful!"

                onSuccess()

            } catch (e: Exception) {

                _message.value = e.message ?: "Login failed."

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _message.value = ""
    }
}