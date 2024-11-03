package ir.ha.meproject

import android.util.Log
import androidx.test.filters.MediumTest
import ir.ha.meproject.presentation.features.fragments.temp3.Temp3Fragment
import org.junit.After
import org.junit.Before
import org.junit.Test

@MediumTest
class Temp3FragmentUiTest {

    val TAG = this::class.java.simpleName


    @Before
    fun setup(){
        Log.i(TAG, "setup: ")
    }


    @After
    fun traDown(){
        Log.i(TAG, "traDown: ")
    }



    @Test
    fun doSomething(){
        val activityScenario = launchFragmentInContainer<Temp3Fragment>()
    }

}