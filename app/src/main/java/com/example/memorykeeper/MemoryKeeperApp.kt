package com.example.memorykeeper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.memorykeeper.ui.screen.EmptyScreen


private fun Color.fillMaxSize() {
    TODO("Not yet implemented")
}

@Composable
fun MemoryKeeperApp() {
    Scaffold(

    ) { innerPadding ->

        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
            .fillMaxSize()
            .padding(innerPadding))
        {
            EmptyScreen()
        }
    }
}