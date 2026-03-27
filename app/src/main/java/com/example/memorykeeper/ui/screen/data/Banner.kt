package com.example.memorykeeper.ui.screen.data

import com.example.memorykeeper.R

data class Banner(
    val img : Int,
    val header: String,
    val subHeader: String,
)


val banners = listOf(

    Banner(R.drawable.beautiful_anime_character_cartoon_scene, "Keep going, even on hard days","Discipline is built in the quiet moments"),
    Banner(R.drawable.anime_style_group_boys_spending_time_together_enjoying_their_friendship,"Self-respect is everything", "How you treat yourself sets the tone"),
    Banner(R.drawable.portrait_adorable_anime_dog, "Fuel your body right", "Healthy choices build lasting energy")
)
