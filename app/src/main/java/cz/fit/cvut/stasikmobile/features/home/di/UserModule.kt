package cz.fit.cvut.stasikmobile.features.home.di

import cz.fit.cvut.stasikmobile.features.home.data.UserRemoteDataSource
import cz.fit.cvut.stasikmobile.features.home.data.api.UserApiDescription
import cz.fit.cvut.stasikmobile.features.home.data.api.UserRetrofitDataSource
import cz.fit.cvut.stasikmobile.features.home.presentetion.HomeViewModel
import cz.fit.cvut.stasikmobile.features.home.data.UserRepository


import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit

val userModule get() = module {
    single { get<Retrofit>().create(UserApiDescription::class.java) }
    factory<UserRemoteDataSource> { UserRetrofitDataSource(apiDescription = get()) }

    factoryOf(::UserRepository)
    viewModelOf(::HomeViewModel)
}