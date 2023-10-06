package athul.pizza.checkout.ui.beans

/**
 * Data class to hold UI state
 */
data class ProductUIData(
    val items:List<ProductItemUI>?=null,
    val discountGroups:List<String>?=null,
    var currentSelectedDiscountGroup:String?=null
)

data class ProductItemUI(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val priceUnit: String,
    val image: String,
    var itemCount:Int
)

