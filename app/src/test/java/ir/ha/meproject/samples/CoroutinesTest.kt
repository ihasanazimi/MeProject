package ir.ha.meproject.samples

import ir.ha.meproject.helper.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesTest : BaseTest() {

    @Test
    fun testDelayedOperation() = runTest {

        var result = false
        launch {
            delay(1000) // Delay for 1 second
            result = true
        }

        advanceTimeBy(999)
        assertFalse(result) // The operation hasn't completed yet

        advanceTimeBy(1)
        yield() // Allow the scheduled coroutine to complete
        assertTrue(result) // Now it has completed
    }


    @Test
    fun `test coroutine exception`() = runTest {
        assertFailsWith<IllegalArgumentException> {
            launch {
                // Simulate an exception being thrown in the coroutine
                throw IllegalArgumentException("Invalid argument")
            }.join() // Make sure the coroutine completes
        }
    }


}




class CoroutineTest {

    @Test
    fun testSimpleCoroutine() = runTest {
        val result = async {
            delay(100)
            "Hello, World!"
        }
        assertEquals("Hello, World!", result.await())
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testWithVirtualTime() = runTest {
        val dispatcher = StandardTestDispatcher()
        val startTime = dispatcher.scheduler.currentTime
        launch {
            delay(1000)
            assertEquals(startTime + 1000, dispatcher.scheduler.currentTime)
        }
        dispatcher.scheduler.advanceTimeBy(1000) /** add 1000MS to virtual time for coroutines scope delay */


        /**
         * @OptIn(ExperimentalCoroutinesApi::class)
         *  use it for testing kotlin coroutines procession
         *  because may be dispatcher.scheduler.advanceTimeBy(1000) not stabled!
         */
    }
}