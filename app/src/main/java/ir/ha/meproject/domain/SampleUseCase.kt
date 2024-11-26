package ir.ha.meproject.domain

import ir.ha.meproject.data.repository.SampleRepositoryImpl

interface SampleUseCase {

    fun getRandomNumber() : Int

    fun isPositiveNumber(number : Int) : Boolean

    fun isOddNumber(number : Int) : Boolean


    fun isEvenNumber(number : Int) : Boolean

    fun getNumberByAnswers() : List<Any>

}



class SampleUseCaseImpl(
    private val sampleRepositoryImpl: SampleRepositoryImpl
) : SampleUseCase {

    override fun getRandomNumber(): Int {
        return sampleRepositoryImpl.getRandomNumber()
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
        return sampleRepositoryImpl.getNumberByAnswers()
    }
}