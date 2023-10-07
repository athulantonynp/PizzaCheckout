package athul.pizza.checkout.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import athul.pizza.checkout.ui.theme.black
import athul.pizza.checkout.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title:String){
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = black,
            titleContentColor = white
        ),
        title = {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    )
}