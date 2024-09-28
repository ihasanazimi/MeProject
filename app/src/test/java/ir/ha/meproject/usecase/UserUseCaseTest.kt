package ir.ha.meproject.usecase


import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.spyk
import ir.ha.meproject.data.model.User
import ir.ha.meproject.data.repository.UserRepositoryImpl
import ir.ha.meproject.domain.UserUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


/**
 * Correctness
 * Method Calls
 * Error Handling
 * Performance and Timeouts
 * State Verification
 * Behavior Data Verification
 * Input and Output
 * No-Call Verification
 * Boundary Conditions
 * Concurrency Tests
 * Exceptional Conditions
 * Return Value
 * Performance Testing
 * Side Effects
 */


@OptIn(ExperimentalCoroutinesApi::class)
class UserUseCaseTest1 {

    private val testDispatcher = TestCoroutineDispatcher()

    private var userUseCase = spyk(UserUseCaseImpl(UserRepositoryImpl()))

    @Before
    fun setUp() {
        // Set Main dispatcher to TestCoroutineDispatcher
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @After
    fun reset() {
        // Reset the dispatcher
        kotlinx.coroutines.Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    /**
     *  Correctness by spyk
     */
    @Test
    fun `getAllUsers emits usersFlow with list of users`() = runTest {

        // Mock data
        val mockUsers = listOf(
            User("Omid", "Sadr", "30", "USA", "New York"),
            User("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
            User("Alireza", "Ganbari", "40", "Iran", "Tehran"),
            User("Hasan", "Azimi", "35", "Iran", "Tehran"),
            User("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
            User("Parsia", "Dolati", "45", "France", "Paris"),
            User("Zahra", "Eslami", "32", "India", "Mumbai")
        )

        val list = userUseCase.getAllUsers().first()
        assertEquals(mockUsers, list)

        /**
         * Method Calls
         */
        coVerify(exactly = 1) { userUseCase.getAllUsers() }

        // Advance coroutine until idle to ensure completion
        advanceUntilIdle()
    }


}









/**
 * Correctness by mockK
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserUseCaseTest2 {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val mockkRule = MockKRule(this)

    private var userUseCase = mockk<UserUseCaseImpl>()

    @Before
    fun setUp() {
        // Set Main dispatcher to TestCoroutineDispatcher
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @After
    fun reset() {
        // Reset the dispatcher
        kotlinx.coroutines.Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getAllUsers emits usersFlow with list of users`() = runTest {

        // Mock data
        val mockUsers = listOf(
            User("Omid", "Sadr", "30", "USA", "New York"),
            User("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
            User("Alireza", "Ganbari", "40", "Iran", "Tehran"),
            User("Hasan", "Azimi", "35", "Iran", "Tehran"),
            User("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
            User("Parsia", "Dolati", "45", "France", "Paris"),
            User("Zahra", "Eslami", "32", "India", "Mumbai")
        )

        coEvery { userUseCase.getAllUsers() } returns flowOf(mockUsers)

        val list = userUseCase.getAllUsers().first()
        assertEquals(mockUsers, list)

        /**
         * Method Calls
         */
        coVerify(exactly = 1) { userUseCase.getAllUsers() }

        // Advance coroutine until idle to ensure completion
        advanceUntilIdle()
    }


    @Test
    fun `Users should be Young`() = runTest {

        // Mock data
        val mockUsers = listOf(
            User("Omid", "Sadr", "30", "USA", "New York"),
            User("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
            User("Alireza", "Ganbari", "40", "Iran", "Tehran"),
            User("Hasan", "Azimi", "35", "Iran", "Tehran"),
            User("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
            User("Parsia", "Dolati", "45", "France", "Paris"),
            User("Zahra", "Eslami", "32", "India", "Mumbai")
        )

        coEvery { userUseCase.getAllUsers() } returns flowOf(mockUsers)

        val list = userUseCase.getAllUsers().first()
        val youngs = list.filter { it.age.toInt() > 19 }
        assertTrue(youngs.isNotEmpty())

        /**
         * Method Calls
         */
        coVerify(exactly = 1) { userUseCase.getAllUsers() }

        // Advance coroutine until idle to ensure completion
        advanceUntilIdle()

    }


    @Test
    fun `Users should be child`() = runTest {

        // Mock data
        val mockUsers = listOf(
            User("Omid", "Sadr", "30", "USA", "New York"),
            User("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
            User("Alireza", "Ganbari", "40", "Iran", "Tehran"),
            User("Hasan", "Azimi", "35", "Iran", "Tehran"),
            User("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
            User("Parsia", "Dolati", "45", "France", "Paris"),
            User("Zahra", "Eslami", "32", "India", "Mumbai")
        )

        coEvery { userUseCase.getAllUsers() } returns flowOf(mockUsers)

        val list = userUseCase.getAllUsers().first()
        val teenage = list.find { it.age.toInt() < 19 }
        assertTrue(teenage == null)

        /**
         * Method Calls
         */
        coVerify(exactly = 1) { userUseCase.getAllUsers() }

        // Advance coroutine until idle to ensure completion
        advanceUntilIdle()

    }


    @Test
    fun `There must be a user from Iran`() = runTest {

        // Mock data
        val mockUsers = listOf(
            User("Omid", "Sadr", "30", "USA", "New York"),
            User("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
            User("Alireza", "Ganbari", "40", "Iran", "Tehran"),
            User("Hasan", "Azimi", "35", "Iran", "Tehran"),
            User("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
            User("Parsia", "Dolati", "45", "France", "Paris"),
            User("Zahra", "Eslami", "32", "India", "Mumbai")
        )

        coEvery { userUseCase.getAllUsers() } returns flowOf(mockUsers)

        val list = userUseCase.getAllUsers().first()
        val thereIs = list.find { it.fromCountry.equals("Iran") }
        assertTrue(thereIs != null)

        /**
         * Method Calls
         */
        coVerify(exactly = 1) { userUseCase.getAllUsers() }

        // Advance coroutine until idle to ensure completion
        advanceUntilIdle()

    }


    /**
     * Performance and Timeouts Test
     */
    @Test(expected = TimeoutCancellationException::class)
    fun `time out time testing`() = runTest {
        withTimeout(100) {
            // Mock data
            val mockUsers = listOf(
                User("Omid", "Sadr", "30", "USA", "New York"),
                User("Pejman", "Pajoohi", "25", "Canada", "Toronto"),
                User("Alireza", "Ganbari", "40", "Iran", "Tehran"),
                User("Hasan", "Azimi", "35", "Iran", "Tehran"),
                User("Sobhan", "Hasanvand", "32", "Japan", "Tokyo"),
                User("Parsia", "Dolati", "45", "France", "Paris"),
                User("Zahra", "Eslami", "32", "India", "Mumbai")
            )

            coEvery { userUseCase.getAllUsers() } returns flowOf(mockUsers)

            // Introduce a delay longer than the timeout to force TimeoutCancellationException
            delay(90)

            val list = userUseCase.getAllUsers().first()
            val thereIs = list.find { it.fromCountry.equals("Iran") }
            assertTrue(thereIs != null)

            /**
             * Method Calls
             */
            coVerify(exactly = 1) { userUseCase.getAllUsers() }

            // Advance coroutine until idle to ensure completion
            advanceUntilIdle()
        }
    }


}


