package cz.fit.cvut.stasikmobile.core.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import cz.fit.cvut.stasikmobile.core.data.api.RetrofitProvider
import cz.fit.cvut.stasikmobile.core.data.datastore.UserProfileSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    single { RetrofitProvider.provide() }

    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("user_preferences")
        }
    }

    singleOf(::UserProfileSource)
}