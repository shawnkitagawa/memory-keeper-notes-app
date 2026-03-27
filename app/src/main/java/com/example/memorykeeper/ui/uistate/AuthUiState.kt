package com.example.memorykeeper.ui.uistate



data class AuthScreenState(
    val email : String = "",
    val password: String = "",
    val authState: AuthUiState = AuthUiState.Idle,
    val userName: String = "",
)



sealed interface AuthUiState {
    object Idle: AuthUiState
    object Loading: AuthUiState
    object Success: AuthUiState
    data class Error(val message: String) : AuthUiState
}