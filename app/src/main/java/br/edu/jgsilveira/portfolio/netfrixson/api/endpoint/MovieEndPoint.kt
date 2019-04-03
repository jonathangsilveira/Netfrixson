package br.edu.jgsilveira.portfolio.netfrixson.api.endpoint

import br.edu.jgsilveira.portfolio.netfrixson.api.dto.BaseResponse
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.MovieGenres
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Rate
import br.edu.jgsilveira.portfolio.netfrixson.api.service.MovieService
import br.edu.jgsilveira.portfolio.netfrixson.common.Result

class MovieEndPoint : BaseEndPoint() {

    private val service: MovieService by lazy {
        retrofit.service<MovieService>()
    }

    fun rate(
            id: Int,
            value: Double,
            guestSessionId: String,
            sessionId: String
    ): Result<BaseResponse> = safeApiCall {
        service.rate(
                movieId = id.toString(),
                rate = Rate(value),
                queries = mapOf("guest_session_id" to guestSessionId, "session_id" to sessionId)
        )
    }

    fun detail(id: Int): Result<Movie> = safeApiCall {
        service.detail(movieId = id)
    }

    fun genres(): Result<MovieGenres> = safeApiCall {
        service.genres()
    }

}