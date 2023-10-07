package athul.pizza.checkout.data

import android.content.Context
import athul.pizza.checkout.data.models.ProductData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.nio.charset.Charset

/**
 * Utility function to read file in asset folder to String
 */
fun Context.getJsonFromAssets(fileName: String): String {
    var jsonString="{}"
    try {
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        jsonString = String(buffer, Charset.defaultCharset())
    } catch (e: IOException) {
        e.printStackTrace()
        return "{}"
    }
    return jsonString
}