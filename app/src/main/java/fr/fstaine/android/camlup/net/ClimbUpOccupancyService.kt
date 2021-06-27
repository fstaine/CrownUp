package fr.fstaine.android.camlup.net

import android.util.Log
import androidx.annotation.WorkerThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ClimbUpOccupancyService() {

    private val TAG = "ClimbUpOccupancyService"

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getGerlandOccupancy(): Int? {
        val doc: Document = Jsoup.connect("https://lyon-gerland.climb-up.fr/").get()
        val element = doc.getElementById("progress_bar")
        val value = element.attr("value")?.toIntOrNull()
        val max = element.attr("max")?.toIntOrNull()
        val occupancy = value?.let { v ->
            max?.let { m ->
                v * 100 / m
            }
        }
        if (occupancy == null) {
            Log.w(TAG, "Failed to parse occupancy from value '$value' & max '$max'")
        } else {
            Log.d(TAG, "Occupancy is $value / $max -> $occupancy%")
        }
        return occupancy
    }
}
