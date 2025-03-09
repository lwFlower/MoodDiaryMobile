package com.example.mooddiary

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiEditScreen(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() },
                        Modifier.size(60.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.popBackStack() },
                        Modifier.size(60.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "Back"
                        )
                    }
                },

            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center // Центрируем содержимое
                ){
                    IconButton(onClick = { navController.popBackStack() },
                        Modifier.size(80.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.save),
                            contentDescription = "Save")
                    }
                }
            }
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .align(Alignment.Center)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ){
                    TextField("Введите название")
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row(
                    horizontalArrangement = Arrangement.Center
                ){
                    Upload()
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row(
                    horizontalArrangement = Arrangement.Center
                ){
                    DropdownMenu()
                }
            }
        }
    }
}

@Composable
fun Upload(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 284.dp, height = 273.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xffd9d9d9).copy(alpha = 0.5f))
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(20.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.upload),
                contentDescription = "Vector",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun DropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Состояния") }

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text("Выберите категорию") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("Состояние", "Эмоции", "Активности").forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(text = option)
                    },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    contentPadding = PaddingValues(16.dp),
                    interactionSource = interactionSource
                )
            }
        }
    }
}

@Composable
fun TextField(textPlaceholder: String) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(textPlaceholder) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDropdownMenuExample() {
    val navController = rememberNavController()
    EmojiEditScreen(navController)
}