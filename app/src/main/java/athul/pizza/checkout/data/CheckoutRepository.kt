package athul.pizza.checkout.data

import android.app.Application
import android.content.Context
import athul.pizza.checkout.R
import athul.pizza.checkout.data.models.BuyData
import athul.pizza.checkout.data.models.ProductData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.jetbrains.annotations.VisibleForTesting

/**
 * Core class responsible for fetching products and discount data.
 * Also calculates the total amount to buy including the discount.
 */
class CheckoutRepository(private val context: Context) {

    @VisibleForTesting
    internal var productsData :ProductData = fetchProductsData()

    /**
     * Fetches product data from response.json
     * In the real world scenario, The product and discounts data should be
     * served from other external sources such as cloud server, databases, SaaS like Firebase etc.,
     */
    @VisibleForTesting()
    internal fun fetchProductsData():ProductData {
        val data = context.getJsonFromAssets("response.json")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(ProductData::class.java)
        return data.let { adapter.fromJson(it) ?: ProductData(emptyList(), emptyList()) }
    }

    /**
     * Calculates the total amount to buy for an item with specified count.
     * In real world scenario, This function should be in a server side rather than the app side.
     * @param itemId id of the item selected
     * @param count quantity of the item selected
     * @param discountGrop currently selected discount group. Can be null if not selected any.
     */
    fun getTotalAmountToBuyItem(itemId:Int,count:Int, discountGrop:String?): BuyData? {
        val discountDetailOfGroup = productsData.discountData.firstOrNull { it.discountGroup == discountGrop }
        val item = productsData.products.first { it.id == itemId }
        if (count<=0){
            return null
        }
        //eligible for discount
        if (discountDetailOfGroup?.productIds?.contains(itemId) == true){
            return when(discountDetailOfGroup.type){
                DISCOUNT_TYPE_GET_X_FOR_Y -> {
                    val forQuantity = discountDetailOfGroup.data?.forQuantity ?: 0
                    val getQuantity = discountDetailOfGroup.data?.getQuantity ?: 0

                    //price
                    val oldTotal = item.price*count
                    val newPrice = ((count/getQuantity)*(forQuantity*item.price))+((count%getQuantity)*item.price)

                    val savings = (oldTotal-newPrice)
                    BuyData(
                        totalAmountToBuy = newPrice.round(),
                        discountMessage =if (savings>0) context.getString(R.string.discount_message_get_x_for_y,savings.round().toString()) else null,
                        totalPizzas = count
                    )
                }
                DISCOUNT_TYPE_PRICE_DROP -> {
                    val newPrice = item.price - (discountDetailOfGroup.data?.droppedAmount ?: 0)
                    val oldTotal = item.price * count
                    val totalAmount = (newPrice * count).round()
                    val discountMessage = context.getString(R.string.discount_message_price_drop,(oldTotal-totalAmount).round().toString())
                    BuyData(
                        totalAmountToBuy = totalAmount,
                        discountMessage = discountMessage,
                        totalPizzas = count
                    )
                }
                // unknown or no discount type
                else ->{
                    val totalAmount = item.price * count
                    BuyData(
                        totalAmount.round(),
                        totalPizzas = count
                    )
                }
            }
        }
        return BuyData(
            totalAmountToBuy = (item.price * count).round(),
            totalPizzas = count
        )

    }

}