package br.edu.jgsilveira.portfolio.netfrixson.api.endpoint

import br.edu.jgsilveira.portfolio.netfrixson.api.dto.BaseResponse
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Rate
import br.edu.jgsilveira.portfolio.netfrixson.api.service.MovieService
import retrofit2.Response

class MovieEndPoint : BaseEndPoint() {

    fun rate(id: Int, value: Double, guestSessionId: String, sessionId: String): Response<BaseResponse> {
        return retrofit.create(MovieService::class.java).rate(
                movieId = id.toString(),
                rate = Rate(value),
                queries = mapOf("guest_session_id" to guestSessionId, "session_id" to sessionId)
        ).execute()
    }

    fun detail(id: Int): Response<Movie> =
            retrofit.create(MovieService::class.java).detail(movieId = id).execute()

}