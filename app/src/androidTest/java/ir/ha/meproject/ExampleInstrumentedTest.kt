package ir.ha.meproject

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)


class AllTest {

    val postalCodeValidatorTest = PostalCodeValidatorTest()

    @org.junit.Test
    fun RunAllTests() {
        postalCodeValidatorTest.postalCode_isValid_whenAllRulesAreMet()
    }


}