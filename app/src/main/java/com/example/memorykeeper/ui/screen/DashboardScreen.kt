package com.example.memorykeeper.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun DashboardScreen() {

    Column()
    {
        TopBar()
        HeroBanner()
        filterIcon()
        TodoList()
        LongTermGoal()
    }
}
@Composable
fun TopBar(
    modifier: Modifier = Modifier
)
{

}

@Composable
fun HeroBanner()
{

}

@Composable
fun filterIcon()
{

}

@Composable
fun TodoList()
{

}

@Composable
fun LongTermGoal()
{

}

