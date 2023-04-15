package cz.fit.cvut.stasikmobile

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            //modules(coreModule, breedModule, factModule)
        }
    }
}