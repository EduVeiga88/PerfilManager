package com.example.perfilmanager.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.perfilmanager.domain.model.Perfil
import com.example.perfilmanager.ui.theme.PerfilManagerTheme


@Composable
fun PerfilItem(
    perfil: Perfil,
    onEditClick:(id:Long) -> Unit,
    onDeleteClick:(id:Long) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(2.dp,Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Text(
                "ID#${perfil.id}",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    perfil.username,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable(onClick ={onEditClick(perfil.id)} ),
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar"
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable(onClick = {onDeleteClick( perfil.id)}),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar"
                )
            }
        }
    }
}

@Preview
@Composable
private fun PerfilItemPreview() {
    PerfilManagerTheme {
        PerfilItem(
            perfil = Perfil(
                id = 0,
                name = "Pedro",
                username = "Pedrito21",
                password = ""
            ),
            onEditClick = {},
            onDeleteClick = {}
        )
    }

}