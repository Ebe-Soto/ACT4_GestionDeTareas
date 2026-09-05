package com.example.act4_dam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.act4_dam.ui.theme.ACT4_DAMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // Modo oscuro / claro para toda la app.
            // Empieza en claro; el usuario lo cambia con el botón de la lista.
            var modoOscuro by remember {
                mutableStateOf(false)
            }

            ACT4_DAMTheme(darkTheme = modoOscuro) {
                val navController = rememberNavController()
                //Lista de eventos
                val eventos = remember { mutableStateListOf<Evento>() }

                NavHost(navController = navController,startDestination = "Bienvenida") {
                    composable("Bienvenida") {
                        PantallaBienvenida(
                            onComenzarClick = { navController.navigate("Registrarse") },
                        )
                    }

                    composable("Registrarse") {
                        PantallaRegistro(
                            onCrearCuentaClick = { nombre, correo, contrasena -> navController.navigate("ListaEventos")
                            }
                        )
                    }

                    composable("ListaEventos") {

                        // Controla si se muestra
                        // el popup de agregar
                        var mostrarAgregar by remember {
                            mutableStateOf(false)
                        }

                        // Guarda el evento que queremos editar
                        var eventoAEditar by remember {
                            mutableStateOf<Evento?>(null)
                        }

                        ListaEventosScreen(

                            eventos = eventos,

                            modoOscuro = modoOscuro,

                            onCambiarModoOscuro = {
                                modoOscuro = !modoOscuro
                            },


                            // -------------------------
                            // AGREGAR
                            // -------------------------

                            onAgregarEvento = { mostrarAgregar = true },


                            // -------------------------
                            // EDITAR
                            // -------------------------

                            onEditarEvento = { evento -> eventoAEditar = evento },


                            // -------------------------
                            // ELIMINAR
                            // -------------------------

                            onEliminarEvento = { evento ->

                                eventos.remove(evento)
                            }
                        )
                        // =================================
                        // POPUP AGREGAR
                        // =================================

                        if (mostrarAgregar) {

                            AgregarEventoDialog(

                                modoOscuro = modoOscuro,

                                onDismiss = {

                                    mostrarAgregar = false

                                },

                                onGuardar = { nuevoEvento ->

                                    // Agregamos el evento
                                    // a nuestra lista

                                    eventos.add(nuevoEvento)

                                    // Cerramos el popup

                                    mostrarAgregar = false

                                }
                            )
                        }


                        // =================================
                        // POPUP EDITAR
                        // =================================

                        eventoAEditar?.let { evento ->

                            EditarEventoDialog(

                                evento = evento,

                                modoOscuro = modoOscuro,

                                onDismiss = {

                                    eventoAEditar = null

                                },

                                onGuardar = { eventoActualizado ->

                                    // Buscamos el evento original

                                    val indice =
                                        eventos.indexOfFirst {

                                            it.id == eventoActualizado.id

                                        }


                                    // Si existe, lo reemplazamos

                                    if (indice != -1) {

                                        eventos[indice] =
                                            eventoActualizado

                                    }


                                    // Cerramos el popup

                                    eventoAEditar = null

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}