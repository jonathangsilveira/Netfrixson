package br.edu.jgsilveira.portfolio.netfrixson.api.endpoint

class GuestSessionEndPoint : BaseEndPoint() {

    /*suspend fun newGuestSession(): GuestSession? = withContext(Dispatchers.IO) {
        val response = retrofit.create(GuestSessionService::class.java).newGuestSession(Api.KEY).execute()
        if (response.isSuccessful)
            response.body()
        else
            throw RetrofitResponseException(response.code(), response.message())
    }

    suspend fun ratedMovies(guestSession: GuestSession): RatedMovies? =
            withContext(Dispatchers.IO) {
                val response = retrofit.create(GuestSessionService::class.java)
                        .getRatedMovies(guestSessionId = guestSession.guestSessionId,
                                apiKey = Api.KEY,
                                language = "en-US",
                                sortBy = "created_at.asc")
                        .execute()
                if (response.isSuccessful)
                    response.body()
                else
                    throw RetrofitResponseException(response.code(), response.message())
            }*/

}