package cz.fit.cvut.stasikmobile.features.home.data

import android.util.Log
import cz.fit.cvut.stasikmobile.features.home.domain.UserResponse

class UserRepository(private val userRemoteDataSource: UserRemoteDataSource) {

    suspend fun getUsers(): UserResponse{
        return try {
            val result = userRemoteDataSource.getUsers()
            UserResponse(result, true)
        }catch (t: Throwable){
            t.message?.let {  }
            UserResponse(listOf(), false)
        }
    }
}