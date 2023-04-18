package cz.fit.cvut.stasikmobile

import android.app.Application
import cz.fit.cvut.stasikmobile.core.di.coreModule
import cz.fit.cvut.stasikmobile.features.canteen.di.canteenModule
import cz.fit.cvut.stasikmobile.features.home.di.userModule
import cz.fit.cvut.stasikmobile.features.profile.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(coreModule, profileModule, userModule, canteenModule)
        }
    }
}