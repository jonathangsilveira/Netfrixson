package br.edu.jgsilveira.portfolio.netfrixson.api.endpoint

import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.service.MovieService
import retrofit2.Response

class DiscoverEndPoint : BaseEndPoint() {

    fun movies(queries: Map<String, String> = mapOf()): Response<DiscoverMovies> =
            retrofit.create(MovieService::class.java).discover(queries).execute()

}