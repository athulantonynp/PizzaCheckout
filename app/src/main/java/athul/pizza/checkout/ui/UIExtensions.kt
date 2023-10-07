package athul.pizza.checkout.ui
import androidx.compose.ui.graphics.Color

val String.composeColor
    get() = Color(android.graphics.Color.parseColor(this))
