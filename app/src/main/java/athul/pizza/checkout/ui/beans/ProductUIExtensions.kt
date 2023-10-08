package athul.pizza.checkout.ui.beans

import athul.pizza.checkout.data.models.Product

fun Product.toProductItemUI(itemCount: Int = 0): ProductItemUI {
    return ProductItemUI(
        id, name, description, price, priceUnit, image, itemCount
    )
}