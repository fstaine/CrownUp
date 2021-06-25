package fr.fstaine.android.camlup.persistence.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OccupancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg occupancies: Occupancy)

    @Query("SELECT * FROM occupancy")
    fun getAll(): Flow<List<Occupancy>>
}
