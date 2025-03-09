package com.example.mooddiary

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AnalyticsScreen(navController: NavController) {
    Column {
        Text("Экран аналитики", style = MaterialTheme.typography.headlineLarge)
        // Форма для добавления записи или другой контент
    }
}

@Preview
@Composable
fun ShowAnalyticsPrev(){
    val navController = rememberNavController()
    AnalyticsScreen(navController)
}