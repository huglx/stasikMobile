package cz.fit.cvut.stasikmobile.features.profile.data

import cz.fit.cvut.stasikmobile.features.profile.domain.LoginResponse

class LoginRepository(private val loginRemoteDataSource: LoginRemoteDataSource) {
    suspend fun getUsersList(): LoginResponse {
        return try {
            LoginResponse(loginRemoteDataSource.getLoggedUserList(), true)
        }catch (t: Throwable) {
            LoginResponse(listOf(), false)
        }
    }
}