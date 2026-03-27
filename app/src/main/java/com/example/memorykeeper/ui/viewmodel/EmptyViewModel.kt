package com.example.memorykeeper.ui.viewmodel

import android.util.Log
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.memorykeeper.application.MemoryKeeperApplication
import com.example.memorykeeper.data.repository.MemoryKeeperRepository
import com.example.memorykeeper.ui.uistate.EmptyAuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmptyViewModel(private val memoryKeeperRepository: MemoryKeeperRepository): ViewModel() {

    private val _uiState: MutableStateFlow<EmptyAuthUiState> =
        MutableStateFlow(EmptyAuthUiState.Idle)
    val uiState: StateFlow<EmptyAuthUiState> = _uiState

    fun isLoading()
    {
        viewModelScope.launch {
            val result = memoryKeeperRepository.isLoggedIn()
            Log.d("isLoading", "result is ${result}")
            if (result)
            {
                _uiState.value = EmptyAuthUiState.LoggedIn
            }
            else
            {
                _uiState.value = EmptyAuthUiState.NavigateToAuth

            }
        }
    }

    fun onNavigationHandled()
    {
        _uiState.value = EmptyAuthUiState.Idle
    }
    companion object{

        val Factory: ViewModelProvider.Factory = viewModelFactory {

            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as MemoryKeeperApplication)
                val memoryKeeperRepository = application.container.memoryKeeperRepository

                EmptyViewModel(memoryKeeperRepository = memoryKeeperRepository)
            }
        }
    }
}