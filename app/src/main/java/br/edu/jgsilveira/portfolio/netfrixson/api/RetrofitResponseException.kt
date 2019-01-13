package br.edu.jgsilveira.portfolio.netfrixson.api

class RetrofitResponseException(code: Int, message: String) : Exception("Response error code: [$code], message: [$message]")