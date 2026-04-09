package com.example.perfilmanager.ui.features.criarPerfil

import kotlin.collections.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfilmanager.domain.model.Perfil
import com.example.perfilmanager.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CriarPerfilViewModel(
    private val repository: PerfilRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CriarPerfilUiState())
    val uiState: StateFlow<CriarPerfilUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onNameChange(name: String) {
        _uiState.update { state ->
            state.copy(
                name = name
            )
        }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { state ->
            state.copy(
                username = username
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { state ->
            state.copy(
                password = password
            )
        }
    }

    fun loadPerfil(id: Long) {
        viewModelScope.launch {
            val perfil = repository.getById(id) ?: return@launch

            _uiState.update { state ->
                state.copy(
                    id = perfil.id,
                    name = perfil.name,
                    username = perfil.username,
                    password = perfil.password
                )
            }
        }
    }

    fun onCriarPerfilClick() {
        val state = _uiState.value

        if (
            state.name.isBlank() ||
            state.username.isBlank() ||
            state.password.isBlank()
        ) {
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.ShowSnackBar("Preenche todos os campos"))
            }
            return
        }

        viewModelScope.launch {
            val usernameExistente = repository.getAll().first().any { perfil ->
                perfil.username == state.username && perfil.id != state.id
            }

            if (usernameExistente) {
                _uiEvent.emit(UiEvent.ShowSnackBar("Username já existe"))
                return@launch
            }

            val perfil = Perfil(
                id = state.id ?: 0,
                name = state.name,
                username = state.username,
                password = state.password
            )

            if (state.id == null) {
                repository.insertPerfil(perfil)
                _uiEvent.emit(UiEvent.ShowSnackBar("Perfil criado com sucesso"))
            } else {
                repository.updatePerfil(perfil)
                _uiEvent.emit(UiEvent.ShowSnackBar("Perfil atualizado com sucesso"))
            }

            _uiEvent.emit(UiEvent.NavigateBack)
        }
    }
}