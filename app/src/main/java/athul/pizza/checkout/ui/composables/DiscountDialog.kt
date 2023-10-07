package athul.pizza.checkout.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import athul.pizza.checkout.R
import athul.pizza.checkout.ui.theme.black
import athul.pizza.checkout.ui.theme.green

@Composable
fun DiscountDialog(data:List<String>,current:String?=null,onSelected:(String?)->Unit){
    Dialog(onDismissRequest = {  }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(Modifier.padding(16.dp)) {
                data.forEach {value->
                    ClickableText(text = AnnotatedString(value), onClick ={onSelected.invoke(value)}, modifier = Modifier.padding(8.dp), style = TextStyle(
                        color = if (value ==current) green else black
                    ))
                }

                ClickableText(text = AnnotatedString(LocalContext.current.getString(R.string.clear_all)), onClick ={onSelected.invoke(null)}, modifier = Modifier.padding(top = 16.dp, start = 8.dp), style = TextStyle(
                    color = black
                ))
            }
        }
    }
}