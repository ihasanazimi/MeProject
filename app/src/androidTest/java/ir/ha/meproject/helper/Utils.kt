package ir.ha.meproject.helper

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltTestApplication
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun readStringFromFile(fileName: String): String {
        try {
            val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as HiltTestApplication).assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }


    fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }




    inline fun <reified T> parseJsonFromAssets(context: Context, fileName: String): T? {
        return try {
            // Open the JSON file in assets
            val inputStream = context.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = bufferedReader.use { it.readText() } // Read the JSON file as a string

            // Parse the JSON string into the specified data class
            Gson().fromJson(jsonString, T::class.java)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

}



