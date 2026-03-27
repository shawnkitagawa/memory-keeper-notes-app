package com.example.memorykeeper.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.memorykeeper.application.MemoryKeeperApplication
import com.example.memorykeeper.data.remote.NoteDto
import com.example.memorykeeper.data.remote.ProfileDto
import com.example.memorykeeper.data.repository.MemoryKeeperRepository
import com.example.memorykeeper.ui.uistate.AuthCheck
import com.example.memorykeeper.ui.uistate.AuthUiState
import com.example.memorykeeper.ui.uistate.FilterStatus
import com.example.memorykeeper.ui.uistate.FormState
import com.example.memorykeeper.ui.uistate.Todo
import com.example.memorykeeper.ui.uistate.TodoUiState
import com.example.memorykeeper.ui.uistate.dashBoardUiState
import com.example.memorykeeper.ui.uistate.toDoInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlin.math.exp
import kotlin.time.Instant

class DashboardViewModel(private val memoryKeeperRepository: MemoryKeeperRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<dashBoardUiState>(dashBoardUiState())
    val uiState: StateFlow<dashBoardUiState> = _uiState


    suspend fun checkLogin()
    {
            val result = memoryKeeperRepository.isLoggedIn()
            if (result)
            {
                _uiState.value = _uiState.value.copy(
                    authcheck = AuthCheck.Loggedin
                )
            }
            else
            {
                _uiState.value = _uiState.value.copy(
                    authcheck = AuthCheck.LoggedOut
                )
            }

    }

    fun setUpdateTodo(todo: Todo) {
        _uiState.value = _uiState.value.copy(
            toDoInput = toDoInput(
                id = todo.id,
                title = todo.plan,
                content = todo.planDescription ?: "",
                isRoutine = todo.isRoutine,
                filterStatus = when (todo.category) {
                    "work" -> FilterStatus.WORK
                    "personal" -> FilterStatus.PERSONAL
                    else -> FilterStatus.HEALTH
                }
            )
        )
    }

    fun setAddTodo()
    {
        _uiState.value = _uiState.value.copy(
            toDoInput = toDoInput()
        )
    }


    suspend fun getUserName(): Result<String>
    {
        val result = memoryKeeperRepository.getUserName().map{profile ->
            profile.username
        }
        return result
    }

//    fun getUserName()
//    {
//
//        _uiState.value = _uiState.value.copy(
//            authState = TodoUiState.Loading
//        )
//        viewModelScope.launch {
//            val result = memoryKeeperRepository.getUserName()
//
//            result.fold(
//                onSuccess = { profile ->
//                    _uiState.value = _uiState.value.copy(
//                        userName = profile.username
//                    )
//                },
//                onFailure = { error ->
//                    _uiState.value = _uiState.value.copy(
//                        authState = TodoUiState.Error(
//                            error.message ?: "Failed to load username"
//                        )
//                    )
//                }
//            )
//        }
//
//    }


    fun signOut()
    {
        viewModelScope.launch{

            _uiState.value = _uiState.value.copy(
                authcheck = AuthCheck.Loggedin
            )
            val result = memoryKeeperRepository.signOut()
            val isLoggedin = memoryKeeperRepository.isLoggedIn()

            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isSignedOut = true,
                        authcheck = AuthCheck.LoggedOut
                    )
                    Log.d("Signout", "Succesfully sign out islooged in is ${isLoggedin}")},

                onFailure = {error -> _uiState.value = _uiState.value.copy(
                    authState = TodoUiState.Error(message = error.message ?: "Failed to Sign Out")
                )}
            )
        }
    }

    suspend fun getToDoData(): Result< List< NoteDto>>
    {
        return memoryKeeperRepository.getDataFromDatabase().first()
    }

