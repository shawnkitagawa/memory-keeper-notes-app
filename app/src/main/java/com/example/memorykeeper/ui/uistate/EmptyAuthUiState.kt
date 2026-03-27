package com.example.memorykeeper.ui.uistate

sealed interface EmptyAuthUiState {
    object Idle : EmptyAuthUiState
    object LoggedIn: EmptyAuthUiState
    object NavigateToAuth: EmptyAuthUiState
}