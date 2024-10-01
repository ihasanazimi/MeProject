package ir.ha.meproject

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

class CalculatorExample ( val operators:Operators) {
    fun addTwoNumbers(a: Int, b: Int): Int = operators.addTwoInt(a,b)
}
object Operators {
    fun addTwoInt(m: Int, n: Int): Int = m - n
}


@RunWith(MockitoJUnitRunner::class)
class CalculatorExampleTest {

    lateinit var CE: CalculatorExample
    lateinit var OP: Operators

    @Before
    fun onSetup() {
        OP = Mockito.mock(Operators::class.java)
        CE = CalculatorExample(OP)
    }

    @Test
    fun addTwoNumber_PrintValue() {
        val a = 100
        val b = 20

        `when`(OP.addTwoInt(a, b)).thenReturn(a + b)

        val result = CE.addTwoNumbers(a, b)

        println(" after add two number : $result")
        assertEquals(result,120)

    }
}