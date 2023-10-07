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
    fun getTotalAmountToBuyItem(itemId:Int,count:Int, discountGrop:String?): BuyData? {
        val discountDetailOfGroup = productsData.discountData.firstOrNull { it.discountGroup == discountGrop }
        val item = productsData.products.first { it.id == itemId }

        //eligible for discount
        if (discountDetailOfGroup?.productIds?.contains(itemId) == true){
            var discountMessage:String? = null
            when(discountDetailOfGroup.type){
                DISCOUNT_TYPE_BUY_X_GET_Y -> {
                    val price = item.price
                    val buy = discountDetailOfGroup.data?.buy ?: 0
                    val get = discountDetailOfGroup.data?.get ?: 0

                    return if (count<buy){
                        BuyData((price*count).round(), totalPizzas = count)
                    }else{

                        val bonusPizzas = (count/buy)*get
                        val leftPizzaWithoutBonus = (count%buy)
                        val totalPizzas = bonusPizzas + leftPizzaWithoutBonus

                        val totalAmount = ((count/buy)*(buy*item.price)) + (leftPizzaWithoutBonus*price)
                        BuyData(
                            totalAmountToBuy = totalAmount.round(),
                            discountMessage = app.getString(R.string.discount_message_buy_x_get_y,totalPizzas.toString(),
                                item.name,count.toString()),
                            totalPizzas = totalPizzas.toInt()
                        )
                    }
                }
                DISCOUNT_TYPE_PRICE_DROP -> {
                    return if (count>0){
                        val newPrice = item.price - (discountDetailOfGroup.data?.droppedAmount ?: 0)
                        val oldTotal = item.price * count
                        val totalAmount = (newPrice * count).round()
                        discountMessage = app.getString(R.string.discount_message_price_drop,(oldTotal-totalAmount).round().toString())
                        BuyData(
                            totalAmountToBuy = totalAmount,
                            discountMessage = discountMessage,
                            totalPizzas = count
                        )
                    }else{
                        null
                    }
                }
                // unknown or no discount type
                else ->{
                    val totalAmount = item.price * count
                    return BuyData(
                        totalAmount.round(),
                        totalPizzas = count
                    )
                }
            }
        }else{
            return BuyData(
                totalAmountToBuy = (item.price * count).round(),
                totalPizzas = count
            )
        }

    }

}