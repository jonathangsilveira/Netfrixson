package br.edu.jgsilveira.portfolio.netfrixson.api.dto

class MovieGenres(val genres: List<Genre>) {
    data class Genre(
            val id: Int,
            val name: String
    )
}