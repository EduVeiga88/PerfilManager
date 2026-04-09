package com.example.perfilmanager.ui.features.criarPerfil


sealed class UiEvent{
    data class ShowSnackBar(val msg: String) : UiEvent()
    object NavigateBack : UiEvent()
}