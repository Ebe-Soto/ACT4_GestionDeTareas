package com.example.act4_dam

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog as MaterialDatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ---------------------------------------------------------
// PALETA DE COLORES (claro / oscuro)
// ---------------------------------------------------------

data class ColoresFormulario(
    val fondo: Color,
    val cafe: Color,
    val texto: Color,
    val borde: Color,
    val blanco: Color
)

private val ColoresFormularioClaro = ColoresFormulario(
    fondo = Color(0xFFF7F1E9),
    cafe = Color(0xFF795238),
    texto = Color(0xFF352A24),
    borde = Color(0xFFB8A99D),
    blanco = Color(0xFFFFFDFC)
)

private val ColoresFormularioOscuro = ColoresFormulario(
    fondo = Color(0xFF1C1512),
    cafe = Color(0xFFC8996B),
    texto = Color(0xFFF3E9DF),
    borde = Color(0xFF5C4A3D),
    blanco = Color(0xFF2A2119)
)

fun coloresFormulario(modoOscuro: Boolean): ColoresFormulario =
    if (modoOscuro) ColoresFormularioOscuro else ColoresFormularioClaro


// ---------------------------------------------------------
// AGREGAR EVENTO
// ---------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarEventoDialog(
    modoOscuro: Boolean,
    onDismiss: () -> Unit,
    onGuardar: (Evento) -> Unit
) {

    val colores = coloresFormulario(modoOscuro)

    var titulo by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    var mostrarCalendario by remember { mutableStateOf(false) }

    var errorTitulo by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.92f),
            shape = RoundedCornerShape(20.dp),
            color = colores.fondo
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {

                FormularioEvento(
                    colores = colores,

                    titulo = titulo,
                    onTituloChange = {
                        titulo = it
                        errorTitulo = false
                    },

                    fecha = fecha,
                    onFechaClick = {
                        mostrarCalendario = true
                    },

                    descripcion = descripcion,
                    onDescripcionChange = {
                        descripcion = it
                    },

                    errorTitulo = errorTitulo,
                    errorFecha = errorFecha,

                    onCancelar = onDismiss,

                    onGuardar = {

                        errorTitulo = titulo.isBlank()
                        errorFecha = fecha.isBlank()

                        if (titulo.isNotBlank() && fecha.isNotBlank()) {

                            val nuevoEvento = Evento(
                                id = System.currentTimeMillis(),
                                titulo = titulo,
                                fecha = fecha,
                                descripcion = descripcion,
                                categoria = ""
                            )

                            onGuardar(nuevoEvento)
                        }
                    }
                )
            } // cierra Column
        } // cierra Surface
    } // cierra Dialog


    // Calendario
    if (mostrarCalendario) {

        val datePickerState = rememberDatePickerState()

        MaterialDatePickerDialog(
            onDismissRequest = {
                mostrarCalendario = false
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        datePickerState.selectedDateMillis?.let { millis ->

                            fecha = convertirMillisAFecha(millis)
                            errorFecha = false

                        }

                        mostrarCalendario = false
                    }
                ) {
                    Text("Aceptar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarCalendario = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {

            DatePicker(
                state = datePickerState
            )
        }
    }
}


// ---------------------------------------------------------
// EDITAR EVENTO
// ---------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarEventoDialog(
    evento: Evento,
    modoOscuro: Boolean,
    onDismiss: () -> Unit,
    onGuardar: (Evento) -> Unit
) {

    val colores = coloresFormulario(modoOscuro)

    var titulo by remember(evento.id) {
        mutableStateOf(evento.titulo)
    }

    var fecha by remember(evento.id) {
        mutableStateOf(evento.fecha)
    }

    var descripcion by remember(evento.id) {
        mutableStateOf(evento.descripcion)
    }

    var mostrarCalendario by remember { mutableStateOf(false) }

    var errorTitulo by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }


    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.92f),
            shape = RoundedCornerShape(20.dp),
            color = colores.fondo
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {

                FormularioEvento(
                    colores = colores,

                    titulo = titulo,
                    onTituloChange = {
                        titulo = it
                        errorTitulo = false
                    },

                    fecha = fecha,
                    onFechaClick = {
                        mostrarCalendario = true
                    },

                    descripcion = descripcion,
                    onDescripcionChange = {
                        descripcion = it
                    },

                    errorTitulo = errorTitulo,
                    errorFecha = errorFecha,

                    onCancelar = onDismiss,

                    onGuardar = {

                        errorTitulo = titulo.isBlank()
                        errorFecha = fecha.isBlank()

                        if (titulo.isNotBlank() && fecha.isNotBlank()) {

                            val eventoActualizado = evento.copy(
                                titulo = titulo,
                                fecha = fecha,
                                descripcion = descripcion
                            )

                            onGuardar(eventoActualizado)
                        }
                    }
                )
            } // cierra Column
        } // cierra Surface
    } // cierra Dialog


    // Calendario para editar
    if (mostrarCalendario) {

        val fechaInicial = convertirFechaAMillis(fecha)

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = fechaInicial
        )

        MaterialDatePickerDialog(

            onDismissRequest = {
                mostrarCalendario = false
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        datePickerState.selectedDateMillis?.let { millis ->

                            fecha = convertirMillisAFecha(millis)
                            errorFecha = false

                        }

                        mostrarCalendario = false
                    }
                ) {
                    Text("Aceptar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarCalendario = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {

            DatePicker(
                state = datePickerState
            )
        }
    }
}


