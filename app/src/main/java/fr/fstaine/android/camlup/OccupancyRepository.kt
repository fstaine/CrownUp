package fr.fstaine.android.camlup

import android.util.Log
import androidx.annotation.WorkerThread
import fr.fstaine.android.camlup.model.net.ClimbUpOccupancyService
import fr.fstaine.android.camlup.model.persistence.entities.Hall
import fr.fstaine.android.camlup.model.persistence.entities.Occupancy
import fr.fstaine.android.camlup.model.persistence.entities.OccupancyDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant

class OccupancyRepository(
    private val occupancyDao: OccupancyDao,
    private val occupancyService: ClimbUpOccupancyService,
    private val scope: CoroutineScope
) {

    val TAG = "OccupancyRepository"

    val allOccupancies: Flow<List<Occupancy>> = occupancyDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(occupancy: Occupancy) {
        occupancyDao.insertAll(occupancy)
    }

    @WorkerThread
    suspend fun fetchCurrentOccupancy() = scope.launch {
        val instant = Instant.now()
        val gerland = occupancyService.getGerlandOccupancy()
        val gerlandOccupancy = gerland?.let {
            Occupancy(instant, it, Hall.GERLAND)
        }
        val confluence = occupancyService.getConfluenceOccupancy()
        val confluenceOccupancy = confluence?.let {
            Occupancy(instant, it, Hall.CONFLUENCE)
        }
        val newOccupancies = listOf(gerlandOccupancy, confluenceOccupancy).mapNotNull { it }.toTypedArray()
        occupancyDao.insertAll(occupancies = newOccupancies)
        Log.d(TAG, "New occupancies: $newOccupancies")
    }
}
