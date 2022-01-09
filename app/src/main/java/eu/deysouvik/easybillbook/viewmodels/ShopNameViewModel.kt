package eu.deysouvik.easybillbook.viewmodels

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.deysouvik.easybillbook.MyApplication
import eu.deysouvik.easybillbook.utils.Constants
import eu.deysouvik.easybillbook.utils.PreferenceProvider

class ShopNameViewModel(application: MyApplication) : AndroidViewModel(application) {

    private val preferenceProvider = PreferenceProvider(application.applicationContext)

    fun isValidShopName(name: String): Boolean{
        return name.isNotBlank()
    }

    fun setShopName(name: String){
        preferenceProvider.putString(Constants.KEY_SHOP_NAME,name)
        preferenceProvider.putBoolean(Constants.KEY_SAVED,true)
    }
}

class ShopNameViewModelFactory(private val application: MyApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShopNameViewModel::class.java))
            return ShopNameViewModel(application) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}