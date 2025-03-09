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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconScreen(navController: NavController) {

    val MoodList = listOf(
        R.drawable.good to "Хорошо",
        R.drawable.meh to "Средне",
        R.drawable.frown to "Плохо"
    )

    val EmotionList = listOf(
        R.drawable.emoji_sad to "Грустно",
        R.drawable.emoji_cry to "Плачу",
        R.drawable.emoji_cool to "Круто",
        R.drawable.emoji_happy to "Счастливо",
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ваши значки",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,) },
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
                    IconButton(onClick = { navController.navigate("create_emoji") },
                        Modifier.size(80.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.add),
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
            EmojiCard(MoodList, "Состояния", navController)

            Spacer(modifier = Modifier.height(16.dp))

            EmojiCard(EmotionList, "Эмоции", navController)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun EmojiCard(
    emojis: List<Pair<Int, String>>,
    titleText: String,
    navController: NavController
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
            Text(
                textAlign = TextAlign.Center,
                color = Color(0xFF570B99),
                text = titleText,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                emojis.forEach { (emojiRes, name) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = emojiRes),
                            contentDescription = "Mood Icon",
                            modifier = Modifier
                                .clickable { navController.navigate("create_emoji") }
                                .size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview
@Composable
fun ShowIconScreen(){
    val navController = rememberNavController()
    IconScreen(navController)
}