package br.edu.jgsilveira.portfolio.netfrixson.api.dto

class MovieGenres(
    success: Boolean?,
    statusCode: Int?,
    statusMessage: String?,
    val genres: List<Genre>
) : BaseResponse(success, statusCode, statusMessage) {
    data class Genre(
            val id: Int,
            val name: String
    )
}