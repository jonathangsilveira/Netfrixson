package br.edu.jgsilveira.portfolio.netfrixson.api.dto

import com.google.gson.annotations.SerializedName

data class DiscoverMovies(
        val page: Int,
        val results: List<Result>,
        @SerializedName("total_results")
        val totalResults: Int,
        @SerializedName("total_pages")
        val totalPages: Int
) {
    data class Result(
            @SerializedName("poster_path")
            val posterPath: Any?,
            val adult: Boolean,
            val overview: String,
            @SerializedName("release_date")
            val releaseDate: String,
            @SerializedName("genre_ids")
            val genreIds: List<Int>,
            val id: Int,
            @SerializedName("original_title")
            val originalTitle: String,
            @SerializedName("original_language")
            val originalLanguage: String,
            val title: String,
            @SerializedName("backdrop_path")
            val backdropPath: Any?,
            val popularity: Double,
            @SerializedName("vote_count")
            val voteCount: Int,
            val video: Boolean,
            @SerializedName("vote_average")
            val voteAverage: Double
    )
}