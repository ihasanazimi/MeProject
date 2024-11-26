package ir.ha.meproject.samples.validations

import ir.ha.meproject.common.extensions.isValidBankCardNumber
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class BankCardNumberTest {

    @Test
    fun `Card number length must be 16 characters`(){
        val cn1 = "5894631560043432"
        val cn2 = "50222910483654700"
        assertTrue(isValidBankCardNumber(cn1))
        assertFalse(isValidBankCardNumber(cn2))
    }

    @Test
    fun `Card number must start with 4, 5, or 6`(){
        val cn1 = "5894631560043432"
        val cn2 = "8022291048365470"
        assertTrue(isValidBankCardNumber(cn1))
        assertFalse(isValidBankCardNumber(cn2))
    }

    @Test
    fun `card_number_with_six_consecutive_start_is valid`(){
        val cn1 = "5894-6315-6004-3432"
        val cn2 = "502229******5470"
        val cn3 = "5022-29**-****-5470"
        val cn4 = "5894631560043432"
        assertTrue(isValidBankCardNumber(cn1))
        assertTrue(isValidBankCardNumber(cn2))
        assertTrue(isValidBankCardNumber(cn3))
        assertTrue(isValidBankCardNumber(cn4))
    }


    @Test
    fun `testInvalidAlgorithm`() {
        val cn = "4532015112830367"
        assertFalse(isValidBankCardNumber(cn))
    }


    @Test
    fun `testValidAlgorithm`() {
        val cn = "4532015112830366"
        assertTrue(isValidBankCardNumber(cn))
    }


}














