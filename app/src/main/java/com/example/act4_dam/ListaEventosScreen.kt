package com.example.act4_dam

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

// ---------------------------------------------------------
// PALETA DE COLORES (claro / oscuro)
// ---------------------------------------------------------

data class ColoresEventos(
    val fondo: Color,
    val cafe: Color,
    val cafeOscuro: Color,
    val cafeClaro: Color,
    val textoSecundario: Color,
    val blanco: Color
)

private val ColoresClaro = ColoresEventos(
    fondo = Color(0xFFF7F1E9),
    cafe = Color(0xFF795238),
    cafeOscuro = Color(0xFF352A24),
    cafeClaro = Color(0xFFEBDCCB),
    textoSecundario = Color(0xFF806F62),
    blanco = Color(0xFFFFFDFC)
)

private val ColoresOscuro = ColoresEventos(
    fondo = Color(0xFF1C1512),
    cafe = Color(0xFFC8996B),
    cafeOscuro = Color(0xFFF3E9DF),
    cafeClaro = Color(0xFF3A2E27),
    textoSecundario = Color(0xFFB8A99D),
    blanco = Color(0xFF2A2119)
)

fun coloresEventos(modoOscuro: Boolean): ColoresEventos =
    if (modoOscuro) ColoresOscuro else ColoresClaro


