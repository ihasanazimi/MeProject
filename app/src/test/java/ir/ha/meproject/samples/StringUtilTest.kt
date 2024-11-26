package ir.ha.meproject.samples

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import ir.ha.meproject.helper.BaseTest
import org.junit.After
import org.junit.Before
import org.junit.Test
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
        operators = mockk()
        stringUtil = StringUtil(operators)
    }

    @Test
    fun addName_and_printValue() {

        val name = "Farzad"
        every { StringOperators.addWord(name) } returns name
        val result = stringUtil.addSomeWord(name)
        println("result value is : $result")
        assertEquals(result,name)

    }
}


class StringUtilTest2 : BaseTest() {

    private lateinit var stringUtil: StringUtil
    private val operators = StringOperators

    override fun setup() {
        super.setup()
        stringUtil = StringUtil(operators)
    }

    @Test
    fun addName_and_printValue() {
        val name = "Farzad"
        val result = stringUtil.addSomeWord(name)
        println("result value is : $result")
        assertEquals("this is a test for -> $name", result)
    }
}