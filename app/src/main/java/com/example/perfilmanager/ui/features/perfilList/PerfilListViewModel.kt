package com.example.perfilmanager.ui.features.perfilList


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfilmanager.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilListViewModel(
    private val repository: PerfilRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PerfilListUiState>(PerfilListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadPerfis()
    }

    fun loadPerfis() {
        viewModelScope.launch {

            _uiState.value = PerfilListUiState.Loading

            repository.getAll().collect { perfis ->

                _uiState.value =
                    if (perfis.isEmpty()) {
                        PerfilListUiState.Empty
                    } else {
                        PerfilListUiState.Perfis(perfis)
                    }
            }
        }
    }

    fun onDeleteClick(id: Long){
        viewModelScope.launch {
            repository.deletePerfil(id)
        }
    }
}