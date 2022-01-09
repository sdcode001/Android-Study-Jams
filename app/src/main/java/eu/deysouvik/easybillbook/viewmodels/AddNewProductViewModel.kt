package eu.deysouvik.easybillbook.viewmodels

import androidx.lifecycle.*
import eu.deysouvik.easybillbook.repository.entities.products.ProductDao
import eu.deysouvik.easybillbook.repository.entities.products.ProductEntity
import kotlinx.coroutines.launch

class AddNewProductViewModel(private val productDao: ProductDao) : ViewModel() {

    fun isValidProductDetails(name: String, price: String) : Boolean{
        return !(name.isBlank() || price.isBlank())
    }

    fun addProduct(name: String, price: String){
        val productEntity = ProductEntity(productName = name, productPrice = price)

        viewModelScope.launch {
            productDao.addProduct(productEntity)
        }
    }
}

class AddNewProductViewModelFactory(private val productDao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddNewProductViewModel::class.java))
            return AddNewProductViewModel(productDao) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}