package athul.pizza.checkout.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import athul.pizza.checkout.data.CheckoutRepository
import athul.pizza.checkout.data.models.BuyData
import athul.pizza.checkout.data.models.ProductData
import athul.pizza.checkout.ui.beans.ProductUIData
import athul.pizza.checkout.ui.beans.toProductItemUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Viewmodel class for [MainActivity.kt]
 * @param app Application instance
 * @param repository Main data repository to handle app's bus
 */
class MainViewModel(private val app:Application,private val repository: CheckoutRepository) : AndroidViewModel(app) {
    /**
     * Flow data class to paint UI
     */
    private val _uiDataFlow = MutableStateFlow(ProductUIData())
    val uiDataFlow: Flow<ProductUIData>
        get() = _uiDataFlow

    /**
     * Flow data class to paint total amount
     */
    private val _totalAmountFlow = MutableStateFlow<BuyData?>(null)
    val totalAmountFlow: Flow<BuyData?>
        get() = _totalAmountFlow


    /**
     * Initial method to get the fresh product data
     */
    fun init(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.productsData
            data?.mapToProductUIData()?.let { _uiDataFlow.emit(it) }
        }
    }

    private fun ProductData.mapToProductUIData():ProductUIData {
        val existingData = _uiDataFlow.value
        return ProductUIData(
            items = products.map { it.toProductItemUI(existingData.items?.firstOrNull {item-> it.id == item.id }?.itemCount ?: 0) },
            currentSelectedDiscountGroup = existingData.currentSelectedDiscountGroup,
            discountGroups = discountData.map { it.discountGroup }
        )
    }

    /**
     * Updates the item count from UI. Once updated, Total amount to buy is also calculated
     * @param itemId id of the product needs to be updated
     * @param isIncrease tells whether the operation is increase or decrease
     */
    fun updateItemCount(itemId:Int, isIncrease:Boolean){
       viewModelScope.launch {
           val newData = _uiDataFlow.value.copy()
           newData.items?.first { it.id == itemId }?.let {
               if (isIncrease){
                   it.itemCount++
               }else{
                   if (it.itemCount>0){
                       it.itemCount--
                   }else{
                       it.itemCount=0
                   }
               }
           }
           _uiDataFlow.emit(newData)
       }
        calculateAmountToBuy()
    }

    /**
     * Updates the current discount group. Once updated, Total amount to buy is also calculated
     * @param group name of the new discount group user selected
     */
    fun updateDiscountGroup(group:String){
        viewModelScope.launch {
            val newData = _uiDataFlow.value.copy()
            newData.currentSelectedDiscountGroup = group
            _uiDataFlow.emit(newData)
        }
        calculateAmountToBuy()
    }

    /**
     * Calculates the total amount to buy based on the current user selections.
     * Also updates the UI once the calculation is done.
     */
    private fun calculateAmountToBuy(){
       viewModelScope.launch {
           val data = _uiDataFlow.value
           val totalList = mutableListOf<BuyData>()
           data.items?.forEach {
               totalList.add(repository.getTotalAmountToBuyItem(it.id,it.itemCount,data.currentSelectedDiscountGroup))
           }
           val totalAmountToBuy = totalList.sumOf { it.totalAmountToBuy }
           val discountMessage = totalList.first { !it.discountMessage.isNullOrEmpty() }.discountMessage
           val totalItems = totalList.sumOf { it.totalPizzas }

           _totalAmountFlow.emit(BuyData(
               totalAmountToBuy,discountMessage,totalItems
           ))
       }
    }
    // Factory object inside companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainViewModel((application), CheckoutRepository(application)) as T
            }
        }
    }
}
