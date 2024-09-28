package ir.ha.meproject.data.repository

import ir.ha.meproject.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserRepository {
    suspend fun getAllUsers() : Flow<List<User>>
}



class UserRepositoryImpl : UserRepository {

    override suspend fun getAllUsers(): Flow<List<User>> = flow {
        emit(listOf(
            User("Omid", "Sadr", "30", "USA", "New York"),
            User("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
            User("Alireza", "Ganbari", "40", "Iran", "Tehran"),
            User("Hasan", "Azimi", "35", "Iran", "Tehran"),
            User("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
            User("Parsia", "Dolati", "45", "France", "Paris"),
            User("Zahra", "Eslami", "32", "India", "Mumbai")
        ))
    }
}