package com.example.memorykeeper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.memorykeeper.ui.navigation.MemoryNavHost
import com.example.memorykeeper.ui.screen.EmptyScreen


private fun Color.fillMaxSize() {
    TODO("Not yet implemented")
}

@Composable
fun MemoryKeeperApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(

    ) { innerPadding ->

        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
            .fillMaxSize()
            .padding(innerPadding))
        {
            MemoryNavHost(navController)
        }
    }
}