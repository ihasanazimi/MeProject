package ir.ha.meproject.domain

import ir.ha.meproject.data.repository.NumberRepositoryImpl
import javax.inject.Inject

interface NumberUseCase {

    fun getRandomNumber() : Int

    fun isPositiveNumber(number : Int) : Boolean

    fun isOddNumber(number : Int) : Boolean

    fun isEvenNumber(number : Int) : Boolean

    fun getNumberByAnswers() : List<Any>

}



class NumberUseCaseImpl @Inject constructor(
    private val numberRepositoryImpl: NumberRepositoryImpl
) : NumberUseCase {

    override fun getRandomNumber(): Int {
        return numberRepositoryImpl.getRandomNumber()
    }

    override fun isPositiveNumber(number: Int): Boolean {
        return number >= 0
    }

    override fun isEvenNumber(number: Int): Boolean {
        return number % 2 == 0
    }

    override fun isOddNumber(number: Int): Boolean {
        return number % 2 != 0
    }

    override fun getNumberByAnswers(): List<Array<Any>> {
        return numberRepositoryImpl.getNumberByAnswers()
    }
}