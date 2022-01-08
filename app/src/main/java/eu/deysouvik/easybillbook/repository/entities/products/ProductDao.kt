package eu.deysouvik.easybillbook.repository.entities.products

import androidx.room.*
import androidx.room.Dao
import eu.deysouvik.easybillbook.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>
}