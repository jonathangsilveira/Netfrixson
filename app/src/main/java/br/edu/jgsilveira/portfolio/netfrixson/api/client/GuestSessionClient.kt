package br.edu.jgsilveira.portfolio.netfrixson.api.client

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.RetrofitResponseException
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.RatedMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.service.GuestSessionService
import br.edu.jgsilveira.portfolio.retrofitgithubsample.dto.GuestSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GuestSessionClient {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    suspend fun newGuestSession(): GuestSession? = withContext(Dispatchers.IO) {
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
            }

}