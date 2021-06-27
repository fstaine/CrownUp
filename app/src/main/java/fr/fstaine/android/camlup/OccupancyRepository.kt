package fr.fstaine.android.camlup

import android.util.Log
import androidx.annotation.WorkerThread
import fr.fstaine.android.camlup.net.ClimbUpNetworkService
import fr.fstaine.android.camlup.net.ClimbUpOccupancyService
import fr.fstaine.android.camlup.persistence.entities.Occupancy
import fr.fstaine.android.camlup.persistence.entities.OccupancyDao
import kotlinx.coroutines.flow.Flow

class OccupancyRepository(
    private val occupancyDao: OccupancyDao,
    private val occupancyService: ClimbUpOccupancyService
) {

    val TAG = "OccupancyRepository"

    val allOccupancies: Flow<List<Occupancy>> = occupancyDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(occupancy: Occupancy) {
        occupancyDao.insertAll(occupancy)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun fetchCurrentOccupancy() {
        val occupancy = occupancyService.getGerlandOccupancy()
        Log.d(TAG, "fetchCurrentOccupancy: $occupancy")
    }
}
