package cz.fit.cvut.stasikmobile.features.canteen.di

import cz.fit.cvut.stasikmobile.core.data.db.StasikDB
import cz.fit.cvut.stasikmobile.features.canteen.data.CanteenLocalDataSource
import cz.fit.cvut.stasikmobile.features.canteen.data.CanteenRemoteDataSource
import cz.fit.cvut.stasikmobile.features.canteen.data.CanteenRepository
import cz.fit.cvut.stasikmobile.features.canteen.data.api.CanteenApiDescription
import cz.fit.cvut.stasikmobile.features.canteen.data.api.CanteenRetrofitDataSource
import cz.fit.cvut.stasikmobile.features.canteen.data.db.CanteenRoomDataSource
import cz.fit.cvut.stasikmobile.features.canteen.presentetion.CanteenViewModel

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit

val canteenModule get() = module {
    single{get<Retrofit>().create(CanteenApiDescription::class.java)}
    factory<CanteenRemoteDataSource> { CanteenRetrofitDataSource(apiDescription = get()) }

    single { get<StasikDB>().stasikDB() }
    factory<CanteenLocalDataSource> { CanteenRoomDataSource(canteenDao = get()) }

    factoryOf(::CanteenRepository)
    viewModelOf(::CanteenViewModel)
}