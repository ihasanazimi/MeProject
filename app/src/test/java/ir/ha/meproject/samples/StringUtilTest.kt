package ir.ha.meproject.samples

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.clearAllCaches
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

class StringUtil (private val operators: StringOperators) {
    fun addSomeWord(a: String) = StringOperators.addWord(a)
}

object StringOperators {
    fun addWord(word : String) = "this is a test for -> $word"
}


class StringUtilTest {

    lateinit var stringUtil: StringUtil
    lateinit var operators: StringOperators

    @Before
    fun onSetup() {
        operators = Mockito.mock(StringOperators::class.java)
        stringUtil = StringUtil(operators)
    }

    @Test
    fun addName_and_printValue() {

        val name = "Farzad"

        `when`(StringOperators.addWord(name)).thenReturn(name)

        val result = stringUtil.addSomeWord(name)

        println("result value is : $result")
        assertEquals(result,name)

    }
}


class StringUtilTest2 {

    private lateinit var stringUtil: StringUtil
    private val operators = StringOperators

    @Before
    fun onSetup() {
        stringUtil = StringUtil(operators)
    }


    @After
    fun onRest(){
        clearAllCaches()
    }

    @Test
    fun addName_and_printValue() {
        val name = "Farzad"
        val result = stringUtil.addSomeWord(name)
        println("result value is : $result")
        assertEquals("this is a test for -> $name", result)
    }
}