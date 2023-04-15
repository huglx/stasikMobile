package cz.fit.cvut.stasikmobile

import android.app.Application
import cz.fit.cvut.stasikmobile.core.data.datastore.di.dataStoreKoinModule
import cz.fit.cvut.stasikmobile.features.profile.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataStoreKoinModule, profileModule)
        }
    }
}