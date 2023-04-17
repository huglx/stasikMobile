package cz.fit.cvut.stasikmobile.features.profile.data.api

import cz.fit.cvut.stasikmobile.features.profile.data.LoginRemoteDataSource

class LoginRetrofitDataSource(private val loginApiDescription: LoginApiDescription): LoginRemoteDataSource {

    override suspend fun getLoggedUserList(): List<String> {
        return loginApiDescription.getLoggedUserList()
    }

}