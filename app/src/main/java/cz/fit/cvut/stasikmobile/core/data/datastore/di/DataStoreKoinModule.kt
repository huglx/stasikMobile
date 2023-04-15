package cz.fit.cvut.stasikmobile.core.data.datastore.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import cz.fit.cvut.stasikmobile.core.data.datastore.UserProfileSource

val dataStoreKoinModule = module {
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("user_preferences")
        }
    }

    singleOf(::UserProfileSource)
}