package br.edu.jgsilveira.portfolio.netfrixson.api.service

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.*
import retrofit2.Call
import retrofit2.http.*

interface MovieService {

    @GET(value = Api.MOVIE_DISCOVER)
    fun discover(@QueryMap queries: Map<String, String>): Call<DiscoverMovies>

    @POST(value = Api.RATE_MOVIE)
    fun rate(@Path(value = "movie_id") movieId: String,
             @Header(value = "Content-Type") contentType: String = "application/json;charset=utf-8",
             @Body rate: Rate,
             @QueryMap queries: Map<String, String>): Call<BaseResponse>

    @GET(value = Api.MOVIE_GENRES)
    fun genres(@Query(value = "api_key") apiKey: String = Api.KEY,
               @Query(value = "language") language: String = "en-US"): Call<MovieGenres>

    @GET(value = Api.MOVIE_DETAIL)
    fun detail(@Path(value = "movie_id") movieId: Int,
               @Query(value = "api_key") apiKey: String = Api.KEY,
               @Query(value = "language") language: String = "en-US"): Call<Movie>

}