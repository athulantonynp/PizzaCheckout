package athul.pizza.checkout.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import athul.pizza.checkout.data.MainRepository

/**
 * Viewmodel class for [MainActivity.kt]
 * @param app Application instance
 * @param repository Main data repository to handle app's business logic
 */
class MainViewModel(private val app:Application,private val repository: MainRepository) : AndroidViewModel(app) {

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
                return MainViewModel((application), MainRepository()) as T
            }
        }
    }


}
