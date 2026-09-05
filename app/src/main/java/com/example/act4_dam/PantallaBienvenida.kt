package com.example.act4_dam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

@Composable
fun PantallaBienvenida(
    onComenzarClick: () -> Unit
) {
    //Acomodamos el contenido de arriba hacia abajo, uno debajo del otro
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp, 40.dp)
    ) {
        // Creacion del encabezado y nombre de la app
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono Visual
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
            Spacer(Modifier.width(10.dp)) // Espacio entre el icono y el texto/titulo
            // Titulo
            Text(
                "Café Lista de Tareas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        // Imagen central de la pantalla
        Box(
            modifier = Modifier
                .size(500.dp)
                .clip(CircleShape), // Recorta la imagen en forma de circulo
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.circulo), // Llamamos al recurso ubicado el drawables
                contentDescription = null,
                contentScale = ContentScale.Crop, // Recorta la imagen para llenar el espacio sin deformarse
                modifier = Modifier
                    .size(400.dp)
                    .fillMaxSize()

            )
        }

        // Titulo central
        Text(
            "Organiza cada evento con calma",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Subtitulo descriptivo
        Text(
            "Crea tareas, reúne los detalles y llega a tiempo a cada momento importante.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer((Modifier.height(24.dp)))

        // Diseño de puntos
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(Modifier.size(20.dp, 6.dp).background(MaterialTheme.colorScheme.primary,
                RoundedCornerShape(3.dp)))
        }

        Spacer(Modifier.height(24.dp))

        // Boton de Comenzar
        Button( // Al hacer click, ejecuta la funcion que se le asigno al inicio, esta se define en MainActiviy
            onClick = onComenzarClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Comenzar", fontSize = 16.sp)
        }

    }

}