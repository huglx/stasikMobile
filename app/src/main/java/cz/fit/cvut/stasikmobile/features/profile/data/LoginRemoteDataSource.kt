package cz.fit.cvut.stasikmobile.features.profile.data

interface LoginRemoteDataSource {
    suspend fun getLoggedUserList(): List<String>
}