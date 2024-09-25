package ir.ha.meproject.common.file

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object AssetHelper {

    // Copy asset file to internal storage and return its file path
    fun getFilePathFromAssets(context: Context, assetFileName: String): String? {
        // Create a new file in internal storage
        val file = File(context.filesDir, assetFileName)
        try {
            // Open an input stream to read from the asset file
            val inputStream = context.assets.open(assetFileName)

            // Open an output stream to write to the internal file
            val outputStream = FileOutputStream(file)

            // Copy the data from the input stream to the output stream
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            // Close streams
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            // Return the file path of the copied asset file
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle error
            return null
        }
    }



    fun readJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }


    inline fun <reified T> parseJson(classLoader:ClassLoader?,fileName:String):T{
        println("parseJson")
        val inputStream = classLoader?.getResourceAsStream(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
//    println("reader ${readTextFile(inputStream!!)}")
        val gson = Gson()
        val dataClass = gson.fromJson(reader.readText(),T::class.java)
        println("final dataClass: $dataClass")
        return dataClass
    }


}