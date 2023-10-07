package athul.pizza.checkout.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import athul.pizza.checkout.R
import athul.pizza.checkout.ui.beans.ProductItemUI
import athul.pizza.checkout.ui.beans.ProductUIData
import athul.pizza.checkout.ui.theme.black
import athul.pizza.checkout.ui.theme.green
import athul.pizza.checkout.ui.theme.lightGray
import athul.pizza.checkout.ui.theme.white
import athul.pizza.checkout.ui.viewmodels.MainViewModel
import coil.compose.AsyncImage

@Composable
fun CheckoutPage(viewModel: MainViewModel, topPadding: Dp) {
    val productData = viewModel.uiDataFlow.collectAsState(initial = ProductUIData())
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = topPadding, bottom = 32.dp)) {
        ProductItems(productData.value.items,viewModel)
        DiscountSection(viewModel,productData.value)
        BuyNowSection(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscountSection(viewModel: MainViewModel, value: ProductUIData) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(containerColor = white),
        border = BorderStroke(1.dp, lightGray),
        onClick = {
            showDialog.value = true
        }
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Current Discount Group: ${value.currentSelectedDiscountGroup ?: "N/A"}",  color = black, fontWeight = FontWeight.Bold)
            Text(text = "Click to change", modifier = Modifier.padding(top = 8.dp))
        }
    }
    if (showDialog.value){
        DiscountDialog(data = value.discountGroups ?: emptyList(), current =value.currentSelectedDiscountGroup ){
            viewModel.updateDiscountGroup(it)
            showDialog.value = false
        }
    }
}

@Composable
fun BuyNowSection(viewModel: MainViewModel) {
    val data = viewModel.totalAmountFlow.collectAsState(initial = null)
    Column(modifier = Modifier.padding(top = 48.dp)) {
        if (data.value == null || data.value?.totalAmountToBuy?.toInt() == 0){
            EmptyCard()
        }else{
            BuyNowCard(data.value?.totalAmountToBuy,data.value?.totalPizzas,data.value?.discountMessage)
        }
    }
}

@Composable
fun BuyNowCard(totalAmountToBuy: Double?, totalPizzas: Int?, discountMessage: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(containerColor = black),
    ){
        Column( modifier = Modifier.padding(16.dp)) {
            Text(text = LocalContext.current.getString(R.string.you_will_get,totalPizzas),
                textAlign = TextAlign.Center,
                color = white, fontSize = 16.sp, modifier = Modifier.padding(vertical = 8.dp))

            if (!discountMessage.isNullOrEmpty()){
                Text(text = discountMessage,
                    textAlign = TextAlign.Center,
                    color = green, fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp),
                    fontWeight = FontWeight.Bold)
            }
            Text(text = LocalContext.current.getString(R.string.total_amount,totalAmountToBuy),
                textAlign = TextAlign.Center,
                color = white, fontSize = 24.sp, modifier = Modifier.padding(vertical = 8.dp),
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun EmptyCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(containerColor = black),
    ){
        Text(text = LocalContext.current.getString(R.string.empty_cart),
            textAlign = TextAlign.Center,
            color = white, fontSize = 16.sp, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ProductItems(items: List<ProductItemUI>?,viewModel: MainViewModel) {
    LazyColumn(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)){
        items(items ?: emptyList()){
            ProductItem(item = it,viewModel)
        }
    }
}

@Composable
fun ProductItem(item:ProductItemUI,viewModel: MainViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(containerColor = white),
        border = BorderStroke(1.dp, lightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = "Image",
                modifier = Modifier
                    .padding(16.dp)
                    .size(92.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = item.name,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.description,
                    fontSize = 12.sp
                )

                Counter(item.itemCount,onValueDecreaseClick = {
                    viewModel.updateItemCount(item.id,false)
                }, onValueIncreaseClick = {
                    viewModel.updateItemCount(item.id,true)
                })
            }
        }
    }
}

