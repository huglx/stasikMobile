package cz.fit.cvut.stasikmobile.features.home.data.api

import retrofit2.http.GET

interface UserApiDescription {
    @GET("ivantuz")
    suspend fun getUsers(): List<List<UserApi>>

}