package eu.deysouvik.easybillbook.viewmodels

import androidx.lifecycle.*
import eu.deysouvik.easybillbook.repository.entities.products.ProductDao

class ProductListViewModel(private val productDao : ProductDao) : ViewModel() {
    val productList = productDao.getAllProducts().asLiveData()
}

class ProductListViewModelFactory(private val productDao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductListViewModel::class.java))
            return ProductListViewModel(productDao) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}