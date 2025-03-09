package com.example.mooddiary

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController) {

    val MoodList = listOf(
        R.drawable.good,
        R.drawable.meh,
        R.drawable.frown
    )

    val EmotionList = listOf(
        R.drawable.emoji_sad,
        R.drawable.emoji_cry,
        R.drawable.emoji_cool,
        R.drawable.emoji_happy,
    )
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
                }
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Заглушка для поля "Дата и время"
            OutlinedTextField(
                value = "Выбрать дату и время",
                onValueChange = {},
                label = { Text("Дата и время") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { /* Handle DatePicker */ }) {
                        Image(painter = painterResource(id = R.drawable.calendar), contentDescription = "Calendar")
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                textAlign = TextAlign.Center,
                text = "Как вам день?",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
            )

            EmojiBox(MoodList)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                textAlign = TextAlign.Center,
                text = "Какие эмоции ощущали?",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
            )

            EmojiBox(EmotionList)

            Spacer(modifier = Modifier.height(16.dp))

            // Блок текста для ввода
            Text(
                textAlign = TextAlign.Center,
                text = "Расскажите что-нибудь",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Поле для ввода текст
        }
    }
}

@Composable
fun EmojiBox(
    emojis: List<Int>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBE86FF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                emojis.forEach { emojiRes ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = emojiRes),
                            contentDescription = "Mood Icon",
                            modifier = Modifier
                                .size(60.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ShowAddScreen(){
    val navController = rememberNavController()
    AddScreen(navController)
}

