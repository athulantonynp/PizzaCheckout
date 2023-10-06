package athul.pizza.checkout.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductData(
    @Json(name = "products")
    val products: List<Product>,
    @Json(name = "discountData")
    val discountData: List<DiscountData>,
)

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "priceUnit")
    val priceUnit: String,
    @Json(name = "image")
    val image: String,
)

@JsonClass(generateAdapter = true)
data class DiscountData(
    @Json(name = "type")
    val type: String,
    @Json(name = "discountGroup")
    val discountGroup: String,
    @Json(name = "productIds")
    val productIds: List<Long>,
    @Json(name = "data")
    val data: Data?,
)

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "buy")
    val buy: Long?,
    @Json(name = "get")
    val get: Long?,
    @Json(name = "droppedAmount")
    val droppedAmount: Long?,
)
