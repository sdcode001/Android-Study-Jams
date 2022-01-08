package eu.deysouvik.easybillbook.repository.entities.customers

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "customer_id") val customerId: Int,
    @NonNull @ColumnInfo(name = "customer_name", defaultValue = "") val customerName: String,
    @NonNull @ColumnInfo(name = "customer_phone_number", defaultValue = "") val customerPhoneNumber : String,
    @NonNull @ColumnInfo(name = "customer_due", defaultValue = "0.0") val customerDue : Double,
    @NonNull @ColumnInfo(name = "shopkeeper_due", defaultValue = "0.0") val shopkeeperDue: Double
)