package athul.pizza.checkout.data.models

data class BuyData(
    val totalAmountToBuy:Double=0.0,
    val discountMessage:String?=null,
    var totalPizzas:Int
)
