package cz.fit.cvut.stasikmobile.features.canteen.data

import cz.fit.cvut.stasikmobile.features.canteen.domain.CanteenItem

interface CanteenRemoteDataSource {

    suspend fun getCanteen(inx: Int): List<CanteenItem>
}