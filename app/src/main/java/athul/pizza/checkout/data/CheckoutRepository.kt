package athul.pizza.checkout.data

import android.app.Application
import athul.pizza.checkout.data.models.ProductData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CheckoutRepository(private val app: Application) {

    /**
     * Gets product data from response.json
     * In the real world scenario, The product and discounts data should be
     * served from other external sources such as cloud server, databases etc.,
     */
    fun getProductsData():ProductData? {
        val data = app.getJsonFromAssets("response.json")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(ProductData::class.java)
        return data?.let { adapter.fromJson(it) }
    }

}