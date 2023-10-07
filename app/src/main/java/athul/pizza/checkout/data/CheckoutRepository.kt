package athul.pizza.checkout.data

import android.app.Application
import athul.pizza.checkout.R
import athul.pizza.checkout.data.models.BuyData
import athul.pizza.checkout.data.models.ProductData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CheckoutRepository(private val app: Application) {

    val productsData by lazy { fetchProductsData() }

    /**
     * Fetches product data from response.json
     * In the real world scenario, The product and discounts data should be
     * served from other external sources such as cloud server, databases, SaaS like Firebase etc.,
     */
    private fun fetchProductsData():ProductData {
        val data = app.getJsonFromAssets("response.json")
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
    fun getTotalAmountToBuyItem(itemId:Int,count:Int, discountGrop:String?): BuyData {
        val discountDetailOfGroup = productsData.discountData.first { it.discountGroup == discountGrop }
        val item = productsData.products.first { it.id == itemId }

        //eligible for discount
        if (discountDetailOfGroup.productIds.contains(itemId)){
            var totalAmount = 0.0
            var discountMessage:String? = null
            when(discountDetailOfGroup.type){
                DISCOUNT_TYPE_BUY_X_GET_Y -> {
                    val price = item.price
                    val buy = discountDetailOfGroup.data?.buy ?: 0
                    val get = discountDetailOfGroup.data?.get ?: 0

                    return if (count<buy){
                        BuyData(price*count, totalPizzas = count)
                    }else{
                        val bonusPizzas = (count/buy)*get
                        val leftOverPizza = (count%buy)
                        val totalPizzas = bonusPizzas + leftOverPizza

                        BuyData(
                            totalAmountToBuy = count * price,
                            discountMessage = app.getString(R.string.discount_message_buy_x_get_y,totalPizzas,
                                item.name,count),
                            totalPizzas = totalPizzas.toInt()
                        )
                    }
                }
                DISCOUNT_TYPE_PRICE_DROP -> {
                    val newPrice = item.price - (discountDetailOfGroup.data?.droppedAmount ?: 0)
                    totalAmount = newPrice * count
                    discountMessage = app.getString(R.string.discount_message_price_drop,totalAmount)
                    return BuyData(
                        totalAmountToBuy = totalAmount,
                        discountMessage = discountMessage,
                        totalPizzas = count
                    )
                }
                // unknown or no discount type
                else ->{
                    totalAmount = item.price * count
                    return BuyData(
                        totalAmount,
                        totalPizzas = count
                    )
                }
            }
        }else{
            return BuyData(
                totalAmountToBuy = item.price * count,
                totalPizzas = count
            )
        }

    }

}