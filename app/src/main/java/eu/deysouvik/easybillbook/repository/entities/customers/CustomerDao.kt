package eu.deysouvik.easybillbook.repository.entities.customers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.deysouvik.easybillbook.models.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCustomer(customerEntity : CustomerEntity)

    @Delete
    suspend fun deleteCustomer(customerEntity : CustomerEntity)

    @Query("SELECT * FROM customers")
    fun getAllCustomers(): Flow<List<CustomerEntity>>
}