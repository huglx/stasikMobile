package cz.fit.cvut.stasikmobile.features.canteen.data

import cz.fit.cvut.stasikmobile.features.canteen.domain.CanteenItem

interface CanteenLocalDataSource {

    suspend fun getCanteen(inx: Int): List<CanteenItem>

    suspend fun insert(canteen: List<CanteenItem>)

    suspend fun deleteAll()
}