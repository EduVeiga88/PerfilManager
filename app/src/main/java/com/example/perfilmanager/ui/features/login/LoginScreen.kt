package com.example.perfilmanager.ui.features.login


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun LoginScreen(
    onNavigateTo:() -> Unit,
    onTextClick: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = AppDatabaseProvider.getDatabase(context)
    val repository = PerfilRepositoryImpl(
        dao = database.perfilDao()
    )
    val viewModel = viewModel<LoginViewModel>{
        LoginViewModel(
            repository = repository
        )
    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.success) {
        if(uiState.success){
            onNavigateTo()
        }
    }

    LoginContent(
        perfil = Perfil(
            id = uiState.id,
            username = uiState.username,
            password = uiState.password,
            name = uiState.name
        ),
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::onLoginClick,
        onTextClick = onTextClick
    )


}

@Composable
fun LoginContent(
    perfil: Perfil,
    onUsernameChange:(username: String)-> Unit,
    onPasswordChange:(password: String)-> Unit,
    onLoginClick:()-> Unit,
    onTextClick:()-> Unit
) {
    Scaffold(
        modifier = Modifier
            .padding(4.dp),
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .imePadding(),
                onClick = onLoginClick
            ) {
                Text("Login")
            }
        }

    ) {paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = perfil.username,
                onValueChange = onUsernameChange,
                label = { Text("Username") },
                placeholder = {Text("Username")}
            )

            OutlinedTextField(
                value = perfil.password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                placeholder = {Text("Password")}
            )

            TextButton(
                onClick = onTextClick
            ) {
                Text("Click aqui - Se não tiver conta!")
            }
        }
    }
}

@Preview
@Composable
private fun LoginPrev() {
    PerfilManagerTheme {
        LoginContent(
            perfil = Perfil(
                id = 1,
                username = "pedro",
                password = "pedrito123",
                name = ""
            ),
            onUsernameChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onTextClick = {}
        )
    }
}