package com.example.perfilmanager.ui.features.login

data class LoginUiState(
    val id: Long = 0,
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val errorMessage: String? = "",
    val success: Boolean = false
)