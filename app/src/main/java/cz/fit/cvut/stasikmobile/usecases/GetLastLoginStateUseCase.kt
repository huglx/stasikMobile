package cz.fit.cvut.stasikmobile.usecases

import cz.fit.cvut.stasikmobile.core.data.datastore.UserProfileSource
import kotlinx.coroutines.flow.Flow

class GetLastLoginStateUseCase(
    private val userProfileSource: UserProfileSource
) {

    operator fun invoke(): Pair<Flow<String>, Flow<Boolean>> {
        return Pair(userProfileSource.getName(), userProfileSource.getLogged())
    }

}