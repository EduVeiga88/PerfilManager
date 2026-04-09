package com.example.perfilmanager.ui.features.login

import kotlin.collections.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfilmanager.domain.model.Perfil
import com.example.perfilmanager.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: PerfilRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String){
        _uiState.update { state ->
            state.copy(
                username = username
            )
        }
    }

    fun onPasswordChange(password: String){
        _uiState.update { state ->
            state.copy(
                password = password
            )
        }
    }

    fun onLoginClick() {
        val state = _uiState.value

        if (state.username.isBlank() || state.password.isBlank()) {
            _uiState.update {
                it.copy(
                    success = false,
                    errorMessage = "Preenche todos os campos"
                )
            }
            return
        }

        viewModelScope.launch {
            val perfis = repository.getAll().first()

            val perfilEncontrado = perfis.any { perfil ->
                state.username == perfil.username &&
                        state.password == perfil.password
            }

            if (perfilEncontrado) {
                _uiState.update {
                    it.copy(
                        success = true,
                        errorMessage = null
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        success = false,
                        errorMessage = "Login inválido"
                    )
                }
            }
        }
    }
}