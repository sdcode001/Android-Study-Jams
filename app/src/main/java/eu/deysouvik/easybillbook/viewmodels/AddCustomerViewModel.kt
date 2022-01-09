package eu.deysouvik.easybillbook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import eu.deysouvik.easybillbook.repository.entities.customers.CustomerDao
import eu.deysouvik.easybillbook.repository.entities.customers.CustomerEntity
import kotlinx.coroutines.launch

class AddCustomerViewModel(private val customerDao : CustomerDao) : ViewModel() {

    fun isValidCustomerDetails(name: String, phoneNumber: String) : Boolean{
        return (name.isNotBlank() && phoneNumber.isNotBlank() && phoneNumber.length==10)
    }

    fun addCustomer(name: String, phoneNumber: String, customerDue: Double = 0.0, shopkeeperDue: Double = 0.0){
        val customer = CustomerEntity(
            customerName = name,
            customerPhoneNumber = phoneNumber,
            customerDue = customerDue,
            shopkeeperDue = shopkeeperDue
        )
        viewModelScope.launch {
            customerDao.addCustomer(customer)
        }
    }
}

class AddCustomerViewModelFactory(private val customerDao: CustomerDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddCustomerViewModel::class.java))
            return AddCustomerViewModel(customerDao) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}