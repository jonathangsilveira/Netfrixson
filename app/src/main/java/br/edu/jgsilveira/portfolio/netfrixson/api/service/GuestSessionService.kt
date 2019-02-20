package br.edu.jgsilveira.portfolio.netfrixson.api.service

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Configuration
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.RatedMovies
import br.edu.jgsilveira.portfolio.retrofitgithubsample.dto.GuestSession
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GuestSessionService {

    @GET(value = Api.GUEST_SESSION)
    fun newGuestSession(@Query(value = "api_key") apiKey: String): Call<GuestSession>

    @GET(value = Api.RATED_MOVIES)
    fun ratedMovies(@Path(value = "guest_session_id") guestSessionId: String,
                    @Query(value = "api_key") apiKey: String,
                    @Query(value = "language") language: String,
                    @Query(value = "sort_by") sortBy: String): Call<RatedMovies>

    @GET(value = Api.CONFIGURATION)
    fun configuration(@Query(value = "api_key") apiKey: String = Api.KEY): Call<Configuration>

}