@Composable
fun ListaEventosScreen(
    eventos: List<Evento>,
    modoOscuro: Boolean,
    onCambiarModoOscuro: () -> Unit,
    onAgregarEvento: () -> Unit,
    onEditarEvento: (Evento) -> Unit,
    onEliminarEvento: (Evento) -> Unit
) {

    val colores = coloresEventos(modoOscuro)

    var textoBusqueda by remember {
        mutableStateOf("")
    }

    val eventosFiltrados = eventos.filter { evento ->

        evento.titulo.contains(
            textoBusqueda,
            ignoreCase = true
        )
    }

    Scaffold(
        containerColor = colores.fondo,

        floatingActionButton = {

            FloatingActionButton(
                onClick = onAgregarEvento,
                containerColor = colores.cafe,
                contentColor = Color.White
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar evento"
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {

            // ============================
            // ENCABEZADO
            // ============================

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = "Café Lista",

                    fontSize = 18.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color = colores.cafeOscuro,

                    modifier =
                        Modifier.weight(1f)
                )

                // ---- Botón modo oscuro ----
                IconButton(
                    onClick = onCambiarModoOscuro
                ) {

                    Icon(
                        imageVector = if (modoOscuro)
                            Icons.Default.LightMode
                        else
                            Icons.Default.DarkMode,

                        contentDescription = "Cambiar modo oscuro",

                        tint = colores.cafe
                    )
                }

                Icon(
                    imageVector =
                        Icons.Default.Person,

                    contentDescription =
                        "Perfil",

                    tint = colores.cafe,

                    modifier =
                        Modifier.size(38.dp)
                )
            }


            Spacer(
                modifier =
                    Modifier.height(26.dp)
            )


            // ============================
            // SALUDO
            // ============================

            Text(
                text = "Hola, Sarah Cortado 👋",

                fontSize = 14.sp,

                color = colores.textoSecundario
            )


            Text(
                text = "Tus próximos eventos",

                fontSize = 26.sp,

                fontWeight =
                    FontWeight.Bold,

                color = colores.cafeOscuro,

                modifier =
                    Modifier.padding(top = 4.dp)
            )


            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )


            // ============================
            // BUSCADOR
            // ============================

            OutlinedTextField(

                value = textoBusqueda,

                onValueChange = {
                    textoBusqueda = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                placeholder = {

                    Text(
                        "Buscar eventos por título..."
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Buscar"
                    )
                },

                singleLine = true,

                shape =
                    RoundedCornerShape(14.dp)
            )


            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )


            // ============================
            // LISTA DE EVENTOS
            // ============================

            LazyColumn(

                modifier =
                    Modifier.fillMaxSize(),

                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                items(
                    items = eventosFiltrados,

                    key = { evento ->
                        evento.id
                    }
                ) { evento ->

                    EventoCard(

                        evento = evento,

                        colores = colores,

                        onEditar = {
                            onEditarEvento(evento)
                        },

                        onEliminar = {
                            onEliminarEvento(evento)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EventoCard(
    evento: Evento,
    colores: ColoresEventos,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {

    var visible by remember {
        mutableStateOf(true)
    }

    AnimatedVisibility(

        visible = visible,

        enter =
            fadeIn() +
                    slideInVertically(),

        exit =
            fadeOut() +
                    slideOutHorizontally()
    ) {

        SwipeEventoCard(

            evento = evento,

            colores = colores,

            onEditar = onEditar,

            onEliminar = {

                visible = false

                onEliminar()
            }
        )
    }
}

@Composable
fun SwipeEventoCard(
    evento: Evento,
    colores: ColoresEventos,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {

    var desplazamiento by remember {
        mutableFloatStateOf(0f)
    }

    val limiteSwipe = -180f

    // Un poco más alta que antes para que quepa la descripción
    val alturaTarjeta = 84.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(alturaTarjeta)
    ) {

        // =================================
        // FONDO QUE APARECE AL DESLIZAR
        // =================================

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(colores.cafe),

            contentAlignment =
                Alignment.CenterEnd
        ) {

            Icon(
                imageVector =
                    Icons.Default.Delete,

                contentDescription =
                    "Eliminar",

                tint =
                    Color.White,

                modifier =
                    Modifier
                        .padding(end = 22.dp)
                        .size(28.dp)
            )
        }


        // =================================
        // TARJETA
        // =================================

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .height(alturaTarjeta)

                .offset {
                    IntOffset(
                        desplazamiento.roundToInt(),
                        0
                    )
                }

                .pointerInput(Unit) {

                    detectHorizontalDragGestures(

                        onHorizontalDrag = {
                                _, cantidad ->

                            desplazamiento =
                                (
                                        desplazamiento +
                                                cantidad
                                        ).coerceIn(
                                        -300f,
                                        0f
                                    )
                        },

                        onDragEnd = {

                            if (
                                desplazamiento <=
                                limiteSwipe
                            ) {

                                onEliminar()

                            } else {

                                desplazamiento = 0f
                            }
                        },

                        onDragCancel = {

                            desplazamiento = 0f
                        }
                    )
                },

            shape =
                RoundedCornerShape(16.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        colores.blanco
                ),

            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
        ) {

            Row(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                // =================================
                // CHECKBOX
                // =================================

                Text(

                    text = "□",

                    fontSize = 27.sp,

                    color = colores.cafe,

                    modifier =
                        Modifier.size(38.dp)
                )


                // =================================
                // FECHA
                // =================================

                FechaEvento(
                    fecha = evento.fecha,
                    colores = colores
                )


                // =================================
                // INFORMACIÓN
                // =================================

                Column(

                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(
                                start = 12.dp
                            )
                ) {

                    Text(

                        text =
                            "• ${evento.categoria}",

                        fontSize = 9.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            colores.textoSecundario,

                        maxLines = 1
                    )

                    Text(

                        text =
                            evento.titulo,

                        fontSize = 14.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            colores.cafeOscuro,

                        maxLines = 1
                    )

                    // -------- Descripción --------
                    if (evento.descripcion.isNotBlank()) {

                        Text(

                            text =
                                evento.descripcion,

                            fontSize = 11.sp,

                            color =
                                colores.textoSecundario,

                            maxLines = 1,

                            overflow =
                                TextOverflow.Ellipsis
                        )
                    }
                }


                // =================================
                // EDITAR
                // =================================

                IconButton(
                    onClick = onEditar
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Edit,

                        contentDescription =
                            "Editar",

                        tint =
                            colores.cafeOscuro
                    )
                }


                // =================================
                // ELIMINAR
                // =================================

                IconButton(

                    onClick = onEliminar

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Delete,

                        contentDescription =
                            "Eliminar",

                        tint =
                            colores.cafe
                    )
                }
            }
        }
    }
}

@Composable
fun FechaEvento(
    fecha: String,
    colores: ColoresEventos
) {

    val partes =
        fecha.split("/")

    val dia =
        if (partes.size == 3)
            partes[0]
        else
            "--"

    val mes =
        if (partes.size == 3)
            obtenerMes(partes[1])
        else
            "---"


    Column(

        modifier =
            Modifier
                .size(
                    width = 45.dp,
                    height = 52.dp
                )
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(
                    colores.cafeClaro
                ),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Text(

            text = mes,

            fontSize = 9.sp,

            fontWeight =
                FontWeight.Bold,

            color = colores.cafe
        )

        Text(

            text = dia,

            fontSize = 18.sp,

            fontWeight =
                FontWeight.Bold,

            color = colores.cafeOscuro
        )
    }
}
fun obtenerMes(
    numero: String
): String {

    return when (numero) {

        "01" -> "ENE"
        "02" -> "FEB"
        "03" -> "MAR"
        "04" -> "ABR"
        "05" -> "MAY"
        "06" -> "JUN"
        "07" -> "JUL"
        "08" -> "AGO"
        "09" -> "SEP"
        "10" -> "OCT"
        "11" -> "NOV"
        "12" -> "DIC"

        else -> "---"
    }
}