package com.example.lab07ejer02

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen() {
    val context = LocalContext.current
    val db = Room.databaseBuilder(context, PaymentDatabase::class.java, "payment_db").build()
    val dao = db.paymentDao()
    val coroutineScope = rememberCoroutineScope()

    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Método de Pago") },
                navigationIcon = {
                    IconButton(onClick = { /* Acción de regreso */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción de búsqueda */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8D6E63),  // Color marrón claro
                    titleContentColor = Color.White,  // Color del título en blanco
                    actionIconContentColor = Color.White  // Color de los íconos en blanco
                )
            )

        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(Color(0xFFD7CCC8))  // Fondo beige claro
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Tarjeta de Información de Pago
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFBCAAA4)  // Color de fondo para la tarjeta
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Producto - S/. 180", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
                        Text("15/09/2024", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        Text("Recuerda activar las compras por internet con tu banco", style = MaterialTheme.typography.bodyMedium)
                    }
                }


                // Campos de texto para el pago
                PaymentTextField(value = cardNumber, label = "Número de tarjeta")
                PaymentTextField(value = expiryDate, label = "MM/AA")
                PaymentTextField(value = cvv, label = "CVV")
                PaymentTextField(value = firstName, label = "Nombre")
                PaymentTextField(value = lastName, label = "Apellido")
                PaymentTextField(value = email, label = "Gmail")

                // Botón para realizar el pago
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val payment = Payment(0, cardNumber, expiryDate, cvv, firstName, lastName, email)
                            dao.insert(payment)
                            // Aquí puedes agregar lógica para mostrar un mensaje de confirmación
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(50),  // Botón con bordes redondeados
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))  // Verde para el botón de pago
                ) {
                    Text("Pagar S/. 180", fontSize = 16.sp, color = Color.White)
                }


                Text(
                    text = "Infórmate sobre el tratamiento de tus datos personales AQUÍ",
                    style = MaterialTheme.typography.bodyMedium,  // Cambiado de body2 a bodyMedium
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentTextField(value: String, label: String, onValueChange: (String) -> Unit = { }) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp)),  // Fondo gris claro con esquinas redondeadas
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFE0E0E0),  // Cambiado backgroundColor por containerColor
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}



