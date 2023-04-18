package cz.fit.cvut.stasikmobile.features.canteen.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CanteenApiDescription {
    @GET("canteen")
    suspend fun getCanteen(@Query("id") idx: Int): List<CanteenItemApi>
}