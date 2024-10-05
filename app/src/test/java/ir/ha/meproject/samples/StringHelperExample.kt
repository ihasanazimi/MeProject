package ir.ha.meproject.samples

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


/** JUnit5*/
class StringHelper {
    fun isPositiveNumber(number: Int): Boolean {
        return number >= 0
    }
}


/*---------------------------------------------------------------------------*/


@RunWith(value = Parameterized::class)
class StringHelperParameterTest0 (private val input: Int, private val expectedValue: Boolean){

    companion object {
        @JvmStatic
        @Parameterized.Parameters()
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf(-1,false),
                arrayOf(3,true)
            )
        }
    }


    @Test
    fun testParameterized_IsPositiveNumberOrNot() {
        val SH = StringHelper()
        val result = SH.isPositiveNumber(input)
        assertEquals(expectedValue,result)
    }

}



/*---------------------------------------------------------------------------*/



class StringHelperParameterTest1 {

    companion object {
        @JvmStatic
        fun provideTestData() = listOf(
            Arguments.of(-1, false),
            Arguments.of(0, false),
            Arguments.of(1, true)
        )
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    fun testIsPositiveNumber(number: Int, expected: Boolean) {
        val SH = StringHelper()
        val result = SH.isPositiveNumber(number)
        println("number is $number and result is $result")
        Assertions.assertEquals(expected,result)
    }

}



/*---------------------------------------------------------------------------*/




class StringHelperParameterTest2 {


    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 1, 2, 3])
    fun testIsPositiveNumber(number: Int) {
        val SH = StringHelper()
        val result = SH.isPositiveNumber(number)
        println("number is $number and result is $result")
        Assertions.assertEquals(number >= 0 , result)
    }

}


/*---------------------------------------------------------------------------*/


class StringHelperParameterTest3 {

    @ParameterizedTest
    @CsvSource(
        "-1,false",
        "0,true",
        "1,true"
    )
    fun testIsPositiveNumber(input: Int, expected: Boolean) {
        val sh = StringHelper()
        val result = sh.isPositiveNumber(input)
        assertEquals(expected, result)
    }
}