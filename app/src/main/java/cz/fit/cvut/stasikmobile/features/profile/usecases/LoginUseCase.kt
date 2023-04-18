package cz.fit.cvut.stasikmobile.features.profile.usecases

import cz.fit.cvut.stasikmobile.core.data.datastore.UserProfileSource
import cz.fit.cvut.stasikmobile.features.profile.data.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository,
    private val userProfileSource: UserProfileSource
) {

    suspend operator fun invoke(name: String): Boolean {
        return if(login(name)) {
            userProfileSource.setName(name = name)
            userProfileSource.setLogged(true)
            true
        }else
            false
    }

    private suspend fun login(userName: String): Boolean {
        val result = loginRepository.getUsersList()
        return result.users.contains(userName.replace(" ", ""))
    }
}