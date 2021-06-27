package fr.fstaine.android.camlup.net

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ClimbUpNetworkService {

    val TAG = "ClimbUpNetworkService"

    suspend fun getGerlandHall(): String? = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://lyon-gerland.climb-up.fr/")
            val http = url.openConnection() as HttpURLConnection
            val content = http.content as InputStream
            content.bufferedReader().use(BufferedReader::readText)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
