package ir.ha.meproject.ui.fragments.temp1

import ir.ha.meproject.databinding.FragmentTemp1Binding
import ir.ha.meproject.utility.base.BaseFragment
import org.junit.Assert
import org.junit.Test

class Temp1Fragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    override fun listeners() {
        super.listeners()


        binding.add.setOnClickListener {
            binding.resultTV.setText(calculate(true,5L , 2L).toString())
        }


        binding.low.setOnClickListener {
            binding.resultTV.setText(calculate(false,5L , 2L).toString())
        }


    }


    private fun calculate(add : Boolean , max : Long, min : Long) : Long{
        return if (add) max+min else max-min
    }


    @Test
    fun isBiggerTest(): Unit {
        val X = calculate(true,80,4)
        Assert.assertTrue(X>83)
    }


    @Test
    fun isSmallest() : Unit{
        val Y = calculate(false , 4,3)
        Assert.assertFalse(Y<1)
    }


    @Test
    fun isValidPeriod() : Unit {
        var array1 = arrayOf(1,2,3)
        var array2 = arrayOf(1,2,3)
        Assert.assertArrayEquals(array1,array2)
    }


}