package com.example.memorykeeper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.memorykeeper.application.MemoryKeeperApplication
import com.example.memorykeeper.data.repository.MemoryKeeperRepository
import com.example.memorykeeper.ui.uistate.AuthScreenState
import com.example.memorykeeper.ui.uistate.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val memoryKeeperRepository: MemoryKeeperRepository): ViewModel() {


    private val _uiState  = MutableStateFlow<AuthScreenState>(AuthScreenState())
        val uiState: StateFlow<AuthScreenState> = _uiState


    fun updateEmail(email:String)
    {
        _uiState.value = _uiState.value.copy(
            email = email
        )
    }
    fun updateUserName(userName:String)
    {
        _uiState.value = _uiState.value.copy(
            userName = userName
        )
    }
    fun updatePass(password: String)
    {
        _uiState.value = _uiState.value.copy(
            password = password
        )
    }

    fun SignIn(email: String, password: String)
    {
        if (email == "" || password == "") return
        viewModelScope.launch()
        {
            _uiState.value = _uiState.value.copy( authState = AuthUiState.Loading)

                val result = memoryKeeperRepository.signIn(email, password)

                _uiState.value = _uiState.value.copy(
                    authState = result.fold(onSuccess = { AuthUiState.Success },
                        onFailure = { AuthUiState.Error(it.message ?: "Signed in failed ")})
                )

        }
    }

    fun signUp(email: String, password: String, userName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(authState = AuthUiState.Loading)

            val signUpResult = memoryKeeperRepository.signUp(email, password)

            if (signUpResult.isSuccess) {
                val addAccountResult = memoryKeeperRepository.addAcount(userName)

                _uiState.value = _uiState.value.copy(
                    authState = addAccountResult.fold(
                        onSuccess = { AuthUiState.Success },
                        onFailure = {
                            AuthUiState.Error(it.message ?: "Failed to create profile")
                        }
                    )
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    authState = AuthUiState.Error(
                        signUpResult.exceptionOrNull()?.message ?: "Signup failed"
                    )
                )
            }
        }
    }


    companion object{

        val Factory: ViewModelProvider.Factory = viewModelFactory {

            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as MemoryKeeperApplication)
                val memoryKeeperRepository = application.container.memoryKeeperRepository

                AuthViewModel(memoryKeeperRepository = memoryKeeperRepository)
            }
        }
    }

}