//    fun getToDoData()
//    {
//        viewModelScope.launch{
//           memoryKeeperRepository.getDataFromDatabase().collect{todo ->
//                todo.fold(
//                        onSuccess = { noteDtoList ->
//                            val todoList = noteDtoList.map { noteDto ->
//                                Todo(
//                                    id = noteDto.id ?: "",
//                                    plan = noteDto.title,
//                                    planDescription = noteDto.content ?: null,
//                                    isRoutine = if (noteDto.section_type == "routine") true else false,
//                                    priority = noteDto.priority,
//                                    createdAt = noteDto.created_at ?: "",
//                                    completed = noteDto.is_completed,
//                                    category = noteDto.category ?: "work",
//
//                                )
//                            }
//                            Log.d("getToDoData", "inspecting success")
//
//                        },
//                        onFailure = {error ->
//                            Log.d("getToDoData", "inspecting error${error.message}")
//                                _uiState.value = _uiState.value.copy(
//                                    authState = TodoUiState.Error(error.message ?: "Failed to load username")
//                                )
//                        }
//                )
//           }
//        }
//    }

    fun addToDo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                formCheck = FormState.Loading
            )
                    if(uiState.value.toDoInput.title == "" )
                    {
                        _uiState.value = _uiState.value.copy(
                            formCheck = FormState.Error(message = "Title is required")
                        )
                        return@launch
                    }
                    val result = memoryKeeperRepository.addToDatabase(
                        task = uiState.value.toDoInput.title,
                        taskDescription = uiState.value.toDoInput.content,
                        category = if (uiState.value.toDoInput.filterStatus == FilterStatus.WORK) "work"
                                    else if (uiState.value.toDoInput.filterStatus == FilterStatus.PERSONAL)"personal"
                                    else "health",
                        sectiontype = if (uiState.value.toDoInput.isRoutine) "routine" else "daily",
                        priority = null,
                        username = _uiState.value.userName
                    )

                    result.fold(
                        onSuccess = {
                            Log.d("AddToDO", "Succesfully insert todo in database")
                            _uiState.value = _uiState.value.copy(
                                formCheck = FormState.Success,
                                toDoInput = toDoInput()
                            )
                        },
                        onFailure = {
                            Log.d("AddToDO", "Failed insert todo in database")
                            loadDashboard()
                            _uiState.value = _uiState.value.copy(
                                formCheck = FormState.Error(it.message ?: "Failed to add todo")
                            )
                        }
                    )
            }
        }

    fun updateTodoData(id: String, updateComplete: Boolean = false)
    {
        viewModelScope.launch{

            if(_uiState.value.toDoInput.title == "")
            {
                _uiState.value = _uiState.value.copy(
                    formCheck = FormState.Error(message = "Title is required")
                )
                return@launch
            }

            _uiState.value = _uiState.value.copy(
                formCheck = FormState.Loading
            )


                val data = _uiState.value.toDo.find{it.id == id} ?: return@launch
                val input = _uiState.value.toDoInput

            Log.d("IDTester", "${id}")

            val result = if (!updateComplete) {
                 memoryKeeperRepository.updateDatabase(
                    task = input.title,
                    taskDescription = input.content ?: "",
                    sectiontype = if(input.isRoutine)"routine" else "daily",
                    username =  _uiState.value.userName,
                    category = if (input.filterStatus == FilterStatus.WORK)"work"
                    else if (input.filterStatus == FilterStatus.PERSONAL)"personal"
                    else "health",
                    priority = null,
                    id = id,
                    isCompleted = data.completed)


            }
            else{
                memoryKeeperRepository.updateDatabase(
                    task = data.plan,
                    taskDescription = data.planDescription ?: "",
                    sectiontype = if(data.isRoutine)"routine" else "daily",
                    username =  _uiState.value.userName,
                    category = if (_uiState.value.filterStatus == FilterStatus.WORK)"work"
                    else if (_uiState.value.filterStatus == FilterStatus.PERSONAL)"personal"
                    else "health",
                    priority = null,
                    id = id,
                    isCompleted = data.completed)

            }

            result.fold(
                onSuccess = {Log.d("UPDATETodoData", "Succesfully updated")
                    _uiState.value = _uiState.value.copy(
                        formCheck = FormState.Success,
                        toDoInput = toDoInput()
                    )
                            },
                onFailure = {
                    Log.d("updateTodoData", "${it.message}")
                    _uiState.value = _uiState.value.copy(
                    formCheck = FormState.Error(message = it.message ?: "Failed to update" )
                )},
            )
        }
    }

    suspend fun deleteTodayDatabase(): Result<Unit>
    {
        val result = memoryKeeperRepository.deleteTodayDatabase()
        return result
    }


