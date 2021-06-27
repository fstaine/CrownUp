package fr.fstaine.android.camlup.net

import android.util.Log

class ClimbUpOccupancyService(
    private val networkService: ClimbUpNetworkService
) {

    private val TAG = "ClimbUpOccupancyService"

    suspend fun getGerlandOccupancy(): Int? {
        val gerland = networkService.getGerlandHall()
        Log.d(TAG, "getGerlandOccupancy: $gerland")
        return null
    }
}
