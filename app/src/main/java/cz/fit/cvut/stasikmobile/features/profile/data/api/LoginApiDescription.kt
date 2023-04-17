package cz.fit.cvut.stasikmobile.features.profile.data.api

import retrofit2.http.GET

interface LoginApiDescription {
    @GET("users")
    suspend fun getLoggedUserList(): List<String>
}