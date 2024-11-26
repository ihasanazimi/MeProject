package ir.ha.meproject.helper

import android.util.Log
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockkStatic
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @Before
    open fun setup() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }


    @After
    open fun tearDown(){
        clearAllMocks()
    }
}