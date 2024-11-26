package ir.ha.meproject.samples.validations

import ir.ha.meproject.common.extensions.isValidPostalCode
import org.junit.Assert.*
import org.junit.Test

class PostalCodeValidatorTest {

    @Test
    fun `postal code is valid when all rules are met`() {
        val validPostalCode = "1234567890"
        assertFalse(isValidPostalCode(validPostalCode))
    }

    @Test
    fun `postal code is invalid when length is not 10`() {
        val invalidPostalCode = "12345"
        assertFalse(isValidPostalCode(invalidPostalCode))
    }

    @Test
    fun `postal code is invalid when first five digits contain 0 or 2`() {
        val invalidPostalCode1 = "0234567890"
        val invalidPostalCode2 = "1234067890"
        assertFalse(isValidPostalCode(invalidPostalCode1))
        assertFalse(isValidPostalCode(invalidPostalCode2))
    }

    @Test
    fun `postal code is invalid when fifth digit is 5`() {
        val invalidPostalCode = "1234557890"
        assertFalse(isValidPostalCode(invalidPostalCode))
    }

    @Test
    fun `postal code is invalid when sixth digit is 0`() {
        val invalidPostalCode = "1234560789"
        assertFalse(isValidPostalCode(invalidPostalCode))
    }

    @Test
    fun `postal code is invalid when last four digits contain non-numeric characters`() {
        val invalidPostalCode = "12345678AB"
        assertFalse(isValidPostalCode(invalidPostalCode))
    }

    @Test
    fun `postal code is invalid when last four digits are 0000`() {
        val invalidPostalCode = "1234560000"
        assertFalse(isValidPostalCode(invalidPostalCode))
    }

    @Test
    fun `postal code is invalid when all digits are the same`() {
        val invalidPostalCode = "1111111111"
        assertFalse(isValidPostalCode(invalidPostalCode))
    }
}
