package athul.pizza.checkout.data

import android.content.Context
import athul.pizza.checkout.data.models.ProductData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.nio.charset.Charset

/**
 * Gets product data from response.json
 * In the real world scenario, The product and discounts data should be
 * served from other external sources such as cloud server, databases etc.,
 */
suspend fun Context.getProductsAndDiscountData():ProductData?{
    val data = getJsonFromAssets("response.json")
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter = moshi.adapter(ProductData::class.java)
    return data?.let { adapter.fromJson(it) }
}

/**
 * Utility function to read file in asset folder to String
 */
fun Context.getJsonFromAssets(fileName: String): String? {
    val jsonString: String
    try {
        val inputStream = assets.open(fileName)

        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        jsonString = String(buffer, Charset.defaultCharset())
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    return jsonString
}