package ir.ha.meproject.domain

import ir.ha.meproject.data.model.User
import ir.ha.meproject.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UserUseCase {

    suspend fun getFirstUser() : Flow<User>

    suspend fun getAllUsers() : Flow<List<User>>

    suspend fun getCustomItem(index : Int)  :  Flow<User>
}



class UserUseCaseImpl @Inject constructor(private val userRepository: UserRepository) : UserUseCase {

    override suspend fun getFirstUser() = flow {
        emit(getAllUsers().first().first())
    }

    override suspend fun getAllUsers() = userRepository.getAllUsers()


    override suspend fun getCustomItem(index: Int): Flow<User> = flow {
        getAllUsers().first().let { list ->
            if (index < list.size-1) emit(list[index])
        }
    }
}