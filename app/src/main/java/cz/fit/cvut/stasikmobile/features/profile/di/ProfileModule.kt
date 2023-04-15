package cz.fit.cvut.stasikmobile.features.profile.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import cz.fit.cvut.stasikmobile.features.profile.ProfileViewModel
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileViewModel)
}