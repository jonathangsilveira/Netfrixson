package br.edu.jgsilveira.portfolio.netfrixson.api.endpoint

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.RatedMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.service.GuestSessionService
import br.edu.jgsilveira.portfolio.retrofitgithubsample.dto.GuestSession
import retrofit2.Response

class GuestSessionEndPoint : BaseEndPoint() {

    fun newGuestSession(): Response<GuestSession> =
            retrofit.create(GuestSessionService::class.java).newGuestSession(Api.KEY).execute()

    fun ratedMovies(guestSession: GuestSession): Response<RatedMovies> {
        return retrofit.create(GuestSessionService::class.java)
                .getRatedMovies(guestSessionId = guestSession.guestSessionId,
                        apiKey = Api.KEY,
                        language = "en-US",
                        sortBy = "created_at.asc")
                .execute()
    }

}