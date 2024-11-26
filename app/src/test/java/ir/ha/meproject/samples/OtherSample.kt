package ir.ha.meproject.samples

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import ir.ha.meproject.helper.BaseTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import kotlin.test.Test


class StringUtil(private val operators: StringOperators) {
    fun addSomeWord(a: String) = operators.addWord(a)
}

object StringOperators {
    fun addWord(word: String) = "this is a test for -> $word"
}

class Example1 {

    lateinit var stringUtil: StringUtil
    lateinit var operators: StringOperators

    @Before
    fun onSetup() {
        operators = mockk()
        stringUtil = spyk(StringUtil(operators))
    }

    @org.junit.Test
    fun addName_and_printValue() {
        val name = "Farzad"
        every { StringOperators.addWord(name) } returns name
        val result = stringUtil.addSomeWord(name)
        println("result value is : $result")
        kotlin.test.assertEquals(result, name)

    }
}

class Example2 : BaseTest() {

    private lateinit var stringUtil: StringUtil
    private val operators = StringOperators

    override fun setup() {
        super.setup()
        stringUtil = spyk(StringUtil(operators))
    }

    @org.junit.Test
    fun addName_and_printValue() {
        val name = "Farzad"
        val result = stringUtil.addSomeWord(name)
        println("result value is : $result")
        kotlin.test.assertEquals("this is a test for -> $name", result)
    }
}


class Dependency {
    fun getData(): String {
        return "data from dependency"
    }
}

class MyClass(private val dependency: Dependency) {
    fun doSomething(): String {
        val data = dependency.getData()
        return "processed $data"
    }
}

class MyClassTest {
    @Test
    fun `test doSomething`() {
        val dependencyMock = mockk<Dependency>()
        every { dependencyMock.getData() } returns "mocked data"
        val myClass = MyClass(dependencyMock)
        val result = myClass.doSomething()
        assertEquals("processed mocked data", result)
        confirmVerified()
    }
}


// Handling exceptions in unit tests
class IndexOutOfBoundExceptionExample {
    private val array = intArrayOf(1, 2, 3)   // index is 0,1,2 value 1,2,3
    fun getValue(index: Int): Int {
        return array[index]
    }
}

class IndexOutOfBoundExceptionExampleTest {
    @org.junit.Test
    fun testException_IndexOutOfBound1() {
        val ob = IndexOutOfBoundExceptionExample()
        try {
            val value = ob.getValue(3)
            println("value of getValue() is : $value")
        } catch (e: Exception) {
            println(e.message)
        }
    }

    @org.junit.Test
    fun testException_IndexOutOfBound2() {
        val ob = IndexOutOfBoundExceptionExample()
        try {
            val value = ob.getValue(2)
            println("value of getValue() is : $value")
        } catch (e: Exception) {
            println(e.message)
        }
    }


}