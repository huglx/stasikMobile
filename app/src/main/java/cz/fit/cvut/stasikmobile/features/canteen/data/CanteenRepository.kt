package cz.fit.cvut.stasikmobile.features.canteen.data

import cz.fit.cvut.stasikmobile.features.canteen.domain.CanteenResponse

class CanteenRepository(
    private val canteenRemoteDataSource: CanteenRemoteDataSource,
    private val canteenLocalDataSource: CanteenLocalDataSource) {

    suspend fun getCanteen(idx: Int): CanteenResponse {
        return try {
            val result = canteenRemoteDataSource.getCanteen(idx)
            if(result.isNotEmpty()) {
                canteenLocalDataSource.deleteAll()
                canteenLocalDataSource.insert(result)
                CanteenResponse(result, true)
                }
            else
                CanteenResponse(canteenLocalDataSource.getCanteen(0), false)
        }catch (t: Throwable) {
            CanteenResponse(canteenLocalDataSource.getCanteen(0), false)
        }
    }
}