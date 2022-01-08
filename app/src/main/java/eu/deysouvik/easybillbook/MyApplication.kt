package eu.deysouvik.easybillbook

import android.app.Application
import eu.deysouvik.easybillbook.repository.AppDatabase

class MyApplication : Application() {
    val appDatabase by lazy {
        AppDatabase.getInstance(this)
    }
}