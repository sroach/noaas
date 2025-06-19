package gy.roach.no.noaas

import noaas.composeapp.generated.resources.Res
import kotlin.random.Random

class ReasonsProvider {
    // Cached list of reasons to avoid reading the file multiple times
    private var reasonsList: List<String>? = null

    /**
     * Gets a random reason from the cached reasons list.
     * The list is loaded only once when first needed.
     * 
     * @return A random reason string.
     */
    suspend fun getRandomReason(): String {
        // Load the list if it hasn't been loaded yet
        if (reasonsList == null) {
            reasonsList = loadReasonsFromFile()
        }

        // Get a random index within the array bounds
        val randomIndex = Random.nextInt(reasonsList!!.size)

        // Return the random reason
        return reasonsList!![randomIndex]
    }

    /**
     * Loads and parses the reasons from the JSON file.
     * This is called only once when the reasonsList is first accessed.
     * 
     * @return A list of reason strings.
     */
    private suspend fun loadReasonsFromFile(): List<String> {
        // Load the reasons.json file
        val reasonsJson = Res.readBytes("files/reasons.json").decodeToString()

        // Parse the JSON array manually
        // The file is a simple JSON array of strings, so we can parse it by splitting on commas
        // and cleaning up the quotes and brackets
        val cleanJson = reasonsJson.trim().removeSurrounding("[", "]")

        // Split by commas that are followed by a quote, but not by commas inside the strings
        return cleanJson.split("\",\n  \"")
            .map { it.trim().removeSurrounding("\"") }
    }
}
