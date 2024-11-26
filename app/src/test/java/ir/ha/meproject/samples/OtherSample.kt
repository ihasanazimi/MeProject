package ir.ha.meproject.samples

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlin.test.Test


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