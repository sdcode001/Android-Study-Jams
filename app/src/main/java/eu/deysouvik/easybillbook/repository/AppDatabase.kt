package eu.deysouvik.easybillbook.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eu.deysouvik.easybillbook.repository.entities.customers.CustomerDao
import eu.deysouvik.easybillbook.repository.entities.customers.CustomerEntity
import eu.deysouvik.easybillbook.repository.entities.products.ProductDao
import eu.deysouvik.easybillbook.repository.entities.products.ProductEntity

@Database(
    entities = [CustomerEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun customerDao() : CustomerDao
    abstract fun productDao() : ProductDao

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : AppDatabase{
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}