//    suspend fun deleteTodayDatabase()
//    {
//        _uiState.value = _uiState.value.copy(
//            authState = TodoUiState.Loading
//        )
//        val result = memoryKeeperRepository.deleteTodayDatabase()
//
//        result.fold(
//            onSuccess = {
//                _uiState.value = _uiState.value.copy(
//                    toDo = _uiState.value.toDo.filter{
//                        it.isRoutine == false
//                    },
//                    authState = TodoUiState.Success
//                )
//            },
//            onFailure = {error -> _uiState.value = _uiState.value.copy(
//                authState = TodoUiState.Error(message = error.message?: "Failed to delete today's task")
//            )},
//        )
//
//    }
    fun updateCategory(category: FilterStatus)
        {
            _uiState.value = _uiState.value.copy(
                toDoInput = _uiState.value.toDoInput.copy(
                    filterStatus = category
                )
            )
        }
    fun updateTitle(title: String)
    {
        _uiState.value = _uiState.value.copy(
           toDoInput = _uiState.value.toDoInput.copy(
               title = title
           )
        )
    }
    fun updateDescription(description: String)
    {
        _uiState.value = _uiState.value.copy(
            toDoInput = _uiState.value.toDoInput.copy(
                content = description
            )
        )

    }
    fun updateIsRoutine(isRoutine: Boolean)
    {
        _uiState.value = _uiState.value.copy(
            toDoInput = _uiState.value.toDoInput.copy(
                isRoutine = isRoutine
            )
        )
    }
    fun updateFilter(filterStatus: FilterStatus)
    {
        _uiState.value = _uiState.value.copy(
            filterStatus = filterStatus
        )
    }
    fun updatecompleted(isCompleted: Boolean, id: String)
    {

        viewModelScope.launch{
            _uiState.value = _uiState.value.copy(
                toDo = _uiState.value.toDo.map{ todo ->
                    if (todo.id == id)
                    {
                        todo.copy(completed = !isCompleted)
                    }
                    else{
                        todo
                    }
                }
            )
            updateTodoData(id = id, true)
        }
    }

    fun updateSignout()
    {
        _uiState.value = _uiState.value.copy(
            isSignedOut = false
        )
    }

    fun resetTodoState()
    {
        _uiState.value = _uiState.value.copy(
            authState = TodoUiState.Idle
        )
    }
    fun resetAuthCheckState()
    {
        _uiState.value = _uiState.value.copy(
            authcheck = AuthCheck.Idle
        )
    }

    fun resetFormState()
    {
        _uiState.value = _uiState.value.copy(
            formCheck = FormState.Idle
        )
    }
    fun loadDashboard() {

        viewModelScope.launch{
            _uiState.value  = _uiState.value.copy(
                authState = TodoUiState.Loading,
                formCheck = FormState.Idle
            )
            val userNameResult = getUserName()
            deleteTodayDatabase()
            val noteDtoDataResult = getToDoData()

            if (userNameResult.isFailure)
            {
                _uiState.value = _uiState.value.copy(
                    authState = TodoUiState.Error("Failed to load userName")
                )
                return@launch

            }
            if (noteDtoDataResult.isFailure)
            {
                _uiState.value = _uiState.value.copy(
                    authState = TodoUiState.Error("Failed to Load noteDto data")
                )
                return@launch
            }
//            if (expireTodo.isFailure)
//            {
//                _uiState.value = _uiState.value.copy(
//                    authState = TodoUiState.Error("Failed to delete expired Todo")
//                )
//            }

            val userName = userNameResult.getOrNull()
            val noteDtos = noteDtoDataResult.getOrNull()

            if (userName != null && noteDtos != null)
            {
                _uiState.value = _uiState.value.copy(
                    userName = userName,
                    toDo =  noteDtos.map{noteDto ->
                        Todo(
                            id = noteDto.id,
                            plan = noteDto.title,
                            planDescription = noteDto.content ?: null,
                            isRoutine = if (noteDto.section_type == "routine") true else false,
                            priority = noteDto.priority,
                            createdAt = noteDto.created_at ?: "",
                            completed = noteDto.is_completed,
                            category = noteDto.category ?: "work",
                            )
                    },
                    authState = TodoUiState.Success
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

                DashboardViewModel(memoryKeeperRepository = memoryKeeperRepository)
            }
        }
    }
}