package com.example.memorykeeper.data.repository

import android.util.Log
import com.example.memorykeeper.data.remote.NoteDto
import com.example.memorykeeper.data.remote.ProfileDto
import com.example.memorykeeper.data.remote.request.insertData
import com.example.memorykeeper.data.remote.request.updateData
import com.example.memorykeeper.ui.uistate.EmptyAuthUiState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.ZoneId
import kotlin.Result.Companion.failure


interface MemoryKeeperRepository {


//    suspend fun signInWithGoogle(idToken: String) : Result<Unit>
    suspend fun signIn(email: String, password: String): Result<Unit>

    suspend fun signUp(email: String, password: String) : Result<Unit>

    suspend fun signOut(): Result<Unit>

    suspend fun addAcount(userName: String) : Result<Unit>

    suspend fun getUserName(): Result<ProfileDto>
    suspend fun addToDatabase(task: String,
                              taskDescription: String?,
                              category: String,
                              sectiontype: String,
                              priority: Int?,
                              username: String) : Result<Unit>

    fun getDataFromDatabase(): Flow<Result<List<NoteDto>>>

    suspend fun updateDatabase(task: String,
                               taskDescription: String?,
                               category: String,
                               sectiontype: String,
                               priority: Int?,
                               username: String,
                               id: String,
                               isCompleted: Boolean) : Result<Unit>

    suspend fun deleteDatabase(id: String) : Result<Unit>

    suspend fun deleteTodayDatabase(): Result<Unit>

    suspend fun isLoggedIn(): Boolean
}

class DefaultMemoryKeeperRepository(
    private val supabase: SupabaseClient
): MemoryKeeperRepository
{
//    override  suspend fun signInWithGoogle(idToken: String) : Result<Unit>
//    {
//        return try {
//            supabase.auth.signInWith(Google)
//            Result.success(Unit)
//
//        }catch(e: Exception)
//        {
//            Result.failure(e)
//        }
//
//    }
    override suspend fun addAcount(userName: String) : Result<Unit> {

        if (userName == "")
        {
            return Result.failure(Exception("Requies a Username"))
        }
        val user_id = supabase.auth.currentUserOrNull()?.id
            ?:return Result.failure(Exception("User not Logged in"))

        return try {

                supabase.from("profile").insert(
                    mapOf(
                        "user_id" to user_id,
                        "username" to userName
                    )
                )
            Result.success(Unit)
            }catch (e: Exception)
            {
                Result.failure(e)
            }
        }

    override suspend fun getUserName(): Result<ProfileDto> {
        val user_id = supabase.auth.currentUserOrNull()?.id
            ?: return Result.failure(Exception("User not Logged in "))

        return try{
            val username = supabase.from("profile").select {
                filter {
                    eq("user_id", user_id)
                }
            }
                .decodeSingle<ProfileDto>()
            Result.success(username )

        }catch(e: Exception)
        {
            Result.failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password

            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

     override suspend fun signUp(email: String, password: String) : Result<Unit>
    {
         return try{
             supabase.auth.signUpWith(Email)
             {
                 this.email = email
                 this.password = password
             }
             Result.success(Unit)

         } catch(e: Exception)
         {
             Result.failure(e)
         }
    }

    override suspend fun signOut(): Result<Unit>
    {
        return try{
            supabase.auth.signOut()
            Result.success(Unit)
        }catch(e: Exception)
        {
            Result.failure(e)
        }
    }

    // Create sign out later

    override suspend fun addToDatabase(task: String,
                                       taskDescription: String?,
                                       category: String,
                                       sectionType: String,
                                       priority: Int?,
                                       username: String) : Result<Unit>
    {
        Log.d("AddToDatabae", "Its running I guess")
        val userId = supabase.auth.currentUserOrNull()?.id
            ?: return Result.failure(Exception("User not logged in"))
        return try {
            supabase.from("notes").insert(
                insertData(
                    title = task ,
                    content = taskDescription,
                    category = category,
                    section_type = sectionType,
                    priority = priority,
                    user_name = username,
                    user_id = userId
                )
            )
            Result.success(Unit)
        }catch(e: Exception)
        {
            Result.failure(e)
        }
    }

    override fun getDataFromDatabase(): Flow<Result<List<NoteDto>>> =
        flow {
            val userId = supabase.auth.currentUserOrNull()?.id
                ?: throw Exception("User not logged in")

            val notes = supabase.from("notes")
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<NoteDto>()

            emit(Result.success(notes))
        }.catch { e ->
            emit(Result.failure(e))
        }

    override suspend fun updateDatabase(task: String,
                                        taskDescription: String?,
                                        category: String,
                                        sectiontype: String,
                                        priority: Int?,
                                        username: String,
                                        id: String,
                                        isCompleted: Boolean) : Result< Unit>
    {

        val userId = supabase.auth.currentUserOrNull()?.id
            ?: return Result.failure(Exception("User not logged in"))
        try {
            supabase.from("notes")
                .update(value = updateData(
                    title = task ,
                    content = taskDescription,
                    category = category,
                    section_type = sectiontype,
                    priority = priority,
                    user_name = username,
                    user_id = userId,
                    id = id,
                    is_completed = isCompleted
                ))
                {
                    filter {
                        eq("id", id)
                        eq("user_id", userId)
                    }
                }
            return Result.success(Unit)
        }
        catch(e: Exception)
        {
            return Result.failure(e)
        }
    }
    override suspend fun deleteDatabase(id: String) : Result< Unit>
    {
        val userId = supabase.auth.currentUserOrNull()?.id
            ?: return Result.failure(Exception("User not logged in"))
        try {
            supabase.from("notes")
                .delete()
                {
                    filter {
                        eq("id", id)
                        eq("user_id", userId)
                    }
                }
            return Result.success(Unit)
        }catch(e: Exception)
        {
           return  Result.failure(e)
        }
    }

    override suspend fun deleteTodayDatabase() : Result< Unit>
    {
        val userId = supabase.auth.currentUserOrNull()?.id
            ?: return Result.failure(Exception("User not logged in"))

        val zone = ZoneId.systemDefault()
        val startOfToday = LocalDate.now()
            .atStartOfDay(zone)
            .toInstant()
            .toString()

        // idea everytime you missed didn't got completed but got delete you get more damage
        // boss gets stronger
        try {
            supabase.from("notes")
                .delete()
                {
                    filter {
                        eq("section_type", "daily")
                        lt("created_at", startOfToday)
                        eq("user_id", userId)
                    }
                }

            return Result.success(Unit)
        }catch(e: Exception)
        {
            return  Result.failure(e)
        }
    }



    override suspend fun isLoggedIn(): Boolean{

        Log.d("isLoggedIn", "${supabase.auth.currentUserOrNull()?.id}")
        return supabase.auth.currentUserOrNull() != null
    }
}