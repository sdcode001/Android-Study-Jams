package eu.deysouvik.easybillbook.viewmodels

import androidx.lifecycle.*
import eu.deysouvik.easybillbook.repository.entities.customers.CustomerDao

class MainViewModel(private val customerDao : CustomerDao) : ViewModel(){

    val customerList = customerDao.getAllCustomers().asLiveData()

    private val _shopName = MutableLiveData("")
    val shopName: LiveData<String>
        get() = _shopName

    fun setShopName(shopName: String){
        _shopName.value = shopName
    }

    fun isValidShopName(shopName: String): Boolean{
        return shopName.isNotBlank()
    }
}

class MainViewModelFactory(private val customerDao: CustomerDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(customerDao) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}