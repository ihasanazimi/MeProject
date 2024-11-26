package ir.ha.meproject.samples

import io.mockk.spyk
import ir.ha.meproject.data.repository.NumberRepositoryImpl
import ir.ha.meproject.domain.NumberUseCaseImpl
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

/*---------------------------------------------------------------------------*/


@RunWith(value = Parameterized::class)
class StringHelperParameterTest0 (private val input: Int, private val expectedValue: Boolean) {


    companion object {

        private lateinit var repo: NumberRepositoryImpl
        private lateinit var useCase: NumberUseCaseImpl

        @JvmStatic
        @Parameterized.Parameters()
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf(-8,false),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
                arrayOf(8,true),
            )
        }

        @JvmStatic
        @Parameterized.Parameters()
        fun hasan(): List<Array<Any>> {
            return listOf(
                arrayOf(-1,false),
                arrayOf(3,true),
                arrayOf(1,true),
                arrayOf(13,true)
            )
        }



    }


    @Test
    fun testParameterized_IsPositiveNumberOrNot() {
        repo = spyk(NumberRepositoryImpl())
        useCase = spyk(NumberUseCaseImpl(repo))
        val result = useCase.isPositiveNumber(input)
        println("result is $result")
        assertEquals(expectedValue,result)
    }

}



/*---------------------------------------------------------------------------*/


class StringHelperParameterTest1 {

    companion object {

        private lateinit var repo : NumberRepositoryImpl
        private lateinit var useCase : NumberUseCaseImpl

        @JvmStatic
        fun provideTestData() : List<Array<Any>> {
            repo = spyk(NumberRepositoryImpl())
            useCase = spyk(NumberUseCaseImpl(repo))
            return useCase.getNumberByAnswers()
        }
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    fun testIsPositiveNumber(number: Int, expected: Boolean) {
        val result = useCase.isPositiveNumber(number)
        println("number is $number and result is $result")
        Assertions.assertEquals(expected,result)
    }

}



/*---------------------------------------------------------------------------*/




class StringHelperParameterTest2 {

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 1, 2, 3])
    fun testIsPositiveNumber(number: Int) {
        val repo = spyk(NumberRepositoryImpl())
        val useCase = spyk(NumberUseCaseImpl(repo))
        val result = useCase.isPositiveNumber(number)
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
        val repo = spyk(NumberRepositoryImpl())
        val useCase = spyk(NumberUseCaseImpl(repo))
        val result = useCase.isPositiveNumber(input)
        println("input is $input and result is $result")
        assertEquals(expected, result)
    }
}



    /*---------------------------------------------------------------------------*/

    class MathUtils {
        fun factorial(n: Int): Int {
            require(n >= 0) { "Factorial is not defined for negative numbers." }
            var result = 1
            for (i in 1..n) {
                result *= i
            }
            return result
        }
    }

    class MathUtilsTest {

        private val mathUtil = spyk(MathUtils())

        companion object {
            @JvmStatic
            fun data(): List<Arguments> {
                return listOf(
                    Arguments.of(0, 1),
                    Arguments.of(1, 1),
                    Arguments.of(2, 2),
                    Arguments.of(3, 6),
                    Arguments.of(4, 24)
                )
            }
        }

        @ParameterizedTest
        @MethodSource("data")
        fun testFactorial1(input: Int, expected: Int) {
            println("input is -> $input  /  expected is ${expected}")
            assertEquals(expected, mathUtil.factorial(input))
        }
    }


    /*---------------------------------------------------------------------------*/

    class NumberUtils {
        fun isEven(number: Int): Boolean {
            return number % 2 == 0
        }
    }

    // Use Parameterized runner
    @RunWith(Parameterized::class)
    class NumberUtilsTest1(private val number: Int, private val isEvenExpected: Boolean) {

        private val numberUtils = spyk(NumberUtils())

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): Collection<Array<Any>> {
                return listOf(
                    arrayOf(2, true),
                    arrayOf(3, false),
                    arrayOf(4, true),
                    arrayOf(5, false)
                )
            }
        }

        @Test
        fun testIsEven() {
            println(
                "isEvenExpected is $isEvenExpected   /   numberUtils.isEven returned ${
                    numberUtils.isEven(
                        number
                    )
                }"
            )
            assertEquals(isEvenExpected, numberUtils.isEven(number))
        }
    }
