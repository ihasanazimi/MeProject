package ir.ha.meproject.samples

import org.junit.Test


// Handling exceptions in unit tests

class IndexOutOfBoundExceptionExample {
    val array = intArrayOf(1, 2, 3)   // index is 0,1,2 value 1,2,3

    fun getValue(index : Int) : Int{
        return array[index]
    }
}


class IndexOutOfBoundExceptionExampleTest {
    @Test
    fun testException_IndexOutOfBound1() {
        val ob = IndexOutOfBoundExceptionExample()
        try {
            val value = ob.getValue(3)
            println("value of getValue() is : $value")
        }catch (e : Exception){
            println(e.message)
        }
    }

    @Test
    fun testException_IndexOutOfBound2() {
        val ob = IndexOutOfBoundExceptionExample()
        try {
            val value = ob.getValue(2)
            println("value of getvalue() is : $value")
        }catch (e : Exception){
            println(e.message)
        }
    }


}



