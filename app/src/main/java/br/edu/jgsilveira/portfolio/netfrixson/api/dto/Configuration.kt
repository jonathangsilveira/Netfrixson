package br.edu.jgsilveira.portfolio.netfrixson.api.dto

import com.google.gson.annotations.SerializedName

data class Configuration(
        val images: Images
) {
    data class Images(
            @SerializedName("base_url")
            val baseUrl: String,
            @SerializedName("secure_base_url")
            val secureBaseUrl: String,
            @SerializedName("backdrop_sizes")
            val backdropSizes: List<String>,
            @SerializedName("logo_sizes")
            val logoSizes: List<String>,
            @SerializedName("poster_sizes")
            val posterSizes: List<String>
    )
}