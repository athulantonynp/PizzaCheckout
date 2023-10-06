 package athul.pizza.checkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import athul.pizza.checkout.data.getProductsAndDiscountData
import athul.pizza.checkout.ui.theme.PizzaCheckoutTheme
import athul.pizza.checkout.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch

 /**
  * Class for Main UI of the app. Since its a single page app, No need of fragments.
  */
 class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel  by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaCheckoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}