package eu.deysouvik.easybillbook.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.deysouvik.easybillbook.models.Customer

class MainActivityViewModel {

    private val _shopName = MutableLiveData("")
    val shopName: LiveData<String>
        get() = _shopName

    private val _customerList = MutableLiveData<List<Customer>>()
    val customerList: LiveData<List<Customer>>
        get() = _customerList

    fun setShopName(shopName: String){
        _shopName.value = shopName
    }

    fun isValidShopName(shopName: String): Boolean{
        return shopName.isNotBlank()
    }

    fun getCustomerListFromDb(){

//        _customerList.value = customerList
    }
}