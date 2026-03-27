package com.example.memorykeeper.application

import android.content.Context
import com.example.memorykeeper.data.repository.DefaultMemoryKeeperRepository
import com.example.memorykeeper.data.repository.MemoryKeeperRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

interface AppContainer{
    val memoryKeeperRepository: MemoryKeeperRepository
}

class DefaultAppContainer(private val context: Context): AppContainer
{

        private val supabase by lazy {
            createSupabaseClient(
                supabaseUrl = "https://gllzzdchkplahkpkilrr.supabase.co",
                supabaseKey = "sb_publishable_IpGTbpMrHeeaW92SpgRm9Q_s_QUhnE2"
            ) {
                install(Auth)
                install(Postgrest)
            }

        }
    override val memoryKeeperRepository: MemoryKeeperRepository by lazy {

        DefaultMemoryKeeperRepository(supabase)
    }
}