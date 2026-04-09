package com.example.perfilmanager.ui.features.perfilList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perfilmanager.data.appDatabase.AppDatabaseProvider
import com.example.perfilmanager.data.repositoryImpl.PerfilRepositoryImpl
import com.example.perfilmanager.domain.model.Perfil
import com.example.perfilmanager.ui.components.PerfilItem
import com.example.perfilmanager.ui.theme.PerfilManagerTheme

@Composable
fun PerfilListScreen(
    onNavigate:(Long) -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = AppDatabaseProvider.getDatabase(context)
    val repository = PerfilRepositoryImpl(
        dao = database.perfilDao()
    )
    val viewModel = viewModel<PerfilListViewModel>{
        PerfilListViewModel(
            repository = repository
        )
    }

    val uiState by viewModel.uiState.collectAsState()

    when(val state = uiState){
        PerfilListUiState.Loading ->{
            Text("Loading")
        }
        PerfilListUiState.Empty ->{
            Text("Lista vazia")
        }
        is PerfilListUiState.Perfis ->{
            PerfilListContent(
                perfis = state.perfis,
                onEditClick = {id -> onNavigate(id)},
                onDeleteClick = {id -> viewModel.onDeleteClick(id)}
            )
        }
    }
}

@Composable
fun PerfilListContent(
    perfis: List<Perfil>,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val perfisFiltrados = perfis.filter {
        it.name.contains(query, ignoreCase = true)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Pesquisar...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn {
                items(perfisFiltrados) { perfil ->
                    PerfilItem(
                        perfil = perfil,
                        onEditClick = { onEditClick(perfil.id) },
                        onDeleteClick = { onDeleteClick(perfil.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PerfilListPreview() {
    PerfilManagerTheme {
        PerfilListContent(
            perfis = listOf(Perfil(0, "Pedro", "Pedrito21", "")),
            onEditClick = {},
            onDeleteClick = {},
        )
    }

}