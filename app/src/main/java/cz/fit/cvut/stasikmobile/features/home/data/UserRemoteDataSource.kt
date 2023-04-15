package cz.fit.cvut.stasikmobile.features.home.data

import cz.fit.cvut.stasikmobile.features.home.domain.User

interface UserRemoteDataSource {
    suspend fun getUsers(): List<List<User>>
}