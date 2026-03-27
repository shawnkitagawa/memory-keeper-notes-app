package com.example.memorykeeper.ui.uistate

import io.github.jan.supabase.auth.Auth

data class dashBoardUiState(
    val toDo: List<Todo> = emptyList(),
    val filterStatus: FilterStatus = FilterStatus.ALL,
    val authState: TodoUiState = TodoUiState.Idle,
    val userName: String = "",
    val toDoInput: toDoInput = toDoInput(),
    val isSignedOut: Boolean = false,
    val authcheck: AuthCheck = AuthCheck.Idle,
    val formCheck: FormState = FormState.Idle
)

sealed interface FormState{
    object Idle: FormState
    object Loading: FormState
    object Success: FormState

    data class Error(val message: String): FormState

}
data class toDoInput(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val isRoutine: Boolean = false,
    val filterStatus: FilterStatus = FilterStatus.PERSONAL
)

data class Todo(
    val id: String = "",
    val plan: String = "",
    val planDescription: String? = null,
    val isRoutine:Boolean = false,
    val priority: Int? = 0 ,
    val createdAt: String = "",
    val completed: Boolean = false,
    val category: String = ""
)
sealed interface TodoUiState {
    object Idle : TodoUiState
    object Loading : TodoUiState
    object Success : TodoUiState
    data class Error(val message: String) : TodoUiState
}

sealed interface AuthCheck{
    object Idle: AuthCheck
    object Loggedin: AuthCheck
    object LoggedOut: AuthCheck
}
enum class FilterStatus {
    WORK,
    PERSONAL,
    HEALTH,
    ALL,

    COMPLETED,
}


