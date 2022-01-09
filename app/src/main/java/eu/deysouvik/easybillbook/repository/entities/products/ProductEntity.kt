package eu.deysouvik.easybillbook.repository.entities.products

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "product_id") val productId : Int = 0,
    @NonNull @ColumnInfo(name = "product_name", defaultValue = "") val productName : String,
    @NonNull @ColumnInfo(name = "product_price", defaultValue = "") val productPrice : String
)