package br.edu.jgsilveira.portfolio.netfrixson.api.dto

data class MovieGenres(val genres: List<Genre>) {
    data class Genre(
            val id: Int,
            val name: String
    ) {
        override fun toString(): String {
            return name
        }
    }
}