package com.example.perfilmanager.ui.features.perfilList

import com.example.perfilmanager.domain.model.Perfil

sealed class PerfilListUiState {
    object Loading : PerfilListUiState()
    object Empty : PerfilListUiState()
    data class Perfis(val perfis:List<Perfil>) : PerfilListUiState()
}