package com.example.perfilmanager.ui.features.criarPerfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perfilmanager.data.appDatabase.AppDatabaseProvider
import com.example.perfilmanager.data.repositoryImpl.PerfilRepositoryImpl
import com.example.perfilmanager.domain.model.Perfil
import com.example.perfilmanager.ui.theme.PerfilManagerTheme

@Composable
fun CriarPerfilScreen(
    perfilId: Long? = null
) {

    val context = LocalContext.current.applicationContext
    val database = AppDatabaseProvider.getDatabase(context)
    val repository = PerfilRepositoryImpl(
        dao = database.perfilDao()
    )
    val viewModel = viewModel<CriarPerfilViewModel>{
        CriarPerfilViewModel(repository = repository)
    }

    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.msg)
                }

                UiEvent.NavigateBack -> Unit
            }
        }
    }

    LaunchedEffect(perfilId) {
        if (perfilId != null) {
            viewModel.loadPerfil(perfilId)
        }
    }

    CriarPerfilContent(
        perfil = Perfil(
            id = uiState.id?:0,
            name = uiState.name,
            username = uiState.username,
            password = uiState.password
        ),
        snackbarHostState = snackbarHostState,
        onNameChange = viewModel::onNameChange,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onCriarPerfilClick = viewModel::onCriarPerfilClick
    )
}

@Composable
fun CriarPerfilContent(
    perfil: Perfil,
    snackbarHostState: SnackbarHostState,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCriarPerfilClick: () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .imePadding(),
                onClick = onCriarPerfilClick
            ) {
                Text("Criar perfil")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = perfil.name,
                onValueChange = onNameChange,
                label = { Text("Name") },
                placeholder = { Text("Name") }
            )

            OutlinedTextField(
                value = perfil.username,
                onValueChange = onUsernameChange,
                label = { Text("Username") },
                placeholder = { Text("Username") }
            )

            OutlinedTextField(
                value = perfil.password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                placeholder = { Text("Password") }
            )
        }
    }
}

@Preview
@Composable
private fun CriarPerfilPreview() {
    PerfilManagerTheme {
        CriarPerfilContent(
            perfil = Perfil(
                id = 0,
                name = "Pedro",
                username = "pedrito",
                password = "pedrito1234"
            ),
            onNameChange = {},
            onUsernameChange = {},
            onPasswordChange = {},
            onCriarPerfilClick = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}