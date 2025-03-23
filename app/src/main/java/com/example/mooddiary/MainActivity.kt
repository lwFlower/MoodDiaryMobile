package com.example.mooddiary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mooddiary.ui.theme.MoodDiaryTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _currentRoute = MutableStateFlow("records")
    val currentRoute: StateFlow<String> = _currentRoute

    fun navigateTo(route: String) {
        viewModelScope.launch {
            _currentRoute.value = route
        }
    }

    init {
        navigateTo("records")
    }
}

class MainActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodDiaryTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = remember { MainViewModel() }
    val currentRoute by viewModel.currentRoute.collectAsState()

    LaunchedEffect(currentRoute) {
        if (navController.currentDestination?.route != currentRoute) {
            navController.navigate(currentRoute) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "records",
        modifier = Modifier.padding(16.dp)
    ) {
        composable("records") { RecordsScreen(navController) }
        composable("add") { AddScreen(navController) }
        composable("analytics") { AnalyticsScreen(navController) }
        composable("create_emoji") { EmojiEditScreen(navController) }
        composable("emojiList") { IconScreen(navController) }
    }
}

@Preview
@Composable
fun ShowRecordScreen() {
    val navController = rememberNavController()
    RecordsScreen(navController)
}

@Composable
fun RecordsScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        var isVisible = remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ваши записи",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "menu",
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { isVisible.value = !isVisible.value }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    RecordCardFull(
                        mood = "хорошо",
                        dateTime = "02.03, 23:00",
                        emojis = listOf(
                            R.drawable.emoji_happy to 60,
                            R.drawable.emoji_cool to 70,
                            R.drawable.emoji_sad to 20
                        ),
                        text = "Lorem ipsum dolor amet.Lorem ipsum dolor amet.Lorem ipsum dolor amet.Lorem ipsum dolor amet.Lorem ipsum dolor amet.Lorem ipsum dolor amet.Lorem ipsum dolor amet.",
                        imageName = "good"
                    )

                    RecordCardShort(
                        mood = "средне",
                        dateTime = "03.03, 12:00",
                        emojis = listOf(R.drawable.emoji_cry to 50, R.drawable.emoji_sad to 50),
                        text = "Дополнительный текст",
                        imageName = "meh"
                    )
                }
            }
            if (isVisible.value) {
                Menu(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-60).dp, y = 50.dp),
                    navController
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color(0xFF570B99))
        )

        NavigationBar(
            containerColor = Color.White,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Записи",
                            modifier = Modifier.size(45.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF570B99),
                        unselectedIconColor = Color.Gray
                    ),
                    selected = currentRoute == "records",
                    onClick = { navController.navigate("records") }
                )
                NavigationBarItem(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Mood Icon",
                            modifier = Modifier.size(80.dp)
                        )
                    },
                    selected = false,
                    onClick = { navController.navigate("add") }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.chart_bar_line),
                            contentDescription = "Настройки",
                            modifier = Modifier.size(60.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF570B99),
                        unselectedIconColor = Color.Gray
                    ),
                    selected = currentRoute == "analytics",
                    onClick = { navController.navigate("analytics") }
                )
            }
        }
    }
}

val imageMap = mapOf(
    "good" to R.drawable.good,
    "meh" to R.drawable.meh
)

@Composable
fun RecordCardFull(
    mood: String,
    dateTime: String,
    emojis: List<Pair<Int, Int>>,
    text: String,
    modifier: Modifier = Modifier,
    imageName: String
) {
    Card(
        modifier = modifier
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val imageResource = imageMap[imageName] ?: R.drawable.frown
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Mood Icon",
                        modifier = Modifier.size(70.dp, 70.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = mood,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = dateTime,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Меню",
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                emojis.forEach { (emojiRes, percent) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = emojiRes),
                            contentDescription = "Mood Icon",
                            modifier = Modifier.size(70.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$percent%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.6f), shape = RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun RecordCardShort(
    mood: String,
    dateTime: String,
    emojis: List<Pair<Int, Int>>,
    text: String,
    modifier: Modifier = Modifier,
    imageName: String
) {
    Card(
        modifier = modifier
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val imageResource = imageMap[imageName] ?: R.drawable.frown
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Mood Icon",
                        modifier = Modifier.size(60.dp, 60.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = mood,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = dateTime,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Меню",
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                emojis.forEach { (emojiRes, _) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = emojiRes),
                            contentDescription = "Mood Icon",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.6f), shape = RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun Menu(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(7.dp)
    ) {
        Row(
            Modifier
                .padding(7.dp)
                .background(Color.White)
        ) {
            Text(
                text = "Значки",
                fontSize = 20.sp,
                modifier = Modifier.clickable { navController.navigate("emojiList") }
            )
        }

        Row(
            Modifier
                .padding(7.dp)
                .background(Color.White)
        ) {
            Text(
                text = "Уведомления",
                fontSize = 20.sp,
                modifier = Modifier.clickable { }
            )
        }
    }
}