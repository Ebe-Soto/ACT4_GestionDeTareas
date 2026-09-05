package com.example.act4_dam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.act4_dam.ui.theme.ACT4_DAMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ACT4_DAMTheme {
                val navController = rememberNavController()

                NavHost(navController = navController,startDestination = "Bienvenida") {
                    composable("Bienvenida") {
                        PantallaBienvenida(
                            onComenzarClick = { navController.navigate("Registrarse")},
                        )
                    }

                    composable("Registrarse") {
                        PantallaRegistro(
                            onCrearCuentaClick = {  nombre, correo, contrasena ->
                            }
                        )
                    }


                }
            }
        }
    }
}