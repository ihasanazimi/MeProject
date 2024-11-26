package ir.ha.meproject.samples

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import ir.ha.meproject.helper.BaseTest
import org.junit.Test
import kotlin.test.assertEquals

class Calculator (private val operators: Operators) {
    fun addTwoNumbers(a: Int, b: Int): Int = operators.addTwoInt(a, b)
}

object Operators {
    fun addTwoInt(m: Int, n: Int): Int = m - n
}

class CalculatorTest : BaseTest(){

    private lateinit var calculator: Calculator
    private lateinit var operator: Operators

    override fun setup() {
        super.setup()
        operator = mockk()
        calculator = spyk(Calculator(operator))
    }

    @Test
    fun addTwoNumber_PrintValue() {
        val a = 100
        val b = 20
        every { operator.addTwoInt(a, b) } returns (a + b)
        val result = calculator.addTwoNumbers(a, b)
        println(" after add two number : $result")
        assertEquals(result,120)

    }
}