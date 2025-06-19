package gy.roach.no.noaas

import noaas.composeapp.generated.resources.Res
import kotlin.random.Random

class ReasonsProvider {
    /**
     * Gets a random reason from the reasons.json file.
     * 
     * @return A random reason string.
     */
    suspend fun getRandomReason(): String {
        // Load the reasons.json file
        val reasonsJson = Res.readBytes("files/reasons.json").decodeToString()
        
        // Parse the JSON array manually
        // The file is a simple JSON array of strings, so we can parse it by splitting on commas
        // and cleaning up the quotes and brackets
        val cleanJson = reasonsJson.trim().removeSurrounding("[", "]")
        
        // Split by commas that are followed by a quote, but not by commas inside the strings
        val reasonsList = cleanJson.split("\",\n  \"")
            .map { it.trim().removeSurrounding("\"") }
        
        // Get a random index within the array bounds
        val randomIndex = Random.nextInt(reasonsList.size)
        
        // Return the random reason
        return reasonsList[randomIndex]
    }
}