package athul.pizza.checkout.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import athul.pizza.checkout.ui.theme.black
import athul.pizza.checkout.ui.theme.white

@Composable
fun Counter(value: Int = 0, onValueDecreaseClick: () -> Unit, onValueIncreaseClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundButton(text = "-") {
            onValueDecreaseClick.invoke()
        }
        Text(
            text = value.toString(),
            modifier = Modifier.padding(horizontal = 8.dp),
            fontWeight = FontWeight.Bold
        )
        RoundButton(text = "+") {
            onValueIncreaseClick.invoke()
        }
    }
}

@Composable
fun RoundButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick.invoke() },
        modifier = Modifier
            .wrapContentSize(Alignment.Center),
        colors = ButtonDefaults.buttonColors(
            containerColor = black,
            contentColor = white
        )
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}