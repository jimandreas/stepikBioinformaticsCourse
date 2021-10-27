@file:Suppress("UnnecessaryVariable", "unused", "UNUSED_VARIABLE")

package algorithms

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class UniprotDownloadFASTA {
    private val client = OkHttpClient()


    fun downloadFASTAfromUniprot(uniprotId: String): String {


        val request = Request.Builder()
            .url("https://www.uniprot.org/uniprot/${uniprotId}.fasta")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val body = response.body?.string()
                //println(body)
                return body!!
            }
        } catch (e: Exception) {
            println("Error on $uniprotId")
        }
        return ""
    }
}

