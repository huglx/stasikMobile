package cz.fit.cvut.stasikmobile.features.home.data.api

import cz.fit.cvut.stasikmobile.features.home.data.UserRemoteDataSource
import cz.fit.cvut.stasikmobile.features.home.domain.User

class UserRetrofitDataSource(
    private val apiDescription: UserApiDescription
):UserRemoteDataSource {

    override suspend fun getUsers(): List<List<User>> {
        val result =  apiDescription.getUsers()
        return result.map { listUser -> listUser.map { it.toUser() } }
    }

    private fun UserApi.toUser(): User{
        return User(
            username = username,
            subjects = tt.map { User.Subject(
                it.id.toString(),
                User.Subject.Links(
                    it.links.course,
                    it.links.room
                ),
                it.starts_at,
                it.ends_at
            ) }
        )
    }
}