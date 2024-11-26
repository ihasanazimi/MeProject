package ir.ha.meproject.samples

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import ir.ha.meproject.helper.BaseTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CalculatorExample (private val operators: Operators) {
    fun addTwoNumbers(a: Int, b: Int): Int = operators.addTwoInt(a, b)
}

object Operators {
    fun addTwoInt(m: Int, n: Int): Int = m - n
}



class CalculatorExampleTest : BaseTest(){

    lateinit var CE: CalculatorExample
    lateinit var OP: Operators

    override fun setup() {
        super.setup()
        OP = mockk()
        CE = spyk(CalculatorExample(OP))
    }

    @Test
    fun addTwoNumber_PrintValue() {

        val a = 100
        val b = 20

        every { OP.addTwoInt(a, b) } returns (a + b)

        val result = CE.addTwoNumbers(a, b)
        println(" after add two number : $result")
        assertEquals(result,120)

    }
}