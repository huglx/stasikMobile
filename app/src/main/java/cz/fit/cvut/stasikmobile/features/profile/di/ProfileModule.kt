package cz.fit.cvut.stasikmobile.features.profile.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import cz.fit.cvut.stasikmobile.features.profile.ProfileViewModel
import cz.fit.cvut.stasikmobile.features.profile.data.LoginRemoteDataSource
import cz.fit.cvut.stasikmobile.features.profile.data.api.LoginApiDescription
import cz.fit.cvut.stasikmobile.features.profile.data.api.LoginRetrofitDataSource
import cz.fit.cvut.stasikmobile.features.profile.data.LoginRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {
    single { get<Retrofit>().create(LoginApiDescription::class.java) }
    factory<LoginRemoteDataSource> { LoginRetrofitDataSource(loginApiDescription = get()) }
    factoryOf(::LoginRepository)

    viewModelOf(::ProfileViewModel)
}