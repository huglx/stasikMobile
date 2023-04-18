package cz.fit.cvut.stasikmobile.features.canteen.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CanteenDao {
    @Query("SELECT * FROM canteen")
    suspend fun getCanteen(): List<CanteenDB>

    @Insert
    suspend fun insert(canteen: List<CanteenDB>)

    @Query("DELETE FROM canteen")
    suspend fun deleteAll()
}