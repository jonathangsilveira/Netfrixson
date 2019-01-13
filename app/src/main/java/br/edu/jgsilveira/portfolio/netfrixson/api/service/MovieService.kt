package br.edu.jgsilveira.portfolio.netfrixson.api.service

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Rate
import retrofit2.Call
import retrofit2.http.*

interface MovieService {

    @GET(value = Api.MOVIE_DISCOVER)
    fun discover(@QueryMap queries: Map<String, String>): Call<DiscoverMovies>

    @POST(value = Api.RATE_MOVIE)
    fun rate(@Path(value = "movie_id") movieId: String,
             @Header(value = "Content-Type") contentType: String,
             @Body rate: Rate,
             @QueryMap queries: Map<String, String>)

    @GET(value = Api.MOVIE_GENRES)
    fun genres(@Query(value = "api_key") apiKey: String = Api.KEY,
               @Query(value = "language") language: String)

    @GET(value = Api.MOVIE_DETAIL)
    fun detail(@Path(value = "movie_id") movieId: Int,
               @Query(value = "api_key") apiKey: String = Api.KEY,
               @Query(value = "language") language: String = "en-US"): Call<Movie>

}