// ---------------------------------------------------------
// FORMULARIO COMPARTIDO
// ---------------------------------------------------------

@Composable
private fun FormularioEvento(
    colores: ColoresFormulario,

    titulo: String,
    onTituloChange: (String) -> Unit,

    fecha: String,
    onFechaClick: () -> Unit,

    descripcion: String,
    onDescripcionChange: (String) -> Unit,

    errorTitulo: Boolean,
    errorFecha: Boolean,

    onCancelar: () -> Unit,
    onGuardar: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        // ---------------------------------------------
        // ENCABEZADO
        // ---------------------------------------------

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.width(40.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("‹")
            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Text(
                text = "Lista de Eventos",
                color = colores.texto,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // ---------------------------------------------
        // TÍTULO
        // ---------------------------------------------

        Text(
            text = "Título del Evento",
            color = colores.texto
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        OutlinedTextField(

            value = titulo,

            onValueChange = onTituloChange,

            modifier = Modifier.fillMaxWidth(),

            singleLine = true,

            placeholder = {
                Text("Texto")
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Título"
                )
            },

            isError = errorTitulo,

            supportingText = {
                if (errorTitulo) {
                    Text("Ingresa un título")
                }
            },

            shape = RoundedCornerShape(8.dp),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colores.cafe,
                unfocusedBorderColor = colores.borde,
                focusedContainerColor = colores.blanco,
                unfocusedContainerColor = colores.blanco,
                focusedTextColor = colores.texto,
                unfocusedTextColor = colores.texto
            )
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // ---------------------------------------------
        // FECHA
        // ---------------------------------------------

        Text(
            text = "Fecha del Evento",
            color = colores.texto
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onFechaClick()
                }
        ) {

            OutlinedTextField(

                value = fecha,

                onValueChange = {},

                modifier = Modifier
                    .fillMaxWidth(),

                enabled = false,

                readOnly = true,

                singleLine = true,

                placeholder = {
                    Text("DD/MM/AAAA")
                },

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Fecha"
                    )
                },

                trailingIcon = {

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Seleccionar fecha"
                    )
                },

                isError = errorFecha,

                supportingText = {
                    if (errorFecha) {
                        Text("Selecciona una fecha")
                    }
                },

                shape = RoundedCornerShape(8.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colores.cafe,
                    unfocusedBorderColor = colores.borde,
                    focusedContainerColor = colores.blanco,
                    unfocusedContainerColor = colores.blanco,
                    disabledBorderColor = if (errorFecha) MaterialTheme.colorScheme.error else colores.borde,
                    disabledContainerColor = colores.blanco,
                    disabledTextColor = colores.texto,
                    disabledLeadingIconColor = colores.texto,
                    disabledTrailingIconColor = colores.texto,
                    disabledPlaceholderColor = colores.texto.copy(alpha = 0.6f),
                    disabledSupportingTextColor = MaterialTheme.colorScheme.error
                )
            )
        }


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // ---------------------------------------------
        // DESCRIPCIÓN
        // ---------------------------------------------

        Text(
            text = "Descripción",
            color = colores.texto
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )


        OutlinedTextField(

            value = descripcion,

            onValueChange = onDescripcionChange,

            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),

            placeholder = {
                Text("Texto....")
            },

            maxLines = 4,

            shape = RoundedCornerShape(8.dp),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colores.cafe,
                unfocusedBorderColor = colores.borde,
                focusedContainerColor = colores.blanco,
                unfocusedContainerColor = colores.blanco,
                focusedTextColor = colores.texto,
                unfocusedTextColor = colores.texto
            )
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // ---------------------------------------------
        // BOTONES
        // ---------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedButton(

                onClick = onCancelar,

                modifier = Modifier.weight(1f),

                shape = RoundedCornerShape(8.dp),

                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colores.texto
                )
            ) {

                Text("Cancelar")
            }


            Button(

                onClick = onGuardar,

                modifier = Modifier.weight(1f),

                shape = RoundedCornerShape(8.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = colores.cafe
                )
            ) {

                Text(
                    text = "Guardar",
                    color = Color.White
                )
            }
        }
    }
}


// ---------------------------------------------------------
// CONVERSIÓN DE FECHAS
// ---------------------------------------------------------

private fun convertirMillisAFecha(millis: Long): String {

    val formato = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    )

    return formato.format(Date(millis))
}


private fun convertirFechaAMillis(fecha: String): Long? {

    return try {

        val formato = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        )

        formato.parse(fecha)?.time

    } catch (e: Exception) {

        null
    }
}