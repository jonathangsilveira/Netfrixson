package br.edu.jgsilveira.portfolio.netfrixson.api.endpoint

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseEndPoint {

    protected val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}