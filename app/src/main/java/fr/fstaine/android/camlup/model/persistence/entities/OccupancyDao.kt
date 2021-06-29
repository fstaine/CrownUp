package fr.fstaine.android.camlup.model.persistence.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OccupancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg occupancies: Occupancy)

    @Query("SELECT * FROM occupancy ORDER BY timestamp")
    fun getAll(): Flow<List<Occupancy>>

    @Query("SELECT * FROM occupancy WHERE hall = :hall ORDER BY timestamp DESC LIMIT 1")
    fun getLast(hall: Hall): Flow<Occupancy>

    @Query("DELETE FROM Occupancy")
    fun deleteAll()
}
