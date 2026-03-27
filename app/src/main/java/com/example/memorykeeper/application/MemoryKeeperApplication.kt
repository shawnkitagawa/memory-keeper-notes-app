package com.example.memorykeeper.application

import android.app.Application
import com.example.memorykeeper.data.repository.DefaultMemoryKeeperRepository

class MemoryKeeperApplication(): Application() {
    lateinit var container: AppContainer
    override fun onCreate()
    {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}