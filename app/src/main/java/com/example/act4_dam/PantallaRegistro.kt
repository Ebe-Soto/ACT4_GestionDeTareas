package com.example.act4_dam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun PantallaRegistro (
    onCrearCuentaClick: (
            nombre: String,
            correo: String,
            contrasena: String
            ) -> Unit
) {
    var nombre by remember {
        mutableStateOf("")
    }

    var correo by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var aceptarTerminos by remember {
        mutableStateOf(false)
    }

    var errorCorreo by remember {
        mutableStateOf<String?>(null) }
    var errorContrasena by remember {
        mutableStateOf<String?>(null) }
    var errorTerminos by remember {
        mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box (
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("✦",
                    color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.width(10.dp))
            Text(
                "Café Lista de Tareas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height((28.dp)))

        Text(
            "¡Crea tu cuenta!",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(6.dp))

        Text(
            "Tus próximos eventos empiezan con un espacio organizado.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        Text("Nombre completo", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text("Tu nombre")},
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(18.dp))

        Text("Correo electrónico", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                errorCorreo = null
            },
            placeholder = { Text("nombre@gmail.com") },
            isError = errorCorreo != null,
            supportingText = {
                Text(errorCorreo ?: "Debe ser un correo @gmail.com")
            },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Debe ser un correo@tecmilenio.mx",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(Modifier.height(14.dp))

        Text("Contraseña", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorContrasena = null
            },
            placeholder = { Text("••••••••") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = errorContrasena != null,
            supportingText = {
                Text(errorContrasena ?: "Mínimo 8 caracteres")
            },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "8+ caracteres, número y símbolo",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(Modifier.height(18.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = aceptarTerminos,
                onCheckedChange = {
                    aceptarTerminos = it
                    errorTerminos = null
                },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )
            Text(
                "Acepto los términos y condiciones para crear y asignar tareas",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        if (errorTerminos != null) {
            Text(
                errorTerminos!!,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                errorCorreo = if (!esCorreoValido(correo)) "Correo inválido, debe ser @gmail.com" else null
                errorContrasena = if (!esContrasenaValida(password)) "Debe tener al menos 8 caracteres" else null
                errorTerminos = if (!aceptarTerminos) "Debes aceptar los términos y condiciones" else null

                if (errorCorreo == null && errorContrasena == null && errorTerminos == null) {
                    onCrearCuentaClick(nombre, correo, password)
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Crear cuenta", fontSize = 16.sp)
        }
        Text(
            "Al continuar aceptas la Política de privacidad.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 16.dp)
        )

    }

}


fun esCorreoValido(correo: String): Boolean {
    val patronCorreo = Regex("^[A-Za-z0-9._%+-]+@gmail\\.com$")
    return patronCorreo.matches(correo.trim())
}

fun esContrasenaValida(contrasena: String): Boolean {
    return contrasena.length >= 8
}

