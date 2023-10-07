 package athul.pizza.checkout

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import athul.pizza.checkout.ui.composables.AppBar
import athul.pizza.checkout.ui.composables.CheckoutPage
import athul.pizza.checkout.ui.theme.PizzaCheckoutTheme
import athul.pizza.checkout.ui.viewmodels.MainViewModel

 /**
  * Class for Main UI of the app. Since its a single page app, No need of fragments.
  */
 class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel  by viewModels { MainViewModel.Factory }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaCheckoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                       topBar = {
                          AppBar(title = getString(R.string.app_name))
                       }
                    ) {
                        CheckoutPage(viewModel,it.calculateTopPadding())
                    }

                }
            }
        }
        viewModel.init()
    }
}
