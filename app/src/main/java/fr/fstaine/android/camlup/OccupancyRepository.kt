package fr.fstaine.android.camlup

import androidx.annotation.WorkerThread
import fr.fstaine.android.camlup.persistence.entities.Occupancy
import fr.fstaine.android.camlup.persistence.entities.OccupancyDao
import kotlinx.coroutines.flow.Flow

class OccupancyRepository(private val occupancyDao: OccupancyDao) {

    val allOccupancies: Flow<List<Occupancy>> = occupancyDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(occupancy: Occupancy) {
        occupancyDao.insertAll(occupancy)
    }
}
