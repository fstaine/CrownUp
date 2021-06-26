package fr.fstaine.android.camlup.persistence.entities

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OccupancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg occupancies: Occupancy)

    @Query("SELECT * FROM occupancy")
    fun getAll(): Flow<List<Occupancy>>

    @Query("DELETE FROM Occupancy")
    fun deleteAll()
}
