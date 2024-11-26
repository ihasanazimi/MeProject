package ir.ha.meproject.data.repository

import kotlin.random.Random

interface SampleRepository {

    fun getRandomNumber() : Int

    fun getNumberByAnswers() : List<Array<Any>>
}



class SampleRepositoryImpl : SampleRepository {

    override fun getRandomNumber(): Int {
        return Random.nextInt(0,100)
    }


    override fun getNumberByAnswers(): List<Array<Any>> {
        return listOf(
            arrayOf(-1,false),
            arrayOf(3,true),
            arrayOf(1,true),
            arrayOf(13,true)
        )
    }
}


