package ir.ha.meproject.domain


import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.spyk
import ir.ha.meproject.data.model.UserEntity
import ir.ha.meproject.data.repository.UserRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class UserEntityUseCaseTest1 {

    private var userUseCase = spyk(UserUseCaseImpl(UserRepositoryImpl()))
    private var mockUserEntities = arrayListOf<UserEntity>()

    @Before
    fun setUp() {
        mockUserEntities.addAll(
            arrayListOf(
                UserEntity("Omid", "Sadr", "30", "USA", "New York"),
                UserEntity("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
                UserEntity("Alireza", "Ganbari", "40", "Iran", "Tehran"),
                UserEntity("Hasan", "Azimi", "35", "Iran", "Tehran"),
                UserEntity("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
                UserEntity("Parsia", "Dolati", "45", "France", "Paris"),
                UserEntity("Zahra", "Eslami", "32", "India", "Mumbai")
            )
        )
    }

    @After
    fun reset() {
        // Reset the dispatcher
        mockUserEntities.clear()
    }

    /***  Correctness by spyk */
    @Test
    fun `getAllUsers emits usersFlow with list of users`() = runTest {
        val list = userUseCase.getAllUsers().first()
        assertEquals(mockUserEntities, list)
        coVerify(exactly = 1) { userUseCase.getAllUsers() } /*** Method Calls*/
        advanceUntilIdle()
    }


    @Test
    fun testCoroutineExecution() = runTest {
        var result = 0

        launch {
            delay(10000)
            result = 1
        }

        runCurrent() // This won't change result as the coroutine is delayed
        assertEquals(0, result)

        advanceUntilIdle() // This will run all pending coroutines
        assertEquals(1, result)
    }

}









/**
 * Correctness by mockK
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserEntityUseCaseTest2 {

    @get:Rule
    val mockkRule = MockKRule(this)

    val TAG = this::class.java.simpleName + " ------------> "
    private val testDispatcher = UnconfinedTestDispatcher()
    private var userUseCase = mockk<UserUseCaseImpl>()
    private var mockUserEntities = arrayListOf<UserEntity>()

    @Before
    fun setUp() {
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
        // Mock data
        mockUserEntities.addAll(
            arrayListOf(
                UserEntity("Omid", "Sadr", "30", "USA", "New York"),
                UserEntity("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
                UserEntity("Alireza", "Ganbari", "40", "Iran", "Tehran"),
                UserEntity("Hasan", "Azimi", "35", "Iran", "Tehran"),
                UserEntity("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
                UserEntity("Parsia", "Dolati", "45", "France", "Paris"),
                UserEntity("Zahra", "Eslami", "32", "India", "Mumbai")
            )
        )
    }

    @After
    fun reset() {
        // Reset the dispatcher
        mockUserEntities.clear()
        kotlinx.coroutines.Dispatchers.resetMain()
    }




    @Test
    fun `getAllUsers emits usersFlow with list of users`() = runTest {
        coEvery { userUseCase.getAllUsers() } returns flowOf(mockUserEntities)
        val list = userUseCase.getAllUsers().first()
        assertEquals(mockUserEntities, list)
        /*** Method Calls*/
        coVerify(exactly = 1) { userUseCase.getAllUsers() }
        advanceUntilIdle()
    }

    @Test
    fun `Users should be Young`() = runTest {
        coEvery { userUseCase.getAllUsers() } returns flowOf(mockUserEntities)
        val list = userUseCase.getAllUsers().first()
        val youngs = list.filter { it.age.toInt() > 19 }
        assertTrue(youngs.isNotEmpty())
        /*** Method Calls*/
        coVerify(exactly = 1) { userUseCase.getAllUsers() }
        advanceUntilIdle()

    }


    @Test
    fun `Users should be child`() = runTest {
        coEvery { userUseCase.getAllUsers() } returns flowOf(mockUserEntities)
        val list = userUseCase.getAllUsers().first()
        val teenage = list.find { it.age.toInt() < 19 }
        assertTrue(teenage == null)
        /*** Method Calls*/
        coVerify(exactly = 1) { userUseCase.getAllUsers() }
        advanceUntilIdle()
    }


    @Test
    fun `There must be a user from Iran`() = runTest {
        coEvery { userUseCase.getAllUsers() } returns flowOf(mockUserEntities)
        val list = userUseCase.getAllUsers().first()
        val thereIs = list.find { it.fromCountry.equals("Iran") }
        assertTrue(thereIs != null)
        /** Method Calls */
        coVerify(exactly = 1) { userUseCase.getAllUsers() }
        advanceUntilIdle()
    }


    /*** Performance and Timeouts Test */
    @Test(expected = TimeoutCancellationException::class)
    fun `time out time testing`() = runTest {
        withTimeout(4000) {
            coEvery { userUseCase.getAllUsers() } returns flowOf(mockUserEntities)
            println(TAG + "Before delay -->  ")
            delay(8000)
            println(TAG + "After delay -->  ")
            val list = userUseCase.getAllUsers().first()
            val thereIs = list.find { it.fromCountry.equals("Iran") }
            assertTrue(thereIs != null)
            /*** Method Calls*/
            coVerify(exactly = 1) { userUseCase.getAllUsers() }
        }
    